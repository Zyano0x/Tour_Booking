package com.project.tour_booking.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.tour_booking.Validator.ValidDepartureDay;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class DepartureDayDTO {
    @NotNull(message = "Số lượng khách không được để trống!")
    @Min(value = 1, message = "Số lượng khách phải lớn hơn '0'!")
    private Integer quantity;

    @JsonFormat(pattern = "MM-dd-yyyy")
    @ValidDepartureDay(message = "Ngày khởi hành phải lớn hơn thời điểm khởi tạo!")
    private LocalDate departureDay;

    @NotNull(message = "Trạng thái không được để trống!")
    private Boolean status;

    @NotNull(message = "Mã tour không được để trống!")
    @Min(value = 1, message = "Mã tour có giá trị bắt đầu từ '1'!")
    private Long tourId;
}
