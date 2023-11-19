package com.project.tour_booking.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

  // @NonNull
  // @NotNull(message = "Số lượng khách không được để trống!")
  // @Column(name = "quantity")
  // private Integer quantity;

  @NonNull
  @NotBlank(message = "Lịch trình của tour không được để trống!")
  @Column(name = "schedule")
  private String schedule;

  @NonNull
  @NotNull(message = "Giá tiền cho người lớn không được để trống!")
  @Column(name = "price_for_adult")
  private BigDecimal priceForAdult;

  @NonNull
  @NotNull(message = "Giá tiền cho trẻ em không được để trống!")
  @Column(name = "price_for_children")
  private BigDecimal priceForChildren;

  @NonNull
  @NotBlank(message = "Điểm khởi hành không được để trống!")
  @Column(name = "departure_point")
  private String departurePoint;

  @Column(name = "date_of_posting")
  private LocalDate dateOfPosting;

  @Column(name = "edit_date")
  private LocalDate editDate;

  @Column(name = "status")
  private Boolean status;

  @NonNull
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
}
