package com.vareger.servicies.impl;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.BotStatisticDAO;
import com.vareger.models.BotStatistic;
import com.vareger.servicies.BotStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class BotStatisticServiceImpl extends ServiceImpl<Integer, BotStatistic> implements BotStatisticService {

    private BotStatisticDAO botStatisticDAO;

    public BotStatisticServiceImpl(AbstractDao<Integer, BotStatistic> botStatisticDAO) {
        super(botStatisticDAO);
        botStatisticDAO = botStatisticDAO;
    }

    @Override
    public BotStatistic findLast() {
        return botStatisticDAO.findLast();
    }
}
