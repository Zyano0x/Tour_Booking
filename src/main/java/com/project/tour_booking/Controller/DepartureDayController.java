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
@RequestMapping("/api/v1")
@CrossOrigin
public class DepartureDayController {
    private DepartureDayService departureDayService;

    @GetMapping("/departure-days/{id}")
    public ResponseEntity<DepartureDay> getDepartureDay(@PathVariable Long id) {
        return new ResponseEntity<>(departureDayService.getDepartureDay(id), HttpStatus.OK);
    }

    @GetMapping("/departure-days")
    public ResponseEntity<List<DepartureDay>> getDepartureDays() {
        return new ResponseEntity<>(departureDayService.getDepartureDays(), HttpStatus.OK);
    }

    @GetMapping("/departure-days/tours/{tourId}")
    public ResponseEntity<List<DepartureDay>> getDepartureDaysByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(departureDayService.getDepartureDaysByTourId(tourId), HttpStatus.OK);
    }
}
