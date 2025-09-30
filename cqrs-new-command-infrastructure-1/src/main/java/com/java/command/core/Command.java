package com.java.command.core;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

@Data
@FieldNameConstants
public class Command<T> implements Serializable{

	@Serial
	private static final long serialVersionUID = 1L;
	
	private UUID id;
	private String idempotencyKey;
	private OffsetDateTime createdAt;
	private String tenantId;
	private String username;
	private T payload;
}
