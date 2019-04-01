package com.vareger.servicies.impl;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.BotStatisticDAO;
import com.vareger.models.BotStatistic;
import com.vareger.models.PageablePayload;
import com.vareger.servicies.BotStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.IllegalTransactionStateException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

@Service
@Transactional
@Slf4j
public class BotStatisticServiceImpl extends ServiceImpl<Integer, BotStatistic> implements BotStatisticService {

    private BotStatisticDAO botStatisticDAO;

    public BotStatisticServiceImpl(AbstractDao<Integer, BotStatistic> botStatisticDAO) {
        super(botStatisticDAO);
        this.botStatisticDAO = (BotStatisticDAO) botStatisticDAO;
    }

    @Override
    public BotStatistic findLast() {
        return botStatisticDAO.findLast();
    }

    /**
     * Not safe and fast way to aggregate data in one transaction for statistic
     *
     * @param botStatistic
     */
    @Deprecated
    public void save(BotStatistic botStatistic) {
        throw new NotImplementedException();
    }

    @Transactional(
            isolation = Isolation.SERIALIZABLE
    )
    public void save(BotStatistic botStatistic, BotStatistic _prevStatistic) {
        BotStatistic prevStatistic = botStatisticDAO.findLast();
        if (prevStatistic != null && !prevStatistic.getId().equals(prevStatistic.getId()))
            throw new IllegalTransactionStateException("Table was modified while you aggregate data");

        botStatisticDAO.save(botStatistic);
    }

    /**
     * lastPageIndex counted like lastPageIndex = (rowsAmount != amount) ? (rowsAmount % amount == 0 ) ? rowsAmount / amount - 1 : rowsAmount / amount : 0
     *
     * @param offset
     * @param amount
     * @param order
     * @return
     */
    public PageablePayload<BotStatistic> findAll(int offset, int amount, Order order) {
        Long rowsAmount = botStatisticDAO.getCount();
        Long lastPageIndex =
                (rowsAmount != amount) ?
                        (rowsAmount % amount == 0 && rowsAmount != 0) ? rowsAmount / amount - 1 : rowsAmount / amount :
                        0;
        List<BotStatistic> statistics = botStatisticDAO.findAll(offset, amount, order);

        return new PageablePayload<>(statistics, rowsAmount.intValue(),
                lastPageIndex.intValue());
    }
}
