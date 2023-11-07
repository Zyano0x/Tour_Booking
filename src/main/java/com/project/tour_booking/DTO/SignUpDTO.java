package com.project.tour_booking.DTO;

import java.util.Date;

import lombok.Data;

@Data
public class SignUpDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private Date birthday;
    private String gender;
    private String address;
    private Long cid;
    private Long phone;
}
