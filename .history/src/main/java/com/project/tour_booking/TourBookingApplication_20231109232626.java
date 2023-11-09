package com.project.tour_booking;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Service.RoleService;
import com.project.tour_booking.Service.UserService;

import lombok.*;

@SpringBootApplication
@AllArgsConstructor
public class TourBookingApplication implements CommandLineRunner {
	private RoleService roleService;
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(TourBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role[] users = new Role[] {
				new Role(1L, "ADMIN"),
				new Role(2L, "USER")
		};

		for (Role role : users) {
			roleService.saveRole(role);
		}

		User[] users = new User[] {
				new User(1L, "admin", "admin123", "admin", Date, null, null, null, null, null, false, null),
		};

		for (User role : users) {
			userservice.saveRole(role);
		}
	}
}
