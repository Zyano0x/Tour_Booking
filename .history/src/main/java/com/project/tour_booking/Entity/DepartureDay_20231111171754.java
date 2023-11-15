package com.project.tour_booking.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

@Entity
@Table(name = "departure_day", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "id", "tour_id", "departure_day" })
})
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class DepartureDay {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @Column(name = "departure_day")
  private LocalDate departureDay;

  @ManyToOne(optional = false)
  @JoinColumn(name = "tour_id", referencedColumnName = "id")
  private Tour tour;
}
