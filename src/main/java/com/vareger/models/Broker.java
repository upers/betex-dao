package com.vareger.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "broker")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Broker {
	
	@Id
	private Integer id;
	
	@Column(name="broker_address")
	private String brokerAddress;
	
	@Column(name="reserve_address")
	private String reserveAddress;
	
	@Column(name="founder_percent")
	private Short founderPercent;
	
	@Column(name="domain_name")
	private String domainNet;
	
	@OneToMany(mappedBy="broker")
	private List<ReferralIncome> foundersIncome;
	
	public Broker() {
		
	}
	
	public Broker (int id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrokerAddress() {
		return brokerAddress;
	}

	public void setBrokerAddress(String brokerAddress) {
		this.brokerAddress = brokerAddress;
	}

	public String getReserveAddress() {
		return reserveAddress;
	}

	public void setReserveAddress(String reserveAddress) {
		this.reserveAddress = reserveAddress;
	}

	public Short getFounderPercent() {
		return founderPercent;
	}

	public void setFounderPercent(Short founderPercent) {
		this.founderPercent = founderPercent;
	}

	public String getDomainNet() {
		return domainNet;
	}

	public void setDomainNet(String domainNet) {
		this.domainNet = domainNet;
	}

	public List<ReferralIncome> getFoundersIncome() {
		return foundersIncome;
	}

	public void setFoundersIncome(List<ReferralIncome> foundersIncome) {
		this.foundersIncome = foundersIncome;
	}
	
	@Override
	public boolean equals(Object comparable) {
		if (this == comparable) {
            return true;
        }
		if (comparable instanceof Broker) {
			if (((Broker) comparable).id == this.id)
				return true;
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		if (id == null)
			return -1;
		
		return id.hashCode();
	}
	
	@Override
	public String toString() {
		return "Broker [id=" + id + ", brokerAddress=" + brokerAddress + ", reserveAddress=" + reserveAddress + ", founderPercent=" + founderPercent
				+ ", domainNet=" + domainNet + "]";
	}
	
}
