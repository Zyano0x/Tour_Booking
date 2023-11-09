package com.project.tour_booking.Service;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.Role;
import com.project.tour_booking.Repository.RoleRepository;

import lombok.*;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
  private RoleRepository roleRepository;

  @Override
  public void saveRole(Role role) {
    roleRepository.save(role);
  }
}
