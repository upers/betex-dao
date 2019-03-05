package com.vareger.servicies;

import com.vareger.models.BotConfig;
import com.vareger.models.Pair;

import java.util.Optional;

public interface BotConfigService extends Service<Integer, BotConfig> {
    /**
     * Find BotConfig by arg parameters
     * pair
     * liveSeconds
     * openSeconds
     * @return
     */
    Optional<BotConfig> findByPairAndIntervals(Pair pair, Long liveSeconds, Long openSeconds);
}
