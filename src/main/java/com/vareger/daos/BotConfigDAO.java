package com.vareger.daos;

import com.vareger.models.BotConfig;
import com.vareger.models.Pair;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class BotConfigDAO extends AbstractDao<Integer, BotConfig> {

    public BotConfig findByPairAndIntervals(Pair pair, Long liveSeconds, Long openSeconds) {
        return (BotConfig) createEntityCriteria().add(Restrictions.eq("pair", pair))
                .add(Restrictions.eq("liveSeconds", liveSeconds))
                .add(Restrictions.eq("openSeconds", openSeconds))
                .uniqueResult();
    }
}
