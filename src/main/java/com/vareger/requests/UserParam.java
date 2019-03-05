package com.vareger.requests;

import com.vareger.validators.ValidAddress;
import com.vareger.validators.ValidBrokerId;
import com.vareger.validators.ValidEmail;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserParam {
    @ValidEmail
    private String email;

    @ValidAddress
    private String address;

    @ValidBrokerId
    private Integer brokerId;
}
