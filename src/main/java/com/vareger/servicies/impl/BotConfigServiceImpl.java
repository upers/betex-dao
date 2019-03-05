package com.vareger.servicies.impl;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.BotConfigDAO;
import com.vareger.models.Basket;
import com.vareger.models.BotConfig;
import com.vareger.models.Pair;
import com.vareger.servicies.BotConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BotConfigServiceImpl extends ServiceImpl<Integer, BotConfig> implements BotConfigService {
    private BotConfigDAO botConfigDAO;

    public BotConfigServiceImpl(AbstractDao<Integer, BotConfig> botConfigDAO) {
        super(botConfigDAO);
        this.botConfigDAO = (BotConfigDAO) botConfigDAO;
    }

    public Optional<BotConfig> findByPairAndIntervals(Pair pair, Long liveSeconds, Long openSeconds) {
        BotConfig botConfig = botConfigDAO.findByPairAndIntervals(pair, liveSeconds, openSeconds);
        System.out.println(botConfig);
        if (botConfig == null)
            return Optional.empty();

        return Optional.of(botConfig);
    }

}
