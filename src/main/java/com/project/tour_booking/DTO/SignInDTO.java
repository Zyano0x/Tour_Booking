package com.project.tour_booking.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignInDTO {
    @NotBlank(message = "Tên người dùng hoặc email không được để trống!")
    private String usernameOrEmail;

    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;
}
