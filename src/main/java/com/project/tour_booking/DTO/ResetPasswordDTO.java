package com.project.tour_booking.DTO;

import lombok.Data;

@Data
public class ResetPasswordDTO {
    private String email;
    private String token;
    private String password;
    private String repeatPassword;
}
