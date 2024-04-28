package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.TourDTO;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Service.Tour.TourService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TourController {
    private TourService tourService;

    @GetMapping("/tours/{id}")
    public ResponseEntity<Tour> getTour(@PathVariable Long id) {
        return new ResponseEntity<>(tourService.getTour(id), HttpStatus.OK);
    }

    @GetMapping("/tours")
    public ResponseEntity<List<Tour>> getTours() {
        return new ResponseEntity<>(tourService.getTours(), HttpStatus.OK);
    }

    @GetMapping("/tours/type-of-tours/{totId}")
    public ResponseEntity<List<Tour>> getTourByTypeOfTourId(@PathVariable Long totId) {
        return new ResponseEntity<>(tourService.getTourByTypeOfTourId(totId), HttpStatus.OK);
    }
}
