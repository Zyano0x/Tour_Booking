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

import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Service.TypeOfTour.TypeOfTourService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TypeOfTourController {
  private TypeOfTourService typeOfTourService;

  @PostMapping("/admin/type-of-tour")
  public ResponseEntity<String> saveTOT(@Valid @RequestBody TypeOfTour typeOfTour) {
    typeOfTourService.saveTOT(typeOfTour);
    return new ResponseEntity<>("THÊM LOẠI TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/admin/type-of-tour/{totId}")
  public ResponseEntity<TypeOfTour> getTOT(@PathVariable Long totId) {
    return new ResponseEntity<>(typeOfTourService.getTOT(totId), HttpStatus.OK);
  }

  @GetMapping("/type-of-tour/all")
  public ResponseEntity<List<TypeOfTour>> getTOTS() {
    return new ResponseEntity<>(typeOfTourService.getTOTS(), HttpStatus.OK);
  }

  @PutMapping("/{totId}")
  public ResponseEntity<TypeOfTour> updateTOT(@Valid @RequestBody TypeOfTour typeOfTour, @PathVariable Long totId) {
    return new ResponseEntity<>(typeOfTourService.updateTOT(typeOfTour, totId), HttpStatus.OK);
  }

  @DeleteMapping("/{totId}")
  public ResponseEntity<String> deleteTOT(@PathVariable Long totId) {
    typeOfTourService.deleteTOT(totId);
    return new ResponseEntity<>("XÓA THÀNH CÔNG", HttpStatus.OK);
  }
}
