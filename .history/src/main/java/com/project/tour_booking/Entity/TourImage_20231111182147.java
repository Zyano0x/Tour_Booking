package com.project.tour_booking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tour_image", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id", "tour_id" })
})
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class TourImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @NotBlank(message = "Đường dẫn ảnh không thể để trống!")
  @Column(name = "path")
  private String path;

  @Transient
  // @NotBlank(message = "Mã tour không được để trống!")
  @NonNull
  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Long tourId;

  @ManyToOne(optional = false)
  @JoinColumn(name = "tour_id", referencedColumnName = "id")
  private Tour tour;
}
