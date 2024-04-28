package com.project.tour_booking.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String username;
    private String email;
    private LocalDate birthday;
    private String gender;
    private String address;
    private String cid;
    private String phone;
}
