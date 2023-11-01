package com.project.tour_booking.Entity;

import java.util.Date;
import java.util.Calendar;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "confirmation_tokens")
public class SecureToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "token", unique = true)
    private String token;

    @Column(name = "expire_time")
    private Date expireTime;

    @OneToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    private static final int EXPIRATION_TIME = 15;

    public SecureToken() {

    }

    public SecureToken(User user) {
        super();
        this.token = UUID.randomUUID().toString();
        this.user = user;
        this.expireTime = this.getTokenExpirationTime();
    }

    public SecureToken(String token) {
        super();
        this.token = token;
        this.expireTime = this.getTokenExpirationTime();
    }

    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
