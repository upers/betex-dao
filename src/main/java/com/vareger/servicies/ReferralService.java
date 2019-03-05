package com.vareger.servicies;

import com.vareger.models.Referral;
import com.vareger.models.ReferralStatistic;
import com.vareger.models.User;
import com.vareger.models.UserBid;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface ReferralService extends Service<Integer, Referral> {

    boolean checkFounder(User founder, String referralAddress);

    void createReferral(User founder, String referralAddress);

    /**
     * Get referral bids from bid id in second parameter.
     *
     * referral
     * from
     * @return
     */
    List<UserBid> getReferralBidsInFinishRound(@NotNull Referral referral, long from);

    List<Referral> findByAddress(String address);

    boolean hasFounderForBrokerId(String address, Integer brokerId);

}
