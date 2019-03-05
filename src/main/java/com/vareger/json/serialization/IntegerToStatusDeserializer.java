package com.vareger.json.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.vareger.models.BasketStatus;

public class IntegerToStatusDeserializer extends JsonDeserializer<BasketStatus>{
	
	@Override
	public BasketStatus deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
		try {
			Integer origin = jsonParser.readValueAs(Integer.class);
			BasketStatus status = BasketStatus.values()[origin - 1];
			
			return status;
		} catch (ArrayIndexOutOfBoundsException e) {
			//front have got non status so real status is one less
			List<Integer> allowedStatus = new ArrayList<>(BasketStatus.values().length);
			for (int i = 1; i <= BasketStatus.values().length; i++) {
				allowedStatus.add(i);
			}
			throw new ArrayIndexOutOfBoundsException("Invalid basket status. Allowed basket status: " + allowedStatus);
		}
		
	}

}
