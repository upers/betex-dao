package com.vareger.redis.models;


import com.vareger.models.Broker;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class BrokerInfo {

    private Integer id;

    private String brokerAddress;

    private String reserveAddress;

    private Short founderPercent;

    private String domainNet;

    private BigDecimal commissionDebt;

    private Integer decimals;

    public BrokerInfo() {}

    public BrokerInfo(Broker broker, BigInteger commissionDebt, Integer decimals) {
        this.id = broker.getId();
        this.brokerAddress = broker.getBrokerAddress();
        this.reserveAddress = broker.getReserveAddress();
        this.founderPercent = broker.getFounderPercent();
        this.domainNet = broker.getDomainNet();
        this.decimals = decimals;
        this.commissionDebt = new BigDecimal(commissionDebt, decimals).stripTrailingZeros();
    }
}
