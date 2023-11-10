package com.project.tour_booking.Service;

import java.util.Calendar;
import java.util.Collections;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.ResetPasswordDTO;
import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Repository.RoleRepository;
import com.project.tour_booking.Repository.SecureTokenRepository;
import com.project.tour_booking.Repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecureTokenService secureTokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SecureTokenRepository secureTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public enum VerificationResult {
        VALID,
        INVALID_TOKEN,
        EXPIRED,
    }

    @Override
    public ResponseEntity<String> signIn(String usernameOrEmail, String password) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(usernameOrEmail, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User signed-in successfully!", HttpStatus.OK);

        } catch (AuthenticationException e) {
            // Handle authentication failure, e.g., return an error response
            return new ResponseEntity<>("Authentication failed.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<String> signUp(SignUpDTO signUpDTO) {
        try {
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

            Role roles = roleRepository.findByName("USER")
                    .orElseThrow(() -> new IllegalStateException("Default role not found"));
            user.setRoles(Collections.singleton(roles));

            userRepository.save(user);

            SecureToken token = new SecureToken(user);

            secureTokenRepository.save(token);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("no-reply@tourbooking.com");
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here: "
                                +"http://localhost:1337/api/confirm-account?token="+token.getToken());

            emailService.sendEmail(mailMessage);

            return new ResponseEntity<>(
                    "User registered successfully. Verify email by the link sent on your email address!",
                    HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Handle registration failure, e.g., return an error response
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (IllegalStateException e) {
            // Handle role not found, e.g., return an error response
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> confirmEmailVerification(String confirmationToken) {

        if (validateToken(confirmationToken) == VerificationResult.INVALID_TOKEN) {
            return new ResponseEntity<>("Invalid verification token.", HttpStatus.BAD_REQUEST);
        } else if (validateToken(confirmationToken) == VerificationResult.EXPIRED) {
            return new ResponseEntity<>("Verification token already expired.", HttpStatus.BAD_REQUEST);
        } else if (validateToken(confirmationToken) == VerificationResult.VALID) {
            SecureToken token = secureTokenRepository.findByToken(confirmationToken);
            User user = token.getUser();

            user.setVerified(true);
            userRepository.save(user);

            secureTokenService.removeToken(token);

            return new ResponseEntity<>("Email verified successfully!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: Couldn't verify email", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<String> resendEmailVerification(String oldToken) {
        if (validateToken(oldToken) == VerificationResult.EXPIRED) {
            SecureToken token = secureTokenRepository.findByToken(oldToken);
            User user = token.getUser();
            token.setToken(UUID.randomUUID().toString());
            token.setExpireTime(new SecureToken().getTokenExpirationTime());

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("no-reply@tourbooking.com");
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here: "
                                +"http://localhost:1337/api/confirm-account?token="+token.getToken());

            emailService.sendEmail(mailMessage);
            return new ResponseEntity<>("A new verification link hs been sent to your email, "
                                        + "please, check to complete your registration", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found for email: " + email, HttpStatus.NOT_FOUND);
        }
        
        SecureToken token = new SecureToken(user);
        secureTokenRepository.save(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset Password");
        mailMessage.setText("To reset password your account, please click here: "
                            +"http://localhost:1337/api/reset-password?token="+token.getToken());

        emailService.sendEmail(mailMessage);
        
        return new ResponseEntity<>("Password reset email sent to " + email, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> resetPassword(ResetPasswordDTO resetPasswordDTO) {
        SecureToken token = secureTokenRepository.findByToken(resetPasswordDTO.getToken());

        if (validateToken(token.getToken()) == VerificationResult.INVALID_TOKEN) {
            return new ResponseEntity<>("Invalid reset token", HttpStatus.BAD_REQUEST);
        } else if (validateToken(token.getToken()) == VerificationResult.EXPIRED) {
            return new ResponseEntity<>("Reset token has expired", HttpStatus.BAD_REQUEST);
        }
        
        User user = userRepository.findByEmail(resetPasswordDTO.getEmail()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
        }

        // Check if the new password and repeat password match
        String newPassword = resetPasswordDTO.getPassword();
        String repeatPassword = resetPasswordDTO.getRepeatPassword();
        if (!newPassword.equals(repeatPassword)) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }
        
        // Set the new password and save the user
        user.setPassword(passwordEncoder.encode(newPassword)); // Hash the password
        userRepository.save(user);
        
        secureTokenService.removeToken(token);
        
        return new ResponseEntity<>("Password reset successful", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateUserRole(String username, Long roleId) {
        User user = userRepository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + username));
    
        Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + roleId));

        user.getRoles().clear();
        user.getRoles().add(role);
        userRepository.save(user);

        return new ResponseEntity<>("Update user role succeed", HttpStatus.OK);
    }

    public VerificationResult validateToken(String confirmationToken) {
        SecureToken token = secureTokenRepository.findByToken(confirmationToken);

        if (token == null) {
            return VerificationResult.INVALID_TOKEN;
        }

        Calendar calendar = Calendar.getInstance();

        if ((token.getExpireTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return VerificationResult.EXPIRED;
        }

        return VerificationResult.VALID;
    }
}
