package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SliderDTO {
    @NotBlank(message = "Đường dẫn hình ảnh không được để trống!")
    private String path;
}
