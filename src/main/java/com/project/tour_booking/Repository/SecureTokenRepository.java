package com.project.tour_booking.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tour_booking.Entity.SecureToken;

@Repository
public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> {
    SecureToken findByToken(String token);
}
