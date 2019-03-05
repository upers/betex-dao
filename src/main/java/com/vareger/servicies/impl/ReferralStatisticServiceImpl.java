package com.vareger.servicies.impl;

import com.vareger.daos.AbstractDao;
import com.vareger.daos.ReferralDAO;
import com.vareger.daos.ReferralStatisticDAO;
import com.vareger.models.*;
import com.vareger.servicies.ReferralStatisticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class ReferralStatisticServiceImpl extends ServiceImpl<Integer, ReferralStatistic> implements
        ReferralStatisticService {

    private ReferralStatisticDAO referralStatisticDAO;

    private ReferralDAO referralDAO;

    @Autowired
    public ReferralStatisticServiceImpl(@Qualifier("referralStatisticDAO") AbstractDao<Integer, ReferralStatistic> referralStatisticDAO,
                                        @Qualifier("referralDAO") AbstractDao<Integer, Referral> referralDAO) {
        super(referralStatisticDAO);
        this.referralStatisticDAO = (ReferralStatisticDAO) referralStatisticDAO;
        this.referralDAO = (ReferralDAO) referralDAO;
    }

    @Override
    public ReferralStatistic findByReferral(Referral referral) {
        Integer id = referral.getId();
        return referralStatisticDAO.findById(id);
    }


    public List<ReferralStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix) {
        return referralStatisticDAO.getReferralStatisticsByReferralSuffix(referralSuffix);
    }
    /**
     * lastPageIndex counted like lastPageIndex = (rowsAmount != amount) ? (rowsAmount % amount == 0 ) ? rowsAmount / amount - 1 : rowsAmount / amount : 0;
     * referralSuffix
     * offset
     * amount
     * @return
     */
    public PageablePayload<ReferralStatistic> getReferralStatisticsByReferralSuffix(String referralSuffix, int offset, int amount) {
        Long rowsAmount = referralStatisticDAO.getCountByReferralSuffix(referralSuffix);
        Long lastPageIndex = (rowsAmount != amount) ? (rowsAmount % amount == 0 && rowsAmount != 0) ? rowsAmount / amount - 1 : rowsAmount / amount : 0;
        List<ReferralStatistic> statistics = referralStatisticDAO.getReferralStatisticsByReferralSuffix(
                referralSuffix, offset, amount);

        return new PageablePayload<ReferralStatistic>(statistics, rowsAmount.intValue(), lastPageIndex.intValue());
    }

}
