package com.java.command.persistence.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Converter
public class JsonAttributeConverter implements AttributeConverter<JsonNode, String>{

	private final ObjectMapper mapper;

	public JsonAttributeConverter(ObjectMapper mapper) {
		this.mapper = mapper;
		mapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
		mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
//		log.info("Inside JsonAttributeConverter - Constructor");
	}

	@Override
	@SneakyThrows
	public String convertToDatabaseColumn(JsonNode source) {
//		log.info("Inside JsonAttributeConverter - convertToDatabaseColumn");
		return source != null ? mapper.writeValueAsString(source) : null;
	}

	@Override
	@SneakyThrows
	public JsonNode convertToEntityAttribute(String source) {
//		log.info("Inside JsonAttributeConverter - convertToEntityAttribute");
		return source != null ? mapper.readTree(source) : null;
	}
}
