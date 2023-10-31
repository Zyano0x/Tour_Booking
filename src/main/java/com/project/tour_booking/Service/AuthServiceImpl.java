package com.project.tour_booking.Service;

import java.util.Collections;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Repository.RoleRepository;
import com.project.tour_booking.Repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication signIn(String usernameOrEmail, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));
    }

     @Override
    public User signUp(SignUpDTO signUpDTO) {
        // Check for username exists in a DB
        if (userRepository.existsByUsername(signUpDTO.getUsername())) {
            throw new IllegalArgumentException("Username is already exist!");
        }

        // Check for email exists in DB
        if (userRepository.existsByEmail(signUpDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already taken!");
        }

        // Create user
        User user = new User();
        user.setName(signUpDTO.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        user.setBirthday(signUpDTO.getBirthday());
        user.setGender(signUpDTO.getGender());
        user.setAddress(signUpDTO.getAddress());
        user.setCid(signUpDTO.getCid());
        user.setPhone(signUpDTO.getPhone());

        Role roles = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new IllegalStateException("Default role not found"));
        user.setRoles(Collections.singleton(roles));

        return userRepository.save(user);
    }
}
