package com.vareger.json.serialization;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class DateToTimeestampSerializerNumeric extends JsonSerializer<Date>{
	
	@Override
	public void serialize(Date date, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		Long time = date.getTime() / 1000l;
		
		gen.writeNumber(time);
	}

}
