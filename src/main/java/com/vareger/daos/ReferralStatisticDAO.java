package com.vareger.daos;

import com.vareger.models.ReferralStatistic;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReferralStatisticDAO extends AbstractDao<Integer, ReferralStatistic> {

    public Long getCount() {
        return (Long) createEntityCriteria().setProjection(Projections.rowCount()).uniqueResult();
    }

    public Long getCountByReferralSuffix(String referralSuffix) {
        return (Long) createEntityCriteria().createAlias("referral", "referral").createAlias("referral.founder",
                "founder").add(
                Restrictions.eq("founder.referralSuffix", referralSuffix)).setProjection(Projections.rowCount()).uniqueResult();
    }

    private Criteria getCriteriaByReferralSuffix(String referralSuffix) {
        return createEntityCriteria().createAlias("referral", "referral").createAlias("referral.founder",
                "founder").add(
                Restrictions.eq("founder.referralSuffix", referralSuffix)).addOrder(
                Order.desc("bidAmount"));
    }

    public List<ReferralStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix) {
        return getCriteriaByReferralSuffix(referralSuffix).list();
    }

    public List<ReferralStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix, int offset,
                                                                         int amount) {
        return getCriteriaByReferralSuffix(referralSuffix).setFirstResult(offset).setMaxResults(amount).list();
    }

}
