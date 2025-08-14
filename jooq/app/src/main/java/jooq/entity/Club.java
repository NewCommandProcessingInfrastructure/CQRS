package jooq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "club")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Club implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "club_seq_gen")
  @SequenceGenerator(name = "club_seq_gen", sequenceName = "club_seq", allocationSize = 1)
  private Long id;

  @Column(name = "abbreviation", nullable = false, length = 10)
  private String abbreviation;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "organization_id")
  private Long organizationId;
}
