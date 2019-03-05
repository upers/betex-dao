package com.vareger.redis.servicies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.vareger.models.BasketStatus;
import com.vareger.models.BasketWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Component store basket witch are not finished in to Redis.<br>
 * User get current available bask through this class.
 *
 * @author misha
 */
@Service
public class RedisCurrentBasketService {

    public static final String REDIS_KEY = "redis_currenct_basket";

    private static final Logger log = LoggerFactory.getLogger(RedisCurrentBasketService.class);

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void deleteAllBaskets() {
        redisTemplate.delete(REDIS_KEY);
        log.info("All currenct avalible baskets were delete");
    }

    public List<BasketWrapper> getAllBaskets() {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        Set<String> allKeys = hashOps.keys(REDIS_KEY);

        Set<String> keys = allKeys.stream().filter(key -> {
            try {
                Long.valueOf(key);
                return false;
            } catch (NumberFormatException e) {
                return true;
            }
        }).collect(Collectors.toSet());

        List<BasketWrapper> allBaskets = hashOps.multiGet(REDIS_KEY, keys).stream().map(str -> {
            CollectionType collectionType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, BasketWrapper.class);
            List<BasketWrapper> baskets = null;
            try {
                baskets = mapper.readValue(str, collectionType);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                baskets = new ArrayList<>();
            }

            return baskets;
        }).flatMap(list -> list.stream()).collect(Collectors.toList());

