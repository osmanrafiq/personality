package org.rafiq.personality.facebook.impl;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class UnixTimestampDeserializer extends JsonDeserializer<Date>{
	private Logger logger = LoggerFactory.getLogger(UnixTimestampDeserializer.class);
	
	@Override
	public Date deserialize(JsonParser jp, DeserializationContext context) throws IOException, JsonProcessingException {
		String timestamp = jp.getText().trim();

		Date date = null;
		try {
			long unixTime = Long.valueOf(timestamp);
			date = Date.from(Instant.ofEpochSecond(unixTime));
			logger.info("Unix Time: " + unixTime + ", converter: " + date);
        } catch (NumberFormatException e) {
            logger.warn("Unable to deserialize timestamp: " + timestamp, e);
        }
		return date;
	}

}
