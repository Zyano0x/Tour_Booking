package com.project.tour_booking.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "articles")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Articles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NonNull
  @NotBlank(message = "Tiêu đề không được để trống!")
  @Column(name = "title")
  private String title;

  @NonNull
  @NotBlank(message = "Nội dung không được để trống!")
  @Column(name = "content")
  private String content;

  @NonNull
  @Column(name = "date_of_posting")
  private LocalDate dateOfPosting;

  @NonNull
  @Column(name = "status")
  private Boolean status;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
