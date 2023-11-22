package com.project.tour_booking.Entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "locked", nullable = false)
    private boolean locked;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TourReview> tourReviews;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Articles> posts;
}
