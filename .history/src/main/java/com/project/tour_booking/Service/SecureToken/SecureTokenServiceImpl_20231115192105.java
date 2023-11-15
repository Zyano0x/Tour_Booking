package com.project.tour_booking.Service.Admin.SecureToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Repository.SecureTokenRepository;

@Service
public class SecureTokenServiceImpl implements SecureTokenService {

    @Autowired
    private SecureTokenRepository secureTokenRepository;

    public SecureTokenServiceImpl(SecureTokenRepository secureTokenRepository) {
        this.secureTokenRepository = secureTokenRepository;
    }

    @Override
    public void removeToken(SecureToken token) {
        secureTokenRepository.delete(token);
    }
}
