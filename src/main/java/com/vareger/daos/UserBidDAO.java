package com.vareger.daos;

import com.vareger.models.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserBidDAO extends AbstractDao<Integer, UserBid> {

    @SuppressWarnings("unchecked")
    public List<UserBid> getByUserAddress(String userAddress) {
        return getSession().createCriteria(UserBid.class).add(Restrictions.eq("address", userAddress))
                .addOrder(Order.desc("id")).list();
    }

    /**
     * Get users bids.
     *
     * @param addresses users address
     * @param from      user bid id from which the search starts
     * @return List of users bids
     */
    public List<UserBid> getSuccessByUserAddresses(List<String> addresses, int from) {
        Set<Integer> basketsId = ((List<UserBid>) createEntityCriteria()
                .createAlias("basket", "basket")
                .add(Restrictions.eq("basket.status", BasketStatus.FINISHED))
                .add(Restrictions.gt("id", from)).list()).stream().map(UserBid::getBasket).map(Basket::getId)
                .collect(Collectors.toSet());

        return createEntityCriteria().add(Restrictions.in("basket.id", basketsId))
                .add(Restrictions.in("address", addresses))
                .addOrder(Order.desc("id")).list();
    }

    public List<UserBid> getAllFromBaskets(Collection<Integer> basketsIds) {
        if (basketsIds == null || basketsIds.isEmpty())
            return new ArrayList<>();
        return createEntityCriteria().add(Restrictions.in("basket.id", basketsIds)).addOrder(Order.desc("id")).list();
    }

    @SuppressWarnings("unchecked")
    public List<UserBid> getActiveByUserAddress(String userAddress) {
        return createEntityCriteria().add(Restrictions.eq("address", userAddress))
                .add(Restrictions.isNull("rewardDate"))
                .add(Restrictions.isNull("winAmount")).list();
    }

    @SuppressWarnings("unchecked")
    public List<UserBid> getActive() {
        return createEntityCriteria().add(Restrictions.isNull("rewardDate"))
                .add(Restrictions.isNull("winAmount")).list();
    }

    public UserBid findByTxHash(String txHash) {
        return (UserBid) createEntityCriteria().add(Restrictions.eq("txHash", txHash)).uniqueResult();
    }

    /**
     * Find user by basket <i>salt</i> and <i>lastCheckedBid.address</i> if no fount create
     * user and basket if needed.<br>
     * If <i>UserBid.address</i> is NULL throw {@link IllegalStateException}<br>
     * If <i>salt</i> is NULL throw {@link IllegalStateException} If <i>BidType</i>
     * is NULL throw {@link IllegalStateException}
     * <p>
     * salt
     * address
     * bidType
     *
     * @return {@link UserBid}
     */
    @SuppressWarnings("unchecked")
    public List<UserBid> findNotRewardedUsers(Long salt, String address, BidType bidType, BigInteger amount) {
        if (address == null)
            throw new IllegalArgumentException("Address hasn't be NULL");

        if (salt == null)
            throw new IllegalArgumentException("Salt hasn't be NULL");

        if (bidType == null)
            throw new IllegalArgumentException("BidType hasn't be NULL");

        List<UserBid> bids = createEntityCriteria().createAlias("basket", "basket")
                .add(Restrictions.eq("address", address))
                .add(Restrictions.eq("txStatus", TransactionStatus.SUCCESS))
                .add(Restrictions.eq("bidType", bidType)).add(Restrictions.eq("amount", amount))
                .add(Restrictions.eq("basket.salt", salt))
                .add(Restrictions.isNull("rewardDate")).add(Restrictions.isNull("winAmount")).list();

        return bids;
    }

    @SuppressWarnings("unchecked")
    public List<UserBid> findSuccessBidsFromSpecifiedDate(Date from) {
        Set<Integer> basketsId = ((List<UserBid>) createEntityCriteria()
                .createAlias("basket", "basket")
                .add(Restrictions.eq("basket.status", BasketStatus.FINISHED))
                .add(Restrictions.gt("bidDate", from)).list()).stream().map(UserBid::getBasket).map(Basket::getId)
                .collect(Collectors.toSet());

        return createEntityCriteria().add(Restrictions.in("basket.id", basketsId)).list();
    }


    @SuppressWarnings("unchecked")
    public List<UserBid> getByBasketSalt(Long salt) {
        return getSession().createCriteria(UserBid.class).createAlias("basket", "basket")
                .add(Restrictions.eq("basket.salt", salt)).list();
    }

    @SuppressWarnings("unchecked")
    public List<UserBid> getFromSpecifiedDate(Date from) {
        return createEntityCriteria().addOrder(Order.desc("id")).add(Restrictions.gt("bidDate", from)).list();
    }

}
