package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Service.Booking.BookingService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class BookingController {
    private BookingService bookingService;

    @PostMapping("/bookings")
    public ResponseEntity<Booking> saveBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        return new ResponseEntity<>(bookingService.saveBooking(bookingDTO), HttpStatus.CREATED);
    }

    @GetMapping("/bookings/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.getBooking(bookingId), HttpStatus.OK);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return new ResponseEntity<>(bookingService.getBookings(), HttpStatus.OK);
    }

    @GetMapping("/bookings/users/{userId}")
    public ResponseEntity<List<Booking>> getAllBookingByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getAllBookingByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/bookings-valid/users/{userId}")
    public ResponseEntity<List<Booking>> getBookingIsValidOfUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getBookingIsValidOfUserId(userId), HttpStatus.OK);
    }

    @PutMapping("/update-booking-status/{id}")
    public ResponseEntity<String> updateBookingStatus(@PathVariable Long id) {
        bookingService.updateBookingStatus(id);
        return new ResponseEntity<>("Sent confirm email.", HttpStatus.OK);
    }

    @GetMapping("/confirm-cancel")
    public void confirmCancel(@RequestParam("token") String confirmationToken,
            @RequestParam("transaction") Long transactionCode, HttpServletResponse res) throws IOException {
        if (ResponseEntity.ok(bookingService.confirmCancel(transactionCode, confirmationToken)).hasBody())
            res.sendRedirect("/cancel-booking-success");
    }

    @PutMapping("/update-bookings/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@Valid @RequestBody BookingDTO bookingDTO, @PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.updateBooking(bookingDTO, bookingId), HttpStatus.OK);
    }
}
