package com.vareger.service.tools;

import java.math.BigInteger;

public class HexUtils {
	
	public static String getHexClear(String hex) {
		if (hex == null)
			return null;
		
		hex = hex.toLowerCase();
		if (hex.startsWith("0x"))
			hex = hex.substring(2, hex.length());
		
		try {
			new BigInteger(hex, 16);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			
			return null;
		}
		
		return hex;
	}
	
}
