package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;
import com.vareger.validators.ValidAddress;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "referral", schema = "public")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Data
public class Referral {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "address")
    @ValidAddress
    @NotNull
    private String address;

    @Column(name = "registration_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = DoubleToDateDeserializer.class)
    @JsonSerialize(using= DateToTimeestampSerializerNumeric.class)
    private Date registrationTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(columnDefinition = "founder_id")
    private User founder;
}
