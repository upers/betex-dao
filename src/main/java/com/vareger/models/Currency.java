package com.vareger.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "currency")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = Include.NON_EMPTY)
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Integer id;

	@OneToMany(mappedBy = "currency")
	@JsonInclude(Include.NON_EMPTY)
	private List<Ticker> tickers;

	@OneToMany(mappedBy = "baseCurrency")
	@JsonInclude(Include.NON_EMPTY)
	private List<Ticker> baseTickers;

	private String name;
	
	@NotNull
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Ticker> getTickers() {
		return tickers;
	}

	public void setTickers(List<Ticker> stats) {
		this.tickers = stats;
	}

	public List<Ticker> getBaseTickers() {
		return baseTickers;
	}

	public void setBaseTickers(List<Ticker> baseStats) {
		this.baseTickers = baseStats;
	}

	@Override
	public String toString() {
		return code;
	}

}
