package com.vareger.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vareger.models.BidType;
import com.vareger.validators.ValidAddress;
import com.vareger.validators.ValidBrokerId;
import com.vareger.validators.ValidTxHash;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserBidDto {
    @ValidTxHash
    private String id;
    @NotNull
    private BidType bidType;
    @NotNull
    private BigInteger amount;
    @NotNull
    private Long basketSalt;
    @ValidBrokerId
    private Integer brokerId;
    @ValidAddress
    private String address;
}
