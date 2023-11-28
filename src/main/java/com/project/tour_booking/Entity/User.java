package com.project.tour_booking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    @NotBlank(message = "Tên người dùng không được để trống!")
    @Size(min = 6, max = 50, message = "Tên người dùng phải từ 6 đến 50 kí tự!")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Mật khẩu không được để trống!")
    @Length(max = 250, message = "Mật khẩu phải ít hơn 250 kí tự!")
    private String password;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Tên của bạn không được để trống!")
    private String name;

    @Column(name = "birthday", nullable = false)
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Ngày sinh không được để trống!")
    private LocalDate birthday;

    @Column(name = "gender", length = 10, nullable = false)
    private String gender;

    @Column(name = "email", length = 100, unique = true, nullable = false)
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Email không đúng cú pháp!")
    private String email;

    @Column(name = "address", length = 255, nullable = false)
    private String address;

    @Column(name = "cid", length = 20, nullable = false)
    @NotBlank(message = "CCCD không được để trống!")
    private String cid;

    @Column(name = "phone", nullable = false)
    @NotBlank(message = "Số điện thoại không được để trống!")
    private String phone;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "locked", nullable = false)
    private boolean locked;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TourReview> tourReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Articles> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       return true;
    }

    @Override
    public boolean isEnabled() {
       return enabled;
    }

    public String getEmail() {
        return username;
    }
}
