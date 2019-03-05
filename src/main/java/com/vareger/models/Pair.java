package com.vareger.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="pair")
public class Pair {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="currency_id")
	@NotNull
	private Currency currency;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="base_currency_id")
	@NotNull
	private Currency baseCurrency;
	
	@Column(name="format")
	private String format;
	
	@Column(name="active")
	private Boolean enabled;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Currency getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(Currency baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean active) {
		this.enabled = active;
	}

	@Override
	public String toString() {
		return "Pair{" +
				"id=" + id +
				", currency=" + currency +
				", baseCurrency=" + baseCurrency +
				", format='" + format + '\'' +
				", enabled=" + enabled +
				'}';
	}
}
