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
@RequestMapping("/api")
@AllArgsConstructor
public class TourController {
    private TourService tourService;

    @PostMapping("admin/tour")
    public ResponseEntity<String> saveTour(@Valid @RequestBody TourDTO tourDTO) {
        tourService.saveTour(tourDTO);
        return new ResponseEntity<>("THÊM TOUR THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/tour")
    public ResponseEntity<Tour> getTour(@RequestParam Long id) {
        return new ResponseEntity<>(tourService.getTour(id), HttpStatus.OK);
    }

    @GetMapping("/tours")
    public ResponseEntity<List<Tour>> getTours() {
        return new ResponseEntity<>(tourService.getTours(), HttpStatus.OK);
    }

    @GetMapping("/type-of-tours/{totId}/toura")
    public ResponseEntity<List<Tour>> getTourByTypeOfTourId(@PathVariable Long totId) {
        return new ResponseEntity<>(tourService.getTourByTypeOfTourId(totId), HttpStatus.OK);
    }

    @PutMapping("/admin/update-tour")
    public ResponseEntity<Tour> updateTour(@Valid @RequestBody TourDTO tourDTO, @RequestParam Long id) {
        return new ResponseEntity<>(tourService.updateTour(tourDTO, id), HttpStatus.OK);
    }

    @PutMapping("/admin/update-tour-status")
    public ResponseEntity<String> updateTourStatus(@RequestParam Long id) {
        tourService.updateTourStatus(id);
        return new ResponseEntity<>("CHUYỂN ĐỔI TRẠNG THÁI THÀNH CÔNG!", HttpStatus.OK);
    }
}
