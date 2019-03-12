package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.jpa.converter.BigIntegerConverter;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Table(name = "bot_statistic")
public class BotStatistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "bid_amount")
    private Integer bidAmount;

    @Column(name = "bid_volume")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger bidVolume;

    @Column(name = "win_volume")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger winVolume;

    @Column(name = "lose_volume")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger loseVolume;

    @Column(name = "lose_amount")
    private Integer loseAmount;

    @Column(name = "win_amount")
    private Integer winAmount;

    @Column(name = "current_balance")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger currentBalance;

    @Column(name = "current_balance")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger inputWei;

    @Column(name = "update_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = DoubleToDateDeserializer.class)
    @JsonSerialize(using= DateToTimeestampSerializerNumeric.class)
    private Date updateTime;

    @Column(name = "gas_fee")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger gasFee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition = "last_checked_userbid_id")
    private UserBid userBid;

    @Column(name = "last_checked_block")
    private Long lastCheckerBlock;

    @Column(name = "last_checked_index")
    private Integer lastCheckerIndex;

}
