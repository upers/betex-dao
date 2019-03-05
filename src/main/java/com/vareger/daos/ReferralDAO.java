package com.vareger.daos;

import com.vareger.models.Referral;
import com.vareger.models.User;
import com.vareger.models.UserBid;
import com.vareger.validators.AddressValidator;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReferralDAO extends AbstractDao<Integer, Referral> {

    private static final String referralBidsQuery = "SELECT userbid.bid_type, userbid.bid_time, userbid.address, userbid.basket_id, userbid.reward_time, userbid.id, userbid.tx_hash, userbid.amount, userbid.win_amount, userbid.confirmed, userbid.broker_id FROM userbid " +
            "INNER JOIN referral ON userbid.address = referral.address" +
            " INNER JOIN \"user\" ON referral.founder_id = \"user\".id" +
//            " INNER JOIN basket ON userbid.basket_id = basket.id" +
            " WHERE userbid.win_amount IS NOT NULL" +
//            " AND basket.call_amount IS NOT NULL AND basket.put_amount > 0 AND basket.call_amount > 0" +
            " AND \"user\".id = :userId AND referral.address = :referralAddress AND userbid.broker_id = :broker_id AND userbid.id > :from ORDER BY userbid.id;";


    public boolean checkFounder(User founder, String referralAddress) {
        String clearAddress = AddressValidator.getHexClear(referralAddress);
        List<?> list = getSession().createCriteria(Referral.class).createAlias("founder", "founder")
                .add(Restrictions.eq("founder.id", founder.getId())).add(Restrictions.eq("address", clearAddress))
                .list();

        return !list.isEmpty();
    }

//    public List<UserBid> getReferralBidsInFinishRound(@NotNull Referral referral, long from) {
//        return getSession().createSQLQuery("SELECT userbid.bid_type, userbid.bid_time, userbid.address, userbid.basket_id, userbid.reward_time, userbid.id, userbid.tx_hash, userbid.amount, userbid.win_amount, userbid.confirmed, userbid.broker_id FROM userbid INNER JOIN referral\n" +
//                " ON userbid.address = referral.address INNER JOIN \"user\"\n" +
//                " ON referral.founder_id = \"user\".id" +
//                " WHERE userbid.win_amount IS NOT NULL\n" +
//                " AND \"user\".id = :userId AND referral.address = :referralAddress AND userbid.broker_id = :broker_id AND userbid.id > :from ORDER BY userbid.id;").addEntity(UserBid.class)
//                .setParameter("referralAddress", referral.getAddress()).setParameter("userId", referral.getFounder().getId())
//                .setParameter("broker_id", referral.getFounder().getBroker().getId()).setParameter("from", from).list();
//    }

    public List<UserBid> getReferralBidsInFinishRound(Referral referral, long from) {
        return getSession().createSQLQuery(referralBidsQuery).addEntity(UserBid.class)
                .setParameter("referralAddress", referral.getAddress()).setParameter("userId",
                        referral.getFounder().getId())
                .setParameter("broker_id", referral.getFounder().getBroker().getId()).setParameter("from", from).list();
    }

    public List<Referral> findByAddress(String address) {
        return createEntityCriteria().add(Restrictions.eq("address", address)).list();
    }
}
