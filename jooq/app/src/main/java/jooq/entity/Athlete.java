package jooq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "athlete")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Athlete implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "athlete_seq")
  @SequenceGenerator(name = "athlete_seq", sequenceName = "athlete_seq", allocationSize = 1)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(nullable = false, length = 1)
  private char gender;

  @Column(name = "year_of_birth", nullable = false)
  private int yearOfBirth;

  @Column(name = "club_id")
  private Long clubId;

  @Column(name = "organization_id")
  private Long organizationId;

  // Back reference to Category
  @ManyToMany(mappedBy = "athletes")
  private Set<Category> categories = new HashSet<>();
}
