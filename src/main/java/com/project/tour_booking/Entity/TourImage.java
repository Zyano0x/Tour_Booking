package com.project.tour_booking.Entity;

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
@Table(name = "tour_images")
@Setter
@Getter
@Builder
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class TourImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NonNull
  @NotBlank(message = "Đường dẫn ảnh không thể để trống!")
  @Column(name = "path")
  private String path;

  @ManyToOne(optional = false)
  @JoinColumn(name = "tour_id", referencedColumnName = "id")
  private Tour tour;
}
