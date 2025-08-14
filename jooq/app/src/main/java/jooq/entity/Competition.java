package jooq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jooq.Record;

@Entity
@Table(name = "competition")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Competition implements Serializable, Record {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "competition_seq")
  @SequenceGenerator(name = "competition_seq", sequenceName = "competition_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(name = "competition_date", nullable = false)
  private LocalDate competitionDate;

  @Column(name = "always_first_three_medals", nullable = false)
  @Builder.Default
  private boolean alwaysFirstThreeMedals = false;

  @Column(name = "medal_percentage", nullable = false)
  private int medalPercentage;

  @Column(nullable = false)
  private boolean locked = false;

  @Column(name = "series_id")
  private Long seriesId;
}
