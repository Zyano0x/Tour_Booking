package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.TourImageDTO;
import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Service.TourImage.TourImageService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TourImageController {
    private TourImageService tourImageService;

    @GetMapping("/tour-images/{id}")
    public ResponseEntity<TourImage> getTourImage(@PathVariable Long id) {
        return new ResponseEntity<>(tourImageService.getTourImage(id), HttpStatus.OK);
    }

    @GetMapping("/tour-images/tours/{tourId}")
    public ResponseEntity<List<TourImage>> getTourImageByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(tourImageService.getTourImageByTourId(tourId), HttpStatus.OK);
    }

    @GetMapping("/tour-images")
    public ResponseEntity<List<TourImage>> getTourImages() {
        return new ResponseEntity<>(tourImageService.getTourImages(), HttpStatus.OK);
    }
}
