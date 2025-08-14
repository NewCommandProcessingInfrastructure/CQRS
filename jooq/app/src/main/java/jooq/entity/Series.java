package jooq.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "series")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Series implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_seq")
  @SequenceGenerator(name = "series_seq", sequenceName = "series_seq", allocationSize = 1)
  private Long id;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Lob
  @Column(name = "logo")
  private byte[] logo;

  @Column(name = "hidden", nullable = false)
  private boolean hidden = false;

  @Column(name = "locked", nullable = false)
  private boolean locked = false;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "organization_id")
  private Organization organization;
}
