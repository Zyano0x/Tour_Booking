package com.project.tour_booking.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.tour_booking.DTO.DepartureDayDTO;
import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Service.DepartureDay.DepartureDayService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DepartureDayController {
  private DepartureDayService departureDayService;

  @PostMapping("/admin/departure-day")
  public ResponseEntity<String> saveDepartureDay(@Valid @RequestBody DepartureDayDTO departureDayDTO) {
    departureDayService.saveDepartureDay(departureDayDTO);
    return new ResponseEntity<>("THÊM NGÀY KHỞI HÀNH THÀNH CÔNG!", HttpStatus.CREATED);
  }

  @GetMapping("/departure-day/{departureDayId}")
  public ResponseEntity<DepartureDay> getDepartureDay(@PathVariable Long departureDayId) {
    return new ResponseEntity<>(departureDayService.getDepartureDay(departureDayId), HttpStatus.OK);
  }

  @GetMapping("/tour/{tourId}/departure-day")
  public ResponseEntity<List<DepartureDay>> getDepartureDaysByTourId(@PathVariable Long tourId) {
    return new ResponseEntity<>(departureDayService.getDepartureDaysByTourId(tourId), HttpStatus.OK);
  }

  @PutMapping("/admin/update-departure-day/{departureDayId}")
  public ResponseEntity<DepartureDay> updateDepartureDay(@Valid @RequestBody DepartureDayDTO departureDayDTO,
      @PathVariable Long departureDayId) {
    return new ResponseEntity<>(departureDayService.updateDepartureDay(departureDayDTO, departureDayId),
        HttpStatus.OK);
  }

  @PutMapping("/admin/update-status-departure-day/{departureDayId}")
  public ResponseEntity<DepartureDay> updateDepartureDay(@PathVariable Long departureDayId) {
    return new ResponseEntity<>(departureDayService.updateStatusDepartureDay(departureDayId),
        HttpStatus.OK);
  }
}