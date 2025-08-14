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
@Table(name = "organization")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Organization implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "organization_seq_gen")
  @SequenceGenerator(name = "organization_seq_gen", sequenceName = "organization_seq", allocationSize = 1)
  private Long id;

  @Column(name = "organization_key", nullable = false, length = 10)
  private String organizationKey;

  @Column(name = "name", nullable = false, length = 50)
  private String name;

  @Column(name = "owner", nullable = false, length = 50)
  private String owner;

  @ManyToMany
  @JoinTable(
          name = "organization_user",
          joinColumns = @JoinColumn(name = "organization_id"),
          inverseJoinColumns = @JoinColumn(name = "user_id")
  )
  private Set<SecurityUser> users = new HashSet<>();
}
