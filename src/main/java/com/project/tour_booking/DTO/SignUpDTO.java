package com.project.tour_booking.DTO;

import java.time.LocalDate;

import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    @NotBlank(message = "Tên của bạn không được để trống!")
    private String name;

    @NotBlank(message = "Tên người dùng không được để trống!")
    @Size(min = 1, max = 50, message = "Tên người dùng phải từ 1 đến 50 kí tự!")
    private String username;

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không đúng cú pháp!")
    private String email;

    @Length(max = 250, message = "Mật khẩu phải ít hơn 250 kí tự!")
    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;

    @Temporal(TemporalType.DATE)
    @NotNull(message = "Ngày sinh không được để trống!")
    private LocalDate birthday;

    private String gender;

    private String address;

    @NotBlank(message = "CCCD không được để trống!")
    private String cid;

    @NotBlank(message = "Số điện thoại không được để trống!")
    private String phone;
}
