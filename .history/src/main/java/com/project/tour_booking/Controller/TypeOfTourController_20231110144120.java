package com.project.tour_booking.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Service.TypeOfTourService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/typeoftour")
@AllArgsConstructor
public class TypeOfTourController {
  TypeOfTourService typeOfTourService;

  @PostMapping("")
  public ResponseEntity<String> saveTOT(TypeOfTour typeOfTour) {
    typeOfTourService.saveTOT(typeOfTour);
    return new ResponseEntity<>("Thêm loại tour thành công!", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TypeOfTour> getTOT(@PathVariable Long id) {
    return new ResponseEntity<>(typeOfTourService.getTOT(id), HttpStatus.OK);
  }
}
