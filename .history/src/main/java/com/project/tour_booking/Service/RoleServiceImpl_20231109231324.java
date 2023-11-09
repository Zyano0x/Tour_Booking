package com.project.tour_booking.Service;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Repository.RoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
  RoleRepository roleRepository;
}
