package com.vareger.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vareger.json.serialization.DateToTimeestampSerializerNumeric;
import com.vareger.json.serialization.DoubleToDateDeserializer;

@Entity
@Table(name = "capitalcoverage")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CapitalCoverage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "time")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonDeserialize(using = DoubleToDateDeserializer.class)
	@JsonSerialize(using=DateToTimeestampSerializerNumeric.class)
	private Date date;
	
	@Column(name="real_sbt")
	private Double realTotalSupplySbt;
	
	@Column(name="contract_sbt")
	private Double contractSbt;
	
	@Column(name="total_sbt")
	private Double totalSupplySbt;
	
	@Column(name="reserve_eth")
	private Double reserveEth;
	
	@Column(name="eth_rate")
	private Double ethRate;
	
	@Column(name="eth_reserve_in_usd")
	private Double reserveEthInUsd;
	
	@Column(name="reserve_usd")
	private Double reserveUsd;
	
	@Column(name="total_reserve")
	private Double totalReserve;
	
	@Column(name="coverage_percent")
	private Double coveragePercent;
	
	public Double getRealTotalSupplySbt() {
		return realTotalSupplySbt;
	}

	public void setRealTotalSupplySbt(Double realTotalSupplySbt) {
		this.realTotalSupplySbt = realTotalSupplySbt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getReserveEth() {
		return reserveEth;
	}

	public void setReserveEth(Double reserveEth) {
		this.reserveEth = reserveEth;
	}

	public Double getEthRate() {
		return ethRate;
	}

	public void setEthRate(Double ethRate) {
		this.ethRate = ethRate;
	}

	public Double getReserveUsd() {
		return reserveUsd;
	}

	public void setReserveUsd(Double reserveUsd) {
		this.reserveUsd = reserveUsd;
	}

	public Double getTotalReserve() {
		return totalReserve;
	}

	public void setTotalReserve(Double totalReserve) {
		this.totalReserve = totalReserve;
	}

	public Double getCoveragePercent() {
		return coveragePercent;
	}

	public void setCoveragePercent(Double coveragePercent) {
		this.coveragePercent = coveragePercent;
	}
	
	public Double getContractSbt() {
		return contractSbt;
	}

	public void setContractSbt(Double contractSbt) {
		this.contractSbt = contractSbt;
	}

	public Double getTotalSupplySbt() {
		return totalSupplySbt;
	}

	public void setTotalSupplySbt(Double totalSupplySbt) {
		this.totalSupplySbt = totalSupplySbt;
	}

	@Override
	public String toString() {
		return "CapitalCoverage [id=" + id + ", date=" + date + ", realTotalSupplySbt=" + realTotalSupplySbt + ", contractSbt="
				+ contractSbt + ", totalSupplySbt=" + totalSupplySbt + ", reserveEth=" + reserveEth + ", ethRate=" + ethRate
				+ ", reserveUsd=" + reserveUsd + ", totalReserve=" + totalReserve + ", coveragePercent=" + coveragePercent + "]";
	}

	public Double getReserveEthInUsd() {
		return reserveEthInUsd;
	}

	public void setReserveEthInUsd(Double reserveEthInUsd) {
		this.reserveEthInUsd = reserveEthInUsd;
	}

	
}
