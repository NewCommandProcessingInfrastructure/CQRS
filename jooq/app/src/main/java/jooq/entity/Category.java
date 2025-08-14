package jooq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Category implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
  @SequenceGenerator(name = "category_seq", sequenceName = "category_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false, length = 10)
  private String abbreviation;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 1)
  private char gender;

  @Column(name = "year_from", nullable = false)
  private int yearFrom;

  @Column(name = "year_to", nullable = false)
  private int yearTo;

  @Column(name = "series_id")
  private Long seriesId;

  // Many-to-Many with Athlete
  @ManyToMany
  @JoinTable(
          name = "category_athlete",
          joinColumns = @JoinColumn(name = "category_id"),
          inverseJoinColumns = @JoinColumn(name = "athlete_id")
  )
  private Set<Athlete> athletes = new HashSet<>();
}
