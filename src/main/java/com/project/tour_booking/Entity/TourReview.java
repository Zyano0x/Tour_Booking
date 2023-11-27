package com.project.tour_booking.Entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "tour_review", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "tour_id", "user_id" })
})
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class TourReview {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @NotBlank(message = "Nội dung đánh giá không thể để trống!")
  @Column(name = "content")
  private String content;

  @NonNull
  @NotNull(message = "Vote không được để trống!")
  @Column(name = "vote")
  private Integer vote;

  @NonNull
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
