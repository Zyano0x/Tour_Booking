package com.project.tour_booking.Service.User;

import com.project.tour_booking.DTO.AuthenticationResponse;
import com.project.tour_booking.DTO.SignInDTO;
import com.project.tour_booking.DTO.SignUpDTO;
import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Repository.SecureTokenRepository;
import com.project.tour_booking.Repository.UserRepository;
import com.project.tour_booking.Service.Email.EmailService;
import com.project.tour_booking.Service.JWT.JWTService;
import com.project.tour_booking.Service.SecureToken.SecureTokenService;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private SecureTokenService secureTokenService;

    @Mock
    private EmailService emailService;

    @Mock
    private JWTService jwtService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecureTokenRepository secureTokenRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void signIn_ValidCredentials() {
        User user = User.builder()
                .name("Worry")
                .username("Worry")
                .email("test@gmail.com")
                .password(new BCryptPasswordEncoder().encode("0..982883636"))
                .birthday(LocalDate.of(2002, 5, 30))
                .gender("Male")
                .address("Viet Nam")
                .cid("1337")
                .phone("0865689470")
                .role(Role.ADMIN)
                .enabled(true)
                .locked(false)
                .build();

        SignInDTO signInDTO = new SignInDTO("test@gmail.com", "0..982883636");

        when(userRepository.findByEmail(signInDTO.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("AccessToken");
        when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn("RefreshToken");
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        ResponseEntity<?> response = userService.signIn(signInDTO, mock(HttpServletResponse.class));

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertInstanceOf(AuthenticationResponse.class, response.getBody());
    }

    @Test
    void signIn_InvalidCredentials() {
        User user = User.builder()
                .name("Worry")
                .username("Worry")
                .email("test@gmail.com")
                .password(new BCryptPasswordEncoder().encode("0..982883636"))
                .birthday(LocalDate.of(2002, 5, 30))
                .gender("Male")
                .address("Viet Nam")
                .cid("1337")
                .phone("0865689470")
                .role(Role.ADMIN)
                .enabled(true)
                .locked(false)
                .build();

        SignInDTO signInDTO = new SignInDTO("test@gmail.com", "0..982883535");

        when(userRepository.findByEmail(signInDTO.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("AccessToken");
        when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn("RefreshToken");

        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid email or password"));

        ResponseEntity<?> response = userService.signIn(signInDTO, mock(HttpServletResponse.class));

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());
    }

    @Test
    void signIn_NonExistingUser() {
        SignInDTO signInDTO = new SignInDTO("test@gmail.com", "0..982883535");

        when(userRepository.findByEmail(signInDTO.getEmail())).thenReturn(Optional.empty());
        when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("AccessToken");
        when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn("RefreshToken");

        ResponseEntity<?> response = userService.signIn(signInDTO, mock(HttpServletResponse.class));

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Invalid email or password", response.getBody());
    }

    @Test
    void signIn_Locked() {
        User user = User.builder()
                .name("Worry")
                .username("Worry")
                .email("test@gmail.com")
                .password(new BCryptPasswordEncoder().encode("0..982883636"))
                .birthday(LocalDate.of(2002, 5, 30))
                .gender("Male")
                .address("Viet Nam")
                .cid("1337")
                .phone("0865689470")
                .role(Role.ADMIN)
                .enabled(true)
                .locked(true)
                .build();

        SignInDTO signInDTO = new SignInDTO("test@gmail.com", "0..982883636");

        when(userRepository.findByEmail(signInDTO.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(Mockito.any(User.class))).thenReturn("AccessToken");
        when(jwtService.generateRefreshToken(Mockito.any(User.class))).thenReturn("RefreshToken");
        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new LockedException("Your account is locked"));

        ResponseEntity<?> response = userService.signIn(signInDTO, mock(HttpServletResponse.class));

        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Your account is locked", response.getBody());
    }

    @Test
    public void signUp_UsernameExists() {
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .name("Bear")
                .username("Bear")
                .email("bear@gmail.com")
                .password("0..982883636")
                .birthday(LocalDate.of(2002, 5, 30))
                .gender("Male")
                .address("Viet Nam")
                .cid("1337")
                .phone("0865689470")
                .build();

        when(userRepository.existsByUsername(signUpDTO.getUsername())).thenReturn(true);

        ResponseEntity<?> response = userService.signUp(signUpDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void signUp_EmailExists() {
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .name("Bear")
                .username("Bear")
                .email("bear@gmail.com")
                .password("0..982883636")
                .birthday(LocalDate.of(2002, 5, 30))
                .gender("Male")
                .address("Viet Nam")
                .cid("1337")
                .phone("0865689470")
                .build();

        when(userRepository.existsByUsername(signUpDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpDTO.getEmail())).thenReturn(true);

        ResponseEntity<?> response = userService.signUp(signUpDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void signUp_SuccessfulRegistration() throws InstantiationException, IllegalAccessException {
        SignUpDTO signUpDTO = SignUpDTO.builder()
                .name("Bear")
                .username("Bear")
                .email("bear@gmail.com")
                .password("0..982883636")
                .birthday(LocalDate.of(2002, 5, 30))
                .gender("Male")
                .address("Viet Nam")
                .cid("1337")
                .phone("0865689470")
                .build();

        when(userRepository.existsByUsername(signUpDTO.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(signUpDTO.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(signUpDTO.getPassword())).thenReturn("EncodedPassword");

        ResponseEntity<?> response = userService.signUp(signUpDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}