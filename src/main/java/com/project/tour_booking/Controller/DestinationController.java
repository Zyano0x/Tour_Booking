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
@RequestMapping("/api")
@AllArgsConstructor
public class DestinationController {
    private DestinationService destinationService;

    @PostMapping("/admin/destination")
    public ResponseEntity<String> saveDestination(@Valid @RequestBody DestinationDTO destinationDTO) {
        destinationService.saveDestination(destinationDTO);
        return new ResponseEntity<>("THÊM ĐỊA ĐIỂM THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/destination")
    public ResponseEntity<Destination> getDestination(@RequestParam Long id) {
        return new ResponseEntity<>(destinationService.getDestination(id), HttpStatus.OK);
    }

    @GetMapping("/destinations")
    public ResponseEntity<List<Destination>> getTOTS() {
        return new ResponseEntity<>(destinationService.getDestinations(), HttpStatus.OK);
    }

    @PutMapping("/admin/update-destination-status")
    public ResponseEntity<Destination> updateDestinationStatus(@RequestParam Long id) {
        return new ResponseEntity<>(destinationService.updateDestinationStatus(id), HttpStatus.OK);
    }

    @PutMapping("/admin/update-destination")
    public ResponseEntity<Destination> updateTOT(@Valid @RequestBody Destination destination,
            @RequestParam Long id) {
        return new ResponseEntity<>(destinationService.updateDestination(destination, id), HttpStatus.OK);
    }
}
