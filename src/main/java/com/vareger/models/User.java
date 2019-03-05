package com.vareger.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vareger.validators.ValidAddress;
import com.vareger.validators.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    @ValidEmail
    @NotNull
    private String email;

    @Column(name = "address")
    @ValidAddress
    @NotNull
    private String address;

    @Column(name = "referral_suffix")
    private String referralSuffix;

    @OneToOne
    @JoinColumn(columnDefinition = "broker_id")
    private Broker broker;

    @Column(name = "confirmed", columnDefinition = "boolean default false", nullable = false)
    private Boolean confirmed;

    @OneToMany(mappedBy = "founder")
    @JsonIgnore
    private List<Referral> referrals;

    public User() {
        confirmed = false;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getReferralSuffix() {
        return referralSuffix;
    }

    public void setReferralSuffix(String referralSuffix) {
        this.referralSuffix = referralSuffix;
    }

    public List<Referral> getReferrals() {
        return referrals;
    }

    public void setReferrals(List<Referral> referrals) {
        this.referrals = referrals;
    }

    public Broker getBroker() {
        return broker;
    }

    public void setBroker(Broker broker) {
        this.broker = broker;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", referralSuffix='" + referralSuffix + '\'' +
                ", confirmed=" + confirmed +
                '}';
    }
}
