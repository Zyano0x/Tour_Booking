package com.project.tour_booking.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserRoleDTO {
    @NotNull(message = "Mã quyền không được để trống!")
    @Min(value = 1, message = "Mã quyền có giá trị bắt đầu từ '1'!")
    private Long roleId;
}
