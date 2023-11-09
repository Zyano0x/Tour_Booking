package com.project.tour_booking;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Service.RoleService;

import lombok.*;

@SpringBootApplication
@AllArgsConstructor
public class TourBookingApplication implements CommandLineRunner {
	RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(TourBookingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Role[] roles = new Role[] {
				new Role(1L, "ADMIN"),
				new Role(2L, "USER")
		};

		for (Role role : roles) {
			roleService.saveRole(role);
		}
	}
}
