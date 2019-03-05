package com.vareger.daos;

import com.vareger.models.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends AbstractDao<Integer, User> {

    public User getByAddressAndBrokerId(String publicKey, Integer brokerId) {
        return (User) getSession().createCriteria(User.class).createAlias("broker", "broker")
                .add(Restrictions.eq("address", publicKey)).add(Restrictions.eq("broker.id", brokerId)).uniqueResult();
    }

    public User getByReferralSuffix(String referralSuffix) {
        return (User) createEntityCriteria().add(Restrictions.eq("referralSuffix", referralSuffix)).uniqueResult();
    }

}
