package com.vareger.service.tools;

import com.vareger.models.Broker;
import com.vareger.models.User;
import com.vareger.requests.UserParam;
import com.vareger.servicies.BrokerService;
import com.vareger.validators.AddressValidator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParamMapperService {

    private static final int REFERRAL_SUFFIX_LENGTH = 30;

    @Autowired
    private BrokerService brokerService;

    public User generateUserFromParam(UserParam userParam) {
        Broker broker = brokerService.findById(userParam.getBrokerId());
        String clearAddress = AddressValidator.getHexClear(userParam.getAddress());
        String referralSuffix = RandomStringUtils.randomAlphanumeric(REFERRAL_SUFFIX_LENGTH);

        User user = new User();
        user.setAddress(clearAddress);
        user.setBroker(broker);
        user.setEmail(userParam.getEmail());
        user.setReferralSuffix(referralSuffix);

        return user;
    }

    public String generateReferralSuffix() {
        String referralSuffix = RandomStringUtils.randomAlphanumeric(REFERRAL_SUFFIX_LENGTH);

        return referralSuffix;
    }
}
