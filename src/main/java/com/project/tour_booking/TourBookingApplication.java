package com.project.tour_booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Service.RoleService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@AllArgsConstructor
public class TourBookingApplication implements CommandLineRunner {
	
	@Autowired
	private RoleService roleService;

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
