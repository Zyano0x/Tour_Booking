package com.project.tour_booking.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "type_of_tour")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class TypeOfTour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @NotBlank(message = "Tên tour không được để trống")
  @Column(name = "name", nullable = false)
  private String name;
  @NotBlank(message = "Mô tả tour không được để trống")
  @Column(name = "description", nullable = false)
  private String description;
}
