package com.project.tour_booking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tour")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Tour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NonNull
  @NotBlank(message = "Tên của tour không được để trống!")
  @Column(name = "name")
  private String name;

  @NonNull
  @Column(name = "thumbnail")
  @NotBlank(message = "Thumnail tour không được để trống!")
  private String thumbnail;

  @NonNull
  @NotBlank(message = "Nội dung của tour không được để trống!")
  @Column(name = "content")
  private String description;

  @NonNull
  @NotBlank(message = "Dịch vụ không được để trống!")
  @Column(name = "service")
  private String service;

  @NonNull
  @NotBlank(message = "Thời gian của tour không được để trống!")
  @Column(name = "time")
  private String time;

  @NonNull
  @NotBlank(message = "Lịch trình của tour không được để trống!")
  @Column(name = "schedule")
  private String schedule;

  @NonNull
  @NotNull(message = "Giá tiền cho người lớn không được để trống!")
  @PositiveOrZero(message = "Giá tiền cho người lớn phải là số dương hoặc bằng 0!")
  @Column(name = "price_for_adult")
  private BigDecimal priceForAdult;

  @NonNull
  @NotNull(message = "Giá tiền cho trẻ em không được để trống!")
  @PositiveOrZero(message = "Giá tiền cho trẻ em phải là số dương hoặc bằng 0!")
  @Column(name = "price_for_children")
  private BigDecimal priceForChildren;

  @NonNull
  @NotBlank(message = "Điểm khởi hành không được để trống!")
  @Column(name = "departure_point")
  private String departurePoint;

  @NonNull
  @Column(name = "date_of_posting")
  private LocalDate dateOfPosting;

  @Column(name = "edit_date")
  private LocalDate editDate;

  @NonNull
  @NotNull(message = "Trạng thái không được để trống!")
  @Column(name = "status")
  private Boolean status;

  @NonNull
  @NotNull(message = "Is hot không được để trống!")
  @Column(name = "is_hot")
  private Boolean isHot;

  @JsonIgnore
  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
  private List<DepartureDay> departureDay;

  @JsonIgnore
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
  private List<TourImage> tourImages;

  @ManyToOne(optional = false)
  @JoinColumn(name = "type_of_tour_id", referencedColumnName = "id")
  private TypeOfTour typeOfTour;

  @JsonIgnore
  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
  private List<TourReview> tourReviews;

  @ManyToOne(optional = false)
  @JoinColumn(name = "destination_id", referencedColumnName = "id")
  private Destination destination;
}
