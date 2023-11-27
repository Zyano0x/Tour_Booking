package com.project.tour_booking.Entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.tour_booking.Validator.ValidDepartureDay;

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
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "departure_day")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class DepartureDay {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", unique = true)
  private Long id;

  @NonNull
  @NotNull(message = "Số lượng khách không được để trống!")
  @Column(name = "quantity")
  private Integer quantity;

  @NonNull
  @JsonFormat(pattern = "dd-MM-yyyy")
  @Column(name = "departure_day")
  @ValidDepartureDay(message = "Ngày khởi hành phải lớn hơn thời điểm tạo tour!")
  private LocalDate departureDay;

  @NonNull
  @Column(name = "status")
  private Boolean status;

  @ManyToOne(optional = false)
  @JoinColumn(name = "tour_id", referencedColumnName = "id")
  private Tour tour;

  @JsonIgnore
  @OneToMany(mappedBy = "departureDay", cascade = CascadeType.ALL)
  private List<Booking> bookings;
}
