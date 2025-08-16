package com.java.grpc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stocks")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Stock implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "stock_symbol", unique = true, nullable = false)
  private String stockSymbol;

  private Double price;

  @Column(name = "last_updated")
  private LocalDateTime lastUpdated;
}
