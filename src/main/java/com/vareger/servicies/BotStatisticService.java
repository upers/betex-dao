package com.vareger.servicies;

import com.vareger.models.BotStatistic;
import com.vareger.models.PageablePayload;
import org.hibernate.criterion.Order;

public interface BotStatisticService extends Service<Integer, BotStatistic> {
    BotStatistic findLast();

    /**
     * Save bot statistic if previous statistic equal like in parameter
     * @param botStatistic new bot statistic
     * @param _prevStatistic old bot statistic
     */
    void save(BotStatistic botStatistic, BotStatistic _prevStatistic);

    PageablePayload<BotStatistic> findAll(int offset, int amount, Order order);
}
