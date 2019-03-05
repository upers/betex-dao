package com.vareger.servicies;

import com.vareger.models.PageablePayload;
import com.vareger.models.Referral;
import com.vareger.models.ReferralUnpaidStatistic;

import java.util.List;

public interface ReferralUnpaidStatisticService extends Service<Integer, ReferralUnpaidStatistic> {
    ReferralUnpaidStatistic findByReferral(Referral referral);

    PageablePayload<ReferralUnpaidStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix, int offset,
                                                                                   int amount);

    List<ReferralUnpaidStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix);
}
