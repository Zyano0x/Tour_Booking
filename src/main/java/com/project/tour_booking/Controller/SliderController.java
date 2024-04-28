package com.project.tour_booking.Controller;

import com.project.tour_booking.DTO.SliderDTO;
import com.project.tour_booking.Entity.Slider;
import com.project.tour_booking.Service.Slide.SliderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class SliderController {
    private SliderService sliderService;

    @GetMapping("/sliders/{sliderId}")
    public ResponseEntity<Slider> getSlider(@PathVariable Long sliderId) {
        return new ResponseEntity<>(sliderService.getSlider(sliderId), HttpStatus.OK);
    }

    @GetMapping("/sliders")
    public ResponseEntity<List<Slider>> getSliders() {
        return new ResponseEntity<>(sliderService.getSliders(), HttpStatus.OK);
    }
}
