package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.jpa.converter.BigIntegerConverter;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = "referral_unpaid_statistic")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
public class ReferralUnpaidStatistic {
    @Id
    @Column(name = "referral_id")
    private Integer referralId;

    @Column(name = "bid_amount")
    private Integer bidAmount;

    @Column(name = "bid_sum")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger bidSum;

    @Column(name = "avg_bid")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger avgBid;

    @Column(name = "founder_commission")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger founderCommission;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "last_paid_bid_id")
    private UserBid lastPaidBid;

    @Column(name = "last_updated")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = DoubleToDateDeserializer.class)
    @JsonSerialize(using = DateToTimeestampSerializerNumeric.class)
    private Date lastUpdated;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "referral_id")
    @MapsId
    private Referral referral;
}
