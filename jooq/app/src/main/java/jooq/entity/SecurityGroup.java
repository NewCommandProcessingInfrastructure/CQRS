package jooq.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "security_group")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SecurityGroup implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_group_seq")
  @SequenceGenerator(name = "security_group_seq", sequenceName = "security_group_seq", allocationSize = 1)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserGroup> users = new HashSet<>();
}
