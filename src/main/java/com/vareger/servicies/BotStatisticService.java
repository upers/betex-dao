package com.vareger.servicies;

import com.vareger.models.BotStatistic;

public interface BotStatisticService extends Service<Integer, BotStatistic> {
    public BotStatistic findLast();
}
