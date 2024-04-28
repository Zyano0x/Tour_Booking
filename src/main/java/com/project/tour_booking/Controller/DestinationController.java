package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.DestinationDTO;
import com.project.tour_booking.Entity.Destination;
import com.project.tour_booking.Service.Destination.DestinationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class DestinationController {
    private DestinationService destinationService;

    @GetMapping("/destinations/{id}")
    public ResponseEntity<Destination> getDestination(@PathVariable Long id) {
        return new ResponseEntity<>(destinationService.getDestination(id), HttpStatus.OK);
    }

    @GetMapping("/destinations")
    public ResponseEntity<List<Destination>> getDestinations() {
        return new ResponseEntity<>(destinationService.getDestinations(), HttpStatus.OK);
    }
}
