package com.vareger.servicies.impl;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.ReferralUnpaidStatisticDAO;
import com.vareger.models.PageablePayload;
import com.vareger.models.Referral;
import com.vareger.models.ReferralUnpaidStatistic;
import com.vareger.servicies.ReferralUnpaidStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ReferralUnpaidStatisticServiceImpl extends ServiceImpl<Integer, ReferralUnpaidStatistic> implements
        ReferralUnpaidStatisticService {

    private ReferralUnpaidStatisticDAO referralUnpaidStatisticDAO;

    @Autowired
    public ReferralUnpaidStatisticServiceImpl(
            @Qualifier("referralUnpaidStatisticDAO") AbstractDao<Integer, ReferralUnpaidStatistic> referralUnpaidStatisticDAO) {
        super(referralUnpaidStatisticDAO);
        this.referralUnpaidStatisticDAO = (ReferralUnpaidStatisticDAO) referralUnpaidStatisticDAO;
    }

    @Override
    public ReferralUnpaidStatistic findByReferral(Referral referral) {
        Integer id = referral.getId();
        return referralUnpaidStatisticDAO.findById(id);
    }

    public List<ReferralUnpaidStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix) {
        return referralUnpaidStatisticDAO.getReferralStatisticsByReferralSuffix(referralSuffix);
    }

    /**
     * lastPageIndex counted like lastPageIndex = (rowsAmount != amount) ? (rowsAmount % amount == 0 ) ? rowsAmount / amount - 1 : rowsAmount / amount : 0
     *
     * referralSuffix
     * offset
     * amount
     * @return
     */
    public PageablePayload<ReferralUnpaidStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix,
                                                                                          int offset, int amount) {
        Long rowsAmount = referralUnpaidStatisticDAO.getCountByReferralSuffix(referralSuffix);
        Long lastPageIndex =
                (rowsAmount != amount) ?
                        (rowsAmount % amount == 0 && rowsAmount != 0) ? rowsAmount / amount - 1 : rowsAmount / amount :
                        0;
        List<ReferralUnpaidStatistic> statistics = referralUnpaidStatisticDAO.getReferralStatisticsByReferralSuffix(
                referralSuffix, offset, amount);

        return new PageablePayload<ReferralUnpaidStatistic>(statistics, rowsAmount.intValue(),
                lastPageIndex.intValue());
    }

}
