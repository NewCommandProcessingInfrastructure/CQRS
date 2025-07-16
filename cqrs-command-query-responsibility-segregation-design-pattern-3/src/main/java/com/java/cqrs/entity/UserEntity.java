package com.java.cqrs.entity;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "finance-user-entity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserEntity implements Serializable {
	private static final long serialVersionUID = -2420539805870200114L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String lastName;
	private Integer age;
	private Integer mobileNo;
	private String address;
}
