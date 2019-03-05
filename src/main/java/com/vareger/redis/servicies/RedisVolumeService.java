package com.vareger.redis.servicies;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vareger.redis.models.Bot24Volume;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class RedisVolumeService {

    public static final String REDIS_24h_AGO_VOLUME = "redis_24h_ago_volume";
    public static final String REDIS_24h_AGO_VOLUME_BROKER = "redis_24h_ago_volume_broker";
    public static final String REDIS_24h_AGO_VOLUME_BOT = "redis_24h_ago_bot";
    private static final Long ms24h = 86400000l;
    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper mapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private ValueOperations<String, String> valOps;

    @PostConstruct
    public void postConstructor() {
        valOps = redisTemplate.opsForValue();
    }

    public BigInteger get24hAgoVolume() throws IOException {
        String volumeStr = valOps.get(REDIS_24h_AGO_VOLUME);
        if (volumeStr == null)
            return null;

        return new BigInteger(volumeStr);
    }

    public void set24hAgoVolume(BigInteger volume) {
        String volumeStr = volume.toString(10);
        valOps.set(REDIS_24h_AGO_VOLUME, volumeStr);
    }

    public BigInteger get24hAgoVolumeBroker(int brokerId) throws IOException {
        String volumeStr = valOps.get(REDIS_24h_AGO_VOLUME_BROKER + brokerId);
        if (volumeStr == null)
            return null;

        return new BigInteger(volumeStr);
    }

    public void set24hAgoVolumeBroker(int brokerId, BigInteger volume) {
        String volumeStr = volume.toString(10);
        valOps.set(REDIS_24h_AGO_VOLUME_BROKER + brokerId, volumeStr);
    }

    public void add24hAgoVolumeBot(BigInteger val) {
        BotVolumeAdder changeStatusAction = new BotVolumeAdder(val);
        redisTemplate.execute(changeStatusAction);
    }

    public Bot24Volume get24hAgoVolumeBot() {
        String volumeStr = valOps.get(REDIS_24h_AGO_VOLUME_BOT);
        Bot24Volume volume = null;
        try {
            volume = mapper.readValue(volumeStr, Bot24Volume.class);
            resetVolumeIfNewDay(volume);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        if (volume == null)
            return new Bot24Volume(BigInteger.ZERO, new Date());

        return volume;
    }

    private void resetVolumeIfNewDay(Bot24Volume bot24Volume) {
        //If new day set volume to 0
        long currTime = System.currentTimeMillis();
        long remainderOfDivision = currTime % ms24h;
        Date beginningOfTheDay = new Date(currTime - remainderOfDivision);
        if (bot24Volume.getUpdateDate().before(beginningOfTheDay))
            bot24Volume.setVolume(BigInteger.ZERO);

    }

    private class BotVolumeAdder implements SessionCallback<Bot24Volume> {

        private BigInteger val;

        public BotVolumeAdder(BigInteger val) {
            this.val = val;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Bot24Volume execute(@SuppressWarnings("rawtypes") RedisOperations operations) throws
                DataAccessException {
            try {
                while (true) {
                    // watch for changes
                    operations.watch(REDIS_24h_AGO_VOLUME_BOT);

                    ValueOperations<String, String> valOps = operations.opsForValue();

                    String str24Volume = valOps.get(REDIS_24h_AGO_VOLUME_BOT);
                    Bot24Volume bot24Volume;
                    try {
                        bot24Volume = mapper.readValue(str24Volume, Bot24Volume.class);
                    } catch (Exception e) {
                        bot24Volume = new Bot24Volume(BigInteger.ZERO, new Date());
                    }
                    log.info("Old volume: " + bot24Volume.getVolume());
                    //If new day set volume to 0
                    resetVolumeIfNewDay(bot24Volume);

                    // start transaction
                    operations.multi();

                    BigInteger oldVolume = bot24Volume.getVolume();
                    BigInteger newVolume = oldVolume.add(this.val);
                    bot24Volume.setVolume(newVolume);
                    bot24Volume.setUpdateDate(new Date());

                    String bot24VolumeStr = mapper.writeValueAsString(bot24Volume);
                    valOps.set(REDIS_24h_AGO_VOLUME_BOT, bot24VolumeStr);
                    //For result in list operations((((( Now way to do it
                    valOps.get(REDIS_24h_AGO_VOLUME_BOT);
                    // execute transaction
                    List<Object> list = operations.exec();
                    if (list.size() > 0) {
                        log.info("Bot 24 volume changed success. old24Volume: " + oldVolume + " New volume: " + newVolume + "  added: " + this.val);
                        return bot24Volume;
                    } else
                        log.warn("Can not change Bot24Volume. Try later.");

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

        public void resetVolume() {

        }

    }


}
