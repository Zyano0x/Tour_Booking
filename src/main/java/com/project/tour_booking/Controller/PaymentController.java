package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Service.Booking.BookingService;
import com.project.tour_booking.Service.VNPay.VNPayService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final VNPayService vnPayService;
    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody BookingDTO bookingDTO, HttpSession session)
            throws UnsupportedEncodingException {
        session.setAttribute("BookingInfo", bookingDTO);
        return vnPayService.createPayment(bookingDTO);
    }

    @GetMapping("/payment_info")
    public void paymentInfo(@RequestParam("vnp_ResponseCode") String response,
            @RequestParam("vnp_TxnRef") Long transactionCode,
            HttpSession session,
            HttpServletResponse res) throws IOException {
        BookingDTO bookingDTO = (BookingDTO) session.getAttribute("BookingInfo");
        bookingDTO.setTransactionCode(transactionCode);
        if (response.equals("00")) {
            bookingService.saveBooking(bookingDTO);
            res.sendRedirect("/booking-success"); // Success
        } else {
            res.sendRedirect("/booking-fail"); // Fail
        }
    }
}
