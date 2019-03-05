package com.vareger.jsonviews;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vareger.models.BasketConfig;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_EMPTY)
public class BasketConfigView {
	private Integer lifeTime;
	
	private Integer waitTime;
	
	public BasketConfigView() {}
	
	public BasketConfigView(BasketConfig conf) {
		Integer liveTime = (conf.getLiveTime()==null) ? 0 : conf.getLiveTime(); 
		Integer openTime = (conf.getOpenTime()==null) ? 0 : conf.getOpenTime();
		
		this.lifeTime = openTime;
		this.waitTime = liveTime - openTime;
	}
	
	public Integer getLifeTime() {
		return lifeTime;
	}

	public void setLifeTime(Integer lifeTime) {
		this.lifeTime = lifeTime;
	}

	public Integer getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(Integer waitTime) {
		this.waitTime = waitTime;
	}

}

