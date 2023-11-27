package com.project.tour_booking.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private LocalDate birthday;
    private String gender;
    private String address;
    private String cid;
    private String phone;
}
