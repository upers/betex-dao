package com.vareger.json.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.vareger.models.BasketStatus;

public class StatusToIntegerSerializer extends JsonSerializer<BasketStatus>{
	
	@Override
	public void serialize(BasketStatus status, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
		int frontStatus = status.ordinal() + 1;
		gen.writeNumber(frontStatus);
	}

}
