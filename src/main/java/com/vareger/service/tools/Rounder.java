package com.vareger.service.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rounder {
	
	public static Double roundDown(double value, int places) {
	    if (places < 0) 
	    	throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.DOWN);
	    
	    return bd.doubleValue();
	}
}
