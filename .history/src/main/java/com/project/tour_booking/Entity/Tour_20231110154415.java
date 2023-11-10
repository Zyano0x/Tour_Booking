package com.project.tour_booking.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tour")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "images")
  private List<String> images = new ArrayList<>();

  @Column(name = "content")
  private String content;

  @Column(name = "price")
  private BigDecimal price;

  @Column(name = "departureDay")
  private LocalDateTime departureDay;

  @Column(name = "departurePoint")
  private String departurePoint;

  @Column(name = "dateOfPosting")
  private LocalDateTime dateOfPosting;
}
