package com.project.tour_booking.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "post")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Post {
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

  @Column(name = "date_of_posting")
  private LocalDate dateOfPosting;

}
