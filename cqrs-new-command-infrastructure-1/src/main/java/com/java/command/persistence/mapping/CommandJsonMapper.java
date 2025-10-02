package com.java.command.persistence.mapping;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandJsonMapper {
	
	private static final String CLASS_ATTRIBUTE = "@class";
	private final ObjectMapper mapper;
	
	public <T> T map(JsonNode source) {
		
//		log.info("Inside CommandJsonMapper - T map");
		
		if(source == null) {
			return null;
		}
		
		var canonicalName = source.get(CLASS_ATTRIBUTE).asText();
		
		try {
			return (T) mapper.convertValue(source, Class.forName(canonicalName));
		} catch (Exception e) {
			log.error("Error while mapping Json Node, {}", e);
		}
		return null;
	}
	
	public JsonNode map(Object source) {
		if(source == null) {
			return null;
		}
//		log.info("Inside CommandJsonMapper - JsonNode Map");
		var json = mapper.convertValue(source, ObjectNode.class);
		json.set(CLASS_ATTRIBUTE, new TextNode(source.getClass().getCanonicalName()));
		return json;
	}
}
