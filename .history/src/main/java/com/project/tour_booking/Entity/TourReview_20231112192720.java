package com.project.tour_booking.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tour_review", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id" })
})
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

  @JsonFormat(pattern = "dd-MM-yyyy")
  @Column(name = "date_of_posting")
  private LocalDate dateOfPosting;

  @Column(name = "edit_date")
  private LocalDate editDate;

  @ManyToOne(optional = false)
  @JoinColumn(name = "tour_id", referencedColumnName = "id")
  private Tour tour;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
