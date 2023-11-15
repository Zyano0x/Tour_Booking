package com.project.tour_booking.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tour_image")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TourImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tour_image")
  private Long id;

  @NotBlank(message = "Đường dẫn ảnh không thể để trống!")
  @Column(name = "path")
  private String path;

}
