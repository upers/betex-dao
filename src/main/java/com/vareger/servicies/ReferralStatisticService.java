package com.vareger.servicies;

import com.vareger.models.PageablePayload;
import com.vareger.models.Referral;
import com.vareger.models.ReferralStatistic;

import java.util.List;

public interface ReferralStatisticService extends Service<Integer, ReferralStatistic> {
    ReferralStatistic findByReferral(Referral referral);

    PageablePayload<ReferralStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix, int offset,
                                                                             int amount);

    List<ReferralStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix);
}
