package com.project.tour_booking.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.tour_booking.Validator.ValidDepartureDay;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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
    @Min(value = 1, message = "Số lượng khách phải lớn hơn '0'!")
    @Column(name = "quantity")
    private Integer quantity;

    @NonNull
    @JsonFormat(pattern = "MM-dd-yyyy")
    @Column(name = "departure_day")
    @ValidDepartureDay(message = "Ngày khởi hành phải lớn hơn thời điểm khởi tạo!")
    private LocalDate departureDay;

    @NonNull
    @NotNull(message = "Trạng thái không được để trống!")
    @Column(name = "status")
    private Boolean status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tour_id", referencedColumnName = "id")
    private Tour tour;

    @JsonIgnore
    @OneToMany(mappedBy = "departureDay", cascade = CascadeType.ALL)
    private List<Booking> bookings;
}
