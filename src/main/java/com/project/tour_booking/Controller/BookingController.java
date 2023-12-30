package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Service.Booking.BookingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BookingController {
    private BookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity<String> saveBooking(@Valid @RequestBody BookingDTO bookingDTO) {
        bookingService.saveBooking(bookingDTO);
        return new ResponseEntity<>("ĐẶT TOUR THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Booking> getBooking(@PathVariable Long bookingId) {
        return new ResponseEntity<>(bookingService.getBooking(bookingId), HttpStatus.OK);
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return new ResponseEntity<>(bookingService.getBookings(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/bookings")
    public ResponseEntity<List<Booking>> getAllBookingByUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getAllBookingByUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/bookings-valid")
    public ResponseEntity<List<Booking>> getBookingIsValidOfUserId(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getBookingIsValidOfUserId(userId), HttpStatus.OK);
    }

    @GetMapping("/admin/tour/{tourId}/bookings")
    public ResponseEntity<List<Booking>> getAllBookingByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(bookingService.getAllBookingByTourId(tourId), HttpStatus.OK);
    }

    @GetMapping("/admin/user/{userId}/tour/{tourId}/bookings")
    public ResponseEntity<List<Booking>> getBookingByUserIdAndTourId(@PathVariable Long userId,
            @PathVariable Long tourId) {
        return new ResponseEntity<>(bookingService.getBookingByUserIdAndTourId(userId, tourId), HttpStatus.OK);
    }

    @PutMapping("/update-booking-status")
    public ResponseEntity<?> updateBookingStatus(@RequestParam Long id) {
        return ResponseEntity.ok(bookingService.updateBookingStatus(id));
    }

    @GetMapping("/confirm-cancel")
    public ResponseEntity<?> confirmCancel(@RequestParam("token") String confirmationToken,
            @RequestParam("transaction") Long transactionCode) {
        return ResponseEntity.ok(bookingService.confirmCancel(transactionCode, confirmationToken));
    }

    @PutMapping("/update-booking/{bookingId}")
    public ResponseEntity<String> updateBooking(@Valid @RequestBody BookingDTO bookingDTO,
            @PathVariable Long bookingId) {
        bookingService.updateBooking(bookingDTO, bookingId);
        return new ResponseEntity<>("CẬP NHẬT ĐƠN ĐẶT TOUR THÀNH CÔNG!", HttpStatus.OK);
    }
}
