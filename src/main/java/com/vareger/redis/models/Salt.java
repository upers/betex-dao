package com.vareger.redis.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Salt {
	private Long salt;

	public Salt() {
	}

	public Salt(Long salt) {
		this.salt = salt;
	}

	public Long getSalt() {
		return salt;
	}

	public void setSalt(Long salt) {
		this.salt = salt;
	}

	@Override
	public String toString() {
		return "Salt [salt=" + salt + "]";
	}
}
