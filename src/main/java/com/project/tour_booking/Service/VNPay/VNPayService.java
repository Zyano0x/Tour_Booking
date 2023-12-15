package com.project.tour_booking.Service.VNPay;

import com.project.tour_booking.DTO.BookingDTO;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;

public interface VNPayService {
    ResponseEntity<?> createPayment(BookingDTO bookingDTO) throws UnsupportedEncodingException;
}
