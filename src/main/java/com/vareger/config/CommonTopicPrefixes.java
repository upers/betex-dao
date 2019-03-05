package com.vareger.config;

public enum CommonTopicPrefixes {
	BasketFilter("/topic/baskets.filter.");
	
	final public String destenition;
	
	private CommonTopicPrefixes(String destenition) {
		this.destenition = destenition;
	}
}
