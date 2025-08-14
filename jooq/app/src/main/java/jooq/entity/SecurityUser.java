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
@Table(name = "security_user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class SecurityUser implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "security_user_seq")
  @SequenceGenerator(name = "security_user_seq", sequenceName = "security_user_seq", allocationSize = 1)
  private Long id;

  @Column(name = "first_name", nullable = false, length = 100)
  private String firstName;

  @Column(name = "last_name", nullable = false, length = 100)
  private String lastName;

  @Column(nullable = false, length = 50, unique = true)
  private String email;

  @Column(nullable = false, length = 100)
  private String secret;

  @Column(name = "confirmation_id", length = 200)
  private String confirmationId;

  @Column(nullable = false)
  private boolean confirmed = false;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<UserGroup> groups = new HashSet<>();
}
