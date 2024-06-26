package com.project.tour_booking.Service.User;

import com.project.tour_booking.DTO.*;
import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Repository.SecureTokenRepository;
import com.project.tour_booking.Repository.UserRepository;
import com.project.tour_booking.Service.Email.EmailService;
import com.project.tour_booking.Service.JWT.JWTService;
import com.project.tour_booking.Service.SecureToken.SecureTokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final SecureTokenService secureTokenService;
    private final EmailService emailService;
    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final SecureTokenRepository secureTokenRepository;
    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

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

    @Override
    public AuthenticationResponse signIn(SignInDTO signInDTO, HttpServletResponse response) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDTO.getEmail(), signInDTO.getPassword()));

        var user = userRepository.findByEmail(signInDTO.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        Cookie cookie = new Cookie("Authorization", token);
        cookie.setPath("/");
        cookie.setMaxAge(Integer.MAX_VALUE);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return new AuthenticationResponse(token, refreshToken, userDTO);
    }

    @Override
    public UserDTO signUp(SignUpDTO signUpDTO) {
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
        user.setRole(Role.USER);
        user.setEnabled(false);
        user.setLocked(false);

        userRepository.save(user);

        SecureToken token = new SecureToken(user);
        secureTokenRepository.save(token);
        emailService.sendEmailVerify(user, token);

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public ResponseEntity<?> confirmEmailVerification(String confirmationToken) {
        if (validateToken(confirmationToken) == VerificationResult.INVALID_TOKEN) {
            return new ResponseEntity<>("Invalid verification token.", HttpStatus.BAD_REQUEST);
        } else if (validateToken(confirmationToken) == VerificationResult.EXPIRED) {
            return new ResponseEntity<>("Verification token already expired.", HttpStatus.BAD_REQUEST);
        } else if (validateToken(confirmationToken) == VerificationResult.VALID) {
            SecureToken token = secureTokenRepository.findByToken(confirmationToken);
            User user = token.getUser();
            user.setEnabled(true);

            userRepository.save(user);
            secureTokenService.removeToken(token);

            return ResponseEntity.ok().body("Email verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Couldn't verify email.");
        }
    }

    @Override
    public ResponseEntity<?> resendEmailVerification(String oldToken) {
        if (validateToken(oldToken) == VerificationResult.EXPIRED) {
            SecureToken token = secureTokenRepository.findByToken(oldToken);
            User user = token.getUser();
            token.setToken(UUID.randomUUID().toString());
            token.setExpireTime(new SecureToken().getTokenExpirationTime());

            emailService.sendEmailVerify(user, token);

            return ResponseEntity.ok().body("A new verification link hs been sent to your email, "
                    + "please, check to complete your registration");
        }
        return ResponseEntity.badRequest().body("Cannot resend verification link.");
    }

    @Override
    public ResponseEntity<?> forgotPassword(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User not found for email: " + email, HttpStatus.NOT_FOUND);
        }

        SecureToken token = new SecureToken(user);
        secureTokenRepository.save(token);

        emailService.sendEmailForgotPassword(user, token);

        return ResponseEntity.ok().body("Password reset email sent to " + email);
    }

    @Override
    public ResponseEntity<?> resetPassword(ResetPasswordDTO resetPasswordDTO) {
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

        return ResponseEntity.ok().body("Password reset successful");
    }

    @Override
    public ResponseEntity<?> updateUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setLocked(!user.isLocked());
        userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }

    @Override
    public User user(String email) {
        return ResponseEntity
                .ok(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found")))
                .getBody();
    }

    @Override
    public User getUserById(Long id) {
        return ResponseEntity
                .ok(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found")))
                .getBody();
    }

    @Override
    public List<User> listUsers() {
        return ResponseEntity.ok(userRepository.findAll()).getBody();
    }

    public enum VerificationResult {
        VALID,
        INVALID_TOKEN,
        EXPIRED,
    }
}
