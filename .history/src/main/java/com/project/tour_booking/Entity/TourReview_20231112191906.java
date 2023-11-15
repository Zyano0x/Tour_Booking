package com.project.tour_booking.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tour_review")
@AllArgsConstructor
@NoArgsConstructor
public class TourReview {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @Column(name = "content")
  @NotBlank(message = "Nội dung đánh giá không thể để trống!")
  private String content;
}
