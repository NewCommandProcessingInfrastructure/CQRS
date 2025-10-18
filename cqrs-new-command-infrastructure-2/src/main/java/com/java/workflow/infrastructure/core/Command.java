package com.java.workflow.infrastructure.core;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
public class Command<T> implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;
  private String idempotencyKey;
  private String status;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
  private String tenantId;
  private String username;
  private T payload;
}
