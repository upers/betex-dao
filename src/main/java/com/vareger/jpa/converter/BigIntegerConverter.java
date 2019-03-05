package com.vareger.jpa.converter;

import java.math.BigInteger;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

@Convert
public class BigIntegerConverter implements AttributeConverter<BigInteger, String>{

	@Override
	public String convertToDatabaseColumn(BigInteger val) {
		if (val == null)
			return null;
		
		return val.toString();
	}

	@Override
	public BigInteger convertToEntityAttribute(String val) {
		if (val == null)
			return null;
		
		return new BigInteger(val);
	}

}
