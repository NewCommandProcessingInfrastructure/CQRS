package jooq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_seq")
  @SequenceGenerator(name = "event_seq", sequenceName = "event_seq", allocationSize = 1)
  private Long id;

  @Column(length = 10)
  private String abbreviation;

  @Column(length = 50)
  private String name;

  @Column(length = 1)
  private String gender;

  @Column(name = "event_type", length = 10)
  private String eventType;

  @Column(nullable = false)
  private Double a;

  @Column(nullable = false)
  private Double b;

  @Column(nullable = false)
  private Double c;

  @Column(name = "organization_id")
  private Long organizationId;
}
