package com.project.tour_booking.Entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "types_of_tours")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class TypeOfTour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NonNull
  @NotBlank(message = "Tên loại tour không được để trống")
  @Column(name = "name", nullable = false)
  private String name;

  @NonNull
  @NotBlank(message = "Mô tả loại tour không được để trống")
  @Column(name = "description", nullable = false)
  private String description;

  @NonNull
  @Column(name = "status", nullable = false)
  private Boolean status;

  @JsonIgnore
  @OneToMany(mappedBy = "typeOfTour", cascade = CascadeType.ALL)
  private List<Tour> tours;
}
