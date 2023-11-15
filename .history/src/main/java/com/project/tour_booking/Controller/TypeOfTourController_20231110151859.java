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
import com.project.tour_booking.Service.TypeOfTourService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/typeoftour")
@AllArgsConstructor
public class TypeOfTourController {
  private TypeOfTourService typeOfTourService;

  @PostMapping("")
  public ResponseEntity<String> saveTOT(@Valid @RequestBody TypeOfTour typeOfTour) {
    typeOfTourService.saveTOT(typeOfTour);
    return new ResponseEntity<>("THÊM LOẠI TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TypeOfTour> getTOT(@PathVariable Long id) {
    return new ResponseEntity<>(typeOfTourService.getTOT(id), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<TypeOfTour>> getTOTS() {
    return new ResponseEntity<>(typeOfTourService.getTOTS(), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TypeOfTour> updateTOT(@Valid @RequestBody TypeOfTour typeOfTour, @PathVariable Long id) {
    return new ResponseEntity<>(typeOfTourService.updateTOT(typeOfTour, id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteTOT(@PathVariable Long id) {
    return new ResponseEntity<>("Xóa loại tour id '" + id + "' thành công!", HttpStatus.NO_CONTENT);
  }
}
