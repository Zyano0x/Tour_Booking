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
@RequestMapping("/api")
public class TourImageController {
    private TourImageService tourImageService;

    @PostMapping("/admin/tour-image")
    public ResponseEntity<String> saveTourImage(@Valid @RequestBody TourImageDTO tourImageDTO) {
        tourImageService.saveTourImage(tourImageDTO);
        return new ResponseEntity<>("THÊM ẢNH THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/tour-image")
    public ResponseEntity<TourImage> getTourImage(@RequestParam Long id) {
        return new ResponseEntity<>(tourImageService.getTourImage(id), HttpStatus.OK);
    }

    @GetMapping("/tour/{tourId}/tour-images")
    public ResponseEntity<List<TourImage>> getTourImageByTourId(@PathVariable Long tourId) {
        return new ResponseEntity<>(tourImageService.getTourImageByTourId(tourId), HttpStatus.OK);
    }

    @GetMapping("/tour-images")
    public ResponseEntity<List<TourImage>> getTourImages() {
        return new ResponseEntity<>(tourImageService.getTourImages(), HttpStatus.OK);
    }

    @PutMapping("/admin/update-tour-image")
    public ResponseEntity<TourImage> updateTourImage(@Valid @RequestBody TourImageDTO tourImageDTO, @RequestParam Long id) {
        return new ResponseEntity<>(tourImageService.updateTourImage(tourImageDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete-tour-image")
    public ResponseEntity<String> deleteTourImage(@RequestParam Long id) {
        tourImageService.deleteTourImage(id);
        return new ResponseEntity<>("XÓA ẢNH THÀNH CÔNG!", HttpStatus.OK);
    }
}
