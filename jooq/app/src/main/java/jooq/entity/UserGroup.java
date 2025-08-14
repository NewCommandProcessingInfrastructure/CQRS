package jooq.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_group")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup implements Serializable {

  @EmbeddedId
  private UserGroupId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("userId")
  @JoinColumn(name = "user_id", nullable = false)
  private SecurityUser user;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("groupId")
  @JoinColumn(name = "group_id", nullable = false)
  private SecurityGroup group;
}
