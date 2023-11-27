package com.project.tour_booking;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TourBookingApplication implements CommandLineRunner {

	private final UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(TourBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (userRepository.findByRole(Role.ADMIN) == null) {
			User user = new User();
			user.setName("WorryZy");
            user.setUsername("WorryZy");
            user.setEmail("ghuy042@gmail.com");
            user.setPassword(new BCryptPasswordEncoder().encode("0..982883636"));
            user.setBirthday(LocalDate.of(2002, 05, 30));
            user.setGender("Male");
            user.setAddress("Viet Nam");
            user.setCid("1337");
            user.setPhone("0865689470");
            user.setRole(Role.ADMIN);
			user.setEnabled(true);
			user.setLocked(false);

			userRepository.save(user);
		}
	}
}
