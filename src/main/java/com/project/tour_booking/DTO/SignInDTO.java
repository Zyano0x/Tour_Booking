package com.project.tour_booking.DTO;

import lombok.Data;

@Data
public class SignInDTO {
    private String usernameOrEmail;
    private String password;
}
