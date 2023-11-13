package com.project.tour_booking.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tour_review")
@AllArgsConstructor
@NoArgsConstructor
public class TourReview {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
}
