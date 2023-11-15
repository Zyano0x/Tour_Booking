package com.project.tour_booking.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.tour_booking.CustomValidator.ValidDepartureDay;

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
  @JsonFormat(pattern = "dd-MM-yyyy")
  @Column(name = "departure_day")
  @ValidDepartureDay(message = "Ngày khởi hành phải lớn hơn thời điểm tạo tour!")
  private LocalDate departureDay;

  @Transient
  @NonNull
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private Long tourIdForCrud;

  @ManyToOne(optional = false)
  @JoinColumn(name = "tour_id", referencedColumnName = "id")
  private Tour tour;
}
