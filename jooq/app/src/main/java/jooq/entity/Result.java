package jooq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "result")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Result implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
  @SequenceGenerator(name = "result_seq", sequenceName = "result_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false)
  private Integer position;

  @Column(nullable = false, length = 20)
  private String result;

  @Column(nullable = false)
  private Integer points;

  // ---- Relationships ----
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "athlete_id", nullable = false)
  private Athlete athlete;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "competition_id", nullable = false)
  private Competition competition;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;
}
