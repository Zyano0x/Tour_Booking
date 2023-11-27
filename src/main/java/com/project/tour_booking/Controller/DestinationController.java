package com.project.tour_booking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.DestinationDTO;
import com.project.tour_booking.Entity.Destination;
import com.project.tour_booking.Service.Destination.DestinationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class DestinationController {
    private DestinationService destinationService;

    @PostMapping("/admin/destination")
    public ResponseEntity<String> saveDestination(@Valid @RequestBody DestinationDTO destinationDTO) {
        destinationService.saveDestination(destinationDTO);
        return new ResponseEntity<>("THÊM ĐỊA ĐIỂM THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/destination/{destinationId}")
    public ResponseEntity<Destination> getDestination(@PathVariable Long destinationId) {
        return new ResponseEntity<>(destinationService.getDestination(destinationId), HttpStatus.OK);
    }

    @GetMapping("/destination/all")
    public ResponseEntity<List<Destination>> getTOTS() {
        return new ResponseEntity<>(destinationService.getDestinations(), HttpStatus.OK);
    }

    @PutMapping("/admin/update-destination-status/{destinationId}")
    public ResponseEntity<Destination> updateDestinationStatus(@PathVariable Long destinationId) {
        return new ResponseEntity<>(destinationService.updateDestinationStatus(destinationId), HttpStatus.OK);
    }

    @PutMapping("/admin/update-destination/{destinationId}")
    public ResponseEntity<Destination> updateTOT(@Valid @RequestBody Destination destination,
            @PathVariable Long destinationId) {
        return new ResponseEntity<>(destinationService.updateDestination(destination, destinationId), HttpStatus.OK);
    }
}
