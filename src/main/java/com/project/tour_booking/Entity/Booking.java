package com.project.tour_booking.Entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "booking")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Booking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NonNull
  @PositiveOrZero(message = "Số lượng người lớn phải là số nguyên dương hoặc bằng 0!")
  @NotNull(message = "Số lượng người lớn không được để trống!")
  @Column(name = "quantity_of_adult")
  private Integer quantityOfAdult;

  @NonNull
  @PositiveOrZero(message = "Số lượng trẻ em phải là số nguyên dương hoặc bằng 0!")
  @NotNull(message = "Số lượng trẻ em không được để trống!")
  @Column(name = "quantity_of_child")
  private Integer quantityOfChild;

  @NonNull
  @Column(name = "total_price")
  private BigDecimal totalPrice;

  @NonNull
  @Column(name = "status")
  private Boolean status;

  @NonNull
  @Column(name = "booking_date")
  private LocalDate bookingDate;

  @ManyToOne(optional = false)
  @JoinColumn(name = "departure_id", referencedColumnName = "id")
  private DepartureDay departureDay;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;
}
