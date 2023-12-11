package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.TypeOfTourDTO;
import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Service.TypeOfTour.TypeOfTourService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class TypeOfTourController {
  private TypeOfTourService typeOfTourService;

  @PostMapping("/admin/type-of-tours")
  public ResponseEntity<String> saveTOT(@Valid @RequestBody TypeOfTourDTO typeOfTourDTO) {
    typeOfTourService.saveTOT(typeOfTourDTO);
    return new ResponseEntity<>("THÊM LOẠI TOUR THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/type-of-tours")
  public ResponseEntity<TypeOfTour> getTOT(@RequestParam Long id) {
    return new ResponseEntity<>(typeOfTourService.getTOT(id), HttpStatus.OK);
  }

  @GetMapping("/types-of-tours")
  public ResponseEntity<List<TypeOfTour>> getTOTS() {
    return new ResponseEntity<>(typeOfTourService.getTOTS(), HttpStatus.OK);
  }

  @PutMapping("/admin/update-type-of-tours-status")
  public ResponseEntity<TypeOfTour> updateTOTStatus(@RequestParam Long id) {
    return new ResponseEntity<>(typeOfTourService.updateTOTStatus(id), HttpStatus.OK);
  }

  @PutMapping("/admin/update-type-of-tours")
  public ResponseEntity<TypeOfTour> updateTOT(@Valid @RequestBody TypeOfTour typeOfTour, @RequestParam Long id) {
    return new ResponseEntity<>(typeOfTourService.updateTOT(typeOfTour, id), HttpStatus.OK);
  }
}
