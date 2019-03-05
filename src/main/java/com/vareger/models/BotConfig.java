package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vareger.jpa.converter.BigIntegerConverter;
import com.vareger.jpa.converter.LocalTimeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalTime;

@Entity
@Table(name = "bot_config")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BotConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "max_bid")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger maxBid;

    @Column(name = "min_bid")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger minBid;

    @Column(name = "max_alignment_bid")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger maxAlignmentBid;

    @Column(name = "max_amount")
    private Integer maxAmount;

    @Column(name = "min_amount")
    private Integer minAmount;

    @Column(name = "bid_type_ratio")
    private Float bidTypeRatio;

    @Column(name = "allowed_time_from")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime allowedTimeFrom;

    @Column(name = "allowed_time_to")
    @Convert(converter = LocalTimeConverter.class)
    private LocalTime allowedTimeTo;

    @Column(name = "max_bot_24_volume")
    @Convert(converter = BigIntegerConverter.class)
    private BigInteger maxBot24Volume;

    @Column(name = "live_seconds")
    private Long liveSeconds;

    @Column(name = "open_seconds")
    private Long openSeconds;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "bid_interval_ms")
    private Long bidIntervalMs;

    @Column(name = "first_delay_ms")
    private Long firstDelayMs;

    @Column(name = "participation_percent")
    private Float participationPercent;

    @OneToOne
    @JoinColumn(columnDefinition = "broker_id")
    private Broker broker;

    @OneToOne
    @JoinColumn(columnDefinition = "pair_id")
    private Pair pair;

}
