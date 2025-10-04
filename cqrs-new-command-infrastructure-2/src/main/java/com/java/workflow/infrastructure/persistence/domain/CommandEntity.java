package com.java.workflow.infrastructure.persistence.domain;

import com.fasterxml.jackson.databind.JsonNode;
import com.java.workflow.infrastructure.persistence.converter.JsonAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "m_command")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommandEntity implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @Builder.Default
  @Transient
  @Setter(value = AccessLevel.NONE)
  private boolean isNew = true;

  @Column(name = "commandId")
  private UUID commandId;

  @Column(name = "created_at")
  private OffsetDateTime createdAt;

  @Column(name = "tenant_id")
  private String tenantId;

  @Column(name = "username")
  private String username;

  @Column(name = "payload")
  @Convert(converter = JsonAttributeConverter.class)
  private JsonNode payload;

  @PrePersist
  @PostLoad
  void markNotNew(){
    this.isNew = false;
  }
}