        return allBaskets;
    }

    public List<BasketWrapper> getBaskets(String currCode, String baseCurrCode) throws IOException {
        String key = currCode + baseCurrCode;

        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String strBasktes = hashOps.get(REDIS_KEY, key);

        if (strBasktes != null) {
            CollectionType collectionType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, BasketWrapper.class);
            List<BasketWrapper> basketsWrappers = mapper.readValue(strBasktes, collectionType);

            return basketsWrappers;
        }

        return new ArrayList<>();
    }

    public Optional<BasketWrapper> getOpenBasket(String currCode, String baseCurrCode, Long liveSeconds,
                                                 Long openSeconds) {
        try {
            for (BasketWrapper basket : getBaskets(currCode, baseCurrCode)) {
                if (basket.getStatus() == BasketStatus.OPENED) {
                    if (basket.getLiveSeconds().equals(liveSeconds) && basket.getOpenSeconds().equals(openSeconds))
                        return Optional.of(basket);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }


        return Optional.empty();
    }

    public BasketWrapper getBasket(long salt) throws IOException {
        HashOperations<String, String, String> hashOps = redisTemplate.opsForHash();
        String strBasket = hashOps.get(REDIS_KEY, String.valueOf(salt));
        if (strBasket != null) {
            BasketWrapper result = mapper.readValue(strBasket, BasketWrapper.class);

            return result;
        }

        return null;
    }

    public BasketWrapper changeStatus(BasketWrapper basket, BasketStatus status) {
        ChangeBasketStatusAction changeStatusAction = new ChangeBasketStatusAction(basket, status);

        return redisTemplate.execute(changeStatusAction);
    }

    /**
     * Set basket to redis. It means that this basket is not finished.<br>
     * User will be able to get it.
     */
    public void setBasket(BasketWrapper basket) {
        SetBasketAction setAction = new SetBasketAction(basket);

        redisTemplate.execute(setAction);
    }

    /**
     * Replace basket and ignore status.<br>
     *
     *
     */
    public BasketWrapper updateBasket(BasketWrapper basket) {
        boolean replaceMode = true;
        SetBasketAction setAction = new SetBasketAction(basket, replaceMode);

        return redisTemplate.execute(setAction);
    }

    public BasketWrapper deleteBasket(BasketWrapper basket) {
        DeleteBasketAction deleteAction = new DeleteBasketAction(basket);

        return redisTemplate.execute(deleteAction);

    }

    private boolean isValid(BasketWrapper basket) {
        if (basket == null) {
            log.error("basket is null");
            return false;
        }
        if (basket.getSalt() == null) {
            log.error("basket salt is null");
            return false;
        }
        if (basket.getCurrencyCode() == null) {
            log.error("basket currency code is null");
            return false;
        }
        if (basket.getBaseCurrencyCode() == null) {
            log.error("basket base currency code is null");
            return false;
        }

        return true;
    }

    // test commit
    private class SetBasketAction implements SessionCallback<BasketWrapper> {

        private BasketWrapper basket;

        private boolean replaceMode;

        public SetBasketAction(BasketWrapper basket) {
            this.basket = basket;
            this.replaceMode = false;
        }

        public SetBasketAction(BasketWrapper basket, boolean replaceMode) {
            this.basket = basket;
            this.replaceMode = replaceMode;
        }

        @Override
        @SuppressWarnings({"unchecked"})
        public BasketWrapper execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws
                DataAccessException {
            BasketWrapper result = null;
            try {
                if (!isValid(basket))
                    return null;

                log.info("Try store bakset in redis, salt: " + basket.getSalt() + " liveTime: " + basket
                        .getLiveSeconds() + " openTime: "
                        + basket.getOpenSeconds() + " replace mode: " + replaceMode);

                String currCode = basket.getCurrencyCode();
                String baseCurrCode = basket.getBaseCurrencyCode();
                String key = currCode + baseCurrCode;
                long salt = basket.getSalt();

                while (true) {
                    // watch for changes
                    operations.watch(REDIS_KEY);

                    // get baskets from redis
                    HashOperations<String, String, String> hashOps = operations.opsForHash();
                    String strBasktes = hashOps.get(REDIS_KEY, key);

                    // start transaction
                    operations.multi();

                    List<BasketWrapper> baskets = null;
                    if (strBasktes != null) {
                        CollectionType collectionType = mapper.getTypeFactory()
                                .constructCollectionType(List.class, BasketWrapper.class);
                        baskets = mapper.readValue(strBasktes, collectionType);
                    }

                    if (baskets == null)
                        baskets = new ArrayList<>();

                    // replace basket
                    boolean isReplaced = false;
                    for (int i = 0; i < baskets.size(); i++) {
                        BasketWrapper currBasket = baskets.get(i);
                        long currSalt = currBasket.getSalt();
                        if (currSalt == salt) {
                            // If persist basket is newer than input return
                            BigInteger currBlockNum = (currBasket.getBlockNum() == null) ? BigInteger.valueOf(-1)
                                    : currBasket.getBlockNum();
                            BigInteger blokNum = (basket.getBlockNum() == null) ? BigInteger.valueOf(Long.MAX_VALUE) :
                                    basket.getBlockNum();
                            Integer currTxIndex = (currBasket.getTxIndex() == null) ? -1 : currBasket.getTxIndex();
                            Integer txIndex = (basket.getTxIndex() == null) ? Integer.MAX_VALUE : basket.getTxIndex();

                            int compareResult = currBlockNum.compareTo(blokNum);
                            if (compareResult > 0) {
                                log.info(
                                        "Basket is deprecated more new was write.\nBasket tried to write: " + basket + "\nExist basket: "
                                                + currBasket);
                                operations.discard();
                                break;
                            } else if (compareResult == 0) {
                                if (currTxIndex > txIndex) {
                                    log.info(
                                            "Basket is deprecated more new was write.\nBasket tried to write: " + basket
                                                    + "\nExist basket: " + currBasket);
                                    try {
                                        operations.discard();
                                    } catch (Exception e) {
                                        log.error(e.getMessage());
                                    }
                                    break;
                                }
                            }
                            // leave the statue as it was
                            if (replaceMode)
                                basket.setStatus(currBasket.getStatus());

                            currBasket.merge(basket);
                            // duplicate basket in redis for for possibility get by salt
                            String basketStr = mapper.writeValueAsString(currBasket);
                            hashOps.put(REDIS_KEY, String.valueOf(salt), basketStr);

                            result = currBasket;
                            isReplaced = true;

                            break;
                        }
                    }

                    if (!isReplaced && !replaceMode) {
                        baskets.add(basket);
                        String basketStr = mapper.writeValueAsString(basket);
                        hashOps.put(REDIS_KEY, String.valueOf(salt), basketStr);
                        result = basket;
                    }

                    // set baskets list to redis
                    strBasktes = mapper.writeValueAsString(baskets);
                    hashOps.put(REDIS_KEY, key, strBasktes);
                    // execute transaction
                    List<Object> list = operations.exec();
                    if (list.size() > 0) {
                        log.info("Store bakset in redis was successfully , salt: " + basket.getSalt() + " liveTime: "
                                + basket.getLiveSeconds() + " openTime: " + basket.getOpenSeconds());
                        break;
                    } else
                        log.warn("Can not set basket. Try later.");

                }
            } catch (Exception e) {
                // discard transaction
                try {
                    operations.discard();
                } catch (Exception e1) {
                    log.error(e1.getMessage(), e1);
                }
                log.error(e.getMessage(), e);
            }

            return result;
        }

    }

    private class DeleteBasketAction implements SessionCallback<BasketWrapper> {

        private BasketWrapper basket;

        public DeleteBasketAction(BasketWrapper basket) {
            this.basket = basket;
        }

        @Override
        @SuppressWarnings("unchecked")
        public BasketWrapper execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws
                DataAccessException {
            try {
                if (!isValid(basket))
                    return null;

                String currCode = basket.getCurrencyCode();
                String baseCurrCode = basket.getBaseCurrencyCode();
                String key = currCode + baseCurrCode;

                while (true) {
                    // watch for changes
                    operations.watch(REDIS_KEY);

                    // get baskets from redis
                    HashOperations<String, String, String> hashOps = operations.opsForHash();
                    String strBasktes = hashOps.get(REDIS_KEY, key);

                    // start transaction
                    operations.multi();

                    BasketWrapper finishedBasket = null;
                    long salt = basket.getSalt();

                    List<BasketWrapper> baskets = null;
                    if (strBasktes != null) {
                        CollectionType collectionType = mapper.getTypeFactory()
                                .constructCollectionType(List.class, BasketWrapper.class);
                        baskets = mapper.readValue(strBasktes, collectionType);
                    }

                    if (baskets == null) {
                        operations.exec();
                        return null;
                    }

                    // log.info("Current basket befor delete: " + baskets.size());
                    // delete basket form list
                    Iterator<BasketWrapper> iterator = baskets.iterator();
                    while (iterator.hasNext()) {
                        finishedBasket = iterator.next();
                        long currSalt = finishedBasket.getSalt();
                        if (currSalt == salt) {
                            iterator.remove();
                            hashOps.delete(REDIS_KEY, String.valueOf(salt));
                            break;
                        }
                    }

                    // log.info("Current basket after delete: " + baskets.size());

                    // save list without deleted basket
                    strBasktes = mapper.writeValueAsString(baskets);
                    hashOps.put(REDIS_KEY, key, strBasktes);

                    // execute transaction
                    List<Object> list = operations.exec();
                    if (list.size() > 0)
                        return finishedBasket;
                    else
                        log.warn("Can not delete basket. Try later.");

                }

            } catch (Throwable e) {
                // discard transaction
                try {
                    operations.discard();
                } catch (Exception e1) {
                    log.error(e1.getMessage(), e1);
                }
                log.error(e.getMessage(), e);
            }

            return null;
        }

    }

    private class ChangeBasketStatusAction implements SessionCallback<BasketWrapper> {

        private String currCode;
        private String baseCurrCode;
        private long salt;
        private BasketStatus status;

        public ChangeBasketStatusAction(BasketWrapper basket, BasketStatus status) {
            this.currCode = basket.getCurrencyCode();
            this.baseCurrCode = basket.getBaseCurrencyCode();
            this.salt = basket.getSalt();
            this.status = status;
        }

        @Override
        @SuppressWarnings("unchecked")
        public BasketWrapper execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws
                DataAccessException {
            try {
                String key = currCode + baseCurrCode;

                while (true) {
                    // watch for changes
                    operations.watch(REDIS_KEY);
                    BasketWrapper resultBasket = null;

                    // get list of baskets from redis
                    HashOperations<String, String, String> hashOps = operations.opsForHash();
                    String strBasktes = hashOps.get(REDIS_KEY, key);

                    // start transaction
                    operations.multi();

                    List<BasketWrapper> basketsWrappers = null;
                    if (strBasktes != null) {
                        CollectionType collectionType = mapper.getTypeFactory()
                                .constructCollectionType(List.class, BasketWrapper.class);
                        basketsWrappers = mapper.readValue(strBasktes, collectionType);
                    }
                    if (basketsWrappers == null)
                        basketsWrappers = new ArrayList<>();

                    // change basket status
                    for (BasketWrapper currBasket : basketsWrappers) {
                        if (currBasket.getSalt() == salt) {
                            currBasket.setStatus(status);
                            resultBasket = currBasket;
                            // duplicate basket in redis for for possibility get by salt
                            String strResultBasket = mapper.writeValueAsString(resultBasket);
                            hashOps.put(REDIS_KEY, String.valueOf(salt), strResultBasket);
                            break;
                        }
                    }

                    // set list to redis
                    strBasktes = mapper.writeValueAsString(basketsWrappers);
                    hashOps.put(REDIS_KEY, key, strBasktes);

                    // execute transaction
                    List<Object> list = operations.exec();
                    if (list.size() > 0) {
                        log.info("Basket status was changed successfully salt: " + salt + "  status: " + status);
                        return resultBasket;
                    } else
                        log.warn("Can not change basket status. Try later. salt: " + salt);

                }
            } catch (Throwable e) {
                // discard transaction
                try {
                    operations.discard();
                } catch (Exception e1) {
                    log.error(e1.getMessage(), e1);
                }
                log.error(e.getMessage(), e);
            }

            return null;
        }

    }

}
