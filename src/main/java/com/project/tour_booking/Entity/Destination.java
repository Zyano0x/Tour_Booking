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
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "destinations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Destination {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NonNull
  @NotBlank(message = "Tên địa điểm không được để trống!")
  @Column(name = "name")
  private String name;

  @NonNull
  @NotBlank(message = "Thumnail địa điểm không được để trống!")
  @Column(name = "thumbnail")
  private String thumbnail;

  @NonNull
  @NotNull(message = "Is hot không được để trống!")
  @Column(name = "is_hot")
  private Boolean isHot;

  @NonNull
  @NotNull(message = "Trạng thái không được để trống!")
  @Column(name = "status")
  private Boolean status;

  @JsonIgnore
  @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL)
  private List<Tour> tours;
}
