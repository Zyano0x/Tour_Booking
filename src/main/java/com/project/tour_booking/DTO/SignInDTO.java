package com.project.tour_booking.DTO;

import lombok.Data;

@Data
public class SignInDTO {
    private String usernameOrEmail;
    private String password;

    public String getUsernameOrEmail() {
        return this.usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
