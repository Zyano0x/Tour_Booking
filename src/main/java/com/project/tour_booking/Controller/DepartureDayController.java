package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.DepartureDayDTO;
import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Service.DepartureDay.DepartureDayService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DepartureDayController {
    private DepartureDayService departureDayService;

    @PostMapping("/admin/departure-day")
    public ResponseEntity<String> saveDepartureDay(@Valid @RequestBody DepartureDayDTO departureDayDTO) {
        departureDayService.saveDepartureDay(departureDayDTO);
        return new ResponseEntity<>("THÊM NGÀY KHỞI HÀNH THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/departure-day")
    public ResponseEntity<DepartureDay> getDepartureDay(@RequestParam Long id) {
        return new ResponseEntity<>(departureDayService.getDepartureDay(id), HttpStatus.OK);
    }

    @GetMapping("/departure-day/all")
    public ResponseEntity<List<DepartureDay>> getDepartureDays() {
        return new ResponseEntity<>(departureDayService.getDepartureDays(), HttpStatus.OK);
    }

    @GetMapping("/tour/{tourId}/departure-day")
    public ResponseEntity<List<DepartureDay>> getDepartureDaysByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(departureDayService.getDepartureDaysByTourId(tourId), HttpStatus.OK);
    }

    @PutMapping("/admin/update-departure-day")
    public ResponseEntity<DepartureDay> updateDepartureDay(@Valid @RequestBody DepartureDayDTO departureDayDTO, @RequestParam Long id) {
        return new ResponseEntity<>(departureDayService.updateDepartureDay(departureDayDTO, id), HttpStatus.OK);
    }

    @PutMapping("/admin/update-departure-day-status")
    public ResponseEntity<DepartureDay> updateDepartureDayStatus(@RequestParam Long id) {
        return new ResponseEntity<>(departureDayService.updateDepartureDayStatus(id), HttpStatus.OK);
    }
}