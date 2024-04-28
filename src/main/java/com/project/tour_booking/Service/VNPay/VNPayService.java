package com.project.tour_booking.Service.VNPay;

import com.project.tour_booking.DTO.BookingDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

public interface VNPayService {
    Map<String, String> createPayment(BookingDTO bookingDTO);
}
