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
@RequestMapping("/api/v1")
@AllArgsConstructor
public class TypeOfTourController {
    private TypeOfTourService typeOfTourService;

    @GetMapping("/type-of-tours/{id}")
    public ResponseEntity<TypeOfTour> getTOT(@PathVariable Long id) {
        return new ResponseEntity<>(typeOfTourService.getTOT(id), HttpStatus.OK);
    }

    @GetMapping("/types-of-tours")
    public ResponseEntity<List<TypeOfTour>> getTOTS() {
        return new ResponseEntity<>(typeOfTourService.getTOTS(), HttpStatus.OK);
    }
}
