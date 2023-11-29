package com.project.tour_booking.DTO;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordDTO {
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không đúng cú pháp!")
    private String email;

    private String token;

    @Length(max = 250)
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;

    @Length(max = 250)
    @NotBlank(message = "Mật khẩu xác nhận không được để trống!")
    private String repeatPassword;
}
