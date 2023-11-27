package com.project.tour_booking.Service.SecureToken;

import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Repository.SecureTokenRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SecureTokenServiceImpl implements SecureTokenService {

    private final SecureTokenRepository secureTokenRepository;

    @Override
    public void removeToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }
}
