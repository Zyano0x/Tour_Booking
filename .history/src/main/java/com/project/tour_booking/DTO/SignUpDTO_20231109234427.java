package com.project.tour_booking.DTO;

import java.time.LocalDate;

import lombok.Data;

@Data
public class SignUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private LocalDate birthday;
    private String gender;
    private String address;
    private Long cid;
    private String phone;
}
