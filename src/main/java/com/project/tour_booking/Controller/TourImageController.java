package com.project.tour_booking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.TourImageDTO;
import com.project.tour_booking.Entity.TourImage;
import com.project.tour_booking.Service.TourImage.TourImageService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

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

  @GetMapping("/tour-image/{tourImageId}")
  public ResponseEntity<TourImage> getTourImage(@PathVariable Long tourImageId) {
    return new ResponseEntity<>(tourImageService.getTourImage(tourImageId), HttpStatus.OK);
  }

  @GetMapping("/tour/{tourId}/tour-image")
  public ResponseEntity<List<TourImage>> getTourImageByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(tourImageService.getTourImageByTourId(tourId), HttpStatus.OK);
  }

  @GetMapping("/tour-image/all")
  public ResponseEntity<List<TourImage>> getAllTourImage() {
    return new ResponseEntity<>(tourImageService.geAlltTourImage(), HttpStatus.OK);
  }

  @PutMapping("/admin/update-tour-image/{tourImageId}")
  public ResponseEntity<TourImage> updateTourImage(@Valid @RequestBody TourImageDTO tourImageDTO,
      @PathVariable Long tourImageId) {
    return new ResponseEntity<>(tourImageService.updateTourImage(tourImageDTO, tourImageId), HttpStatus.OK);
  }

  @DeleteMapping("/admin/delete-tour-image/{tourImageId}")
  public ResponseEntity<String> deleteTourImage(@PathVariable Long tourImageId) {
    tourImageService.deleteTourImage(tourImageId);
    return new ResponseEntity<>("XÓA ẢNH THÀNH CÔNG!", HttpStatus.OK);
  }
}