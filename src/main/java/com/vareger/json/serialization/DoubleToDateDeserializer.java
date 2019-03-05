package com.vareger.json.serialization;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class DoubleToDateDeserializer extends JsonDeserializer<Date>{
	
	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
		String strVal = jsonParser.readValueAs(String.class);
		int index = -1;
		if ( (index = strVal.indexOf(".")) != -1)
			strVal = strVal.substring(0, index);
		
		if (strVal.length() <= 10)
			return new Date(Long.valueOf(strVal) * 1000l);
		else
			return new Date(Long.valueOf(strVal));
	}

}
