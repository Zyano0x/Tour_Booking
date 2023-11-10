package com.project.tour_booking.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
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
  private Long id;
  private String name;
  private List<String> images = new ArrayList<>();
  private String content;
  private BigDecimal price;
  private LocalDateTime departureDay;
  private String departurePoint;
  private LocalDateTime dateOfPosting;
}
