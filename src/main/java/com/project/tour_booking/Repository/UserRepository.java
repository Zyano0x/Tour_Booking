package com.project.tour_booking.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.tour_booking.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
