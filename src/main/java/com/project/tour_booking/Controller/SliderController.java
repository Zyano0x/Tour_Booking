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

import com.project.tour_booking.DTO.SliderDTO;
import com.project.tour_booking.Entity.Slider;
import com.project.tour_booking.Service.Slide.SliderService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class SliderController {
    private SliderService sliderService;

    @PostMapping("/admin/slider")
    public ResponseEntity<String> saveSlider(@Valid @RequestBody SliderDTO sliderDTO) {
        sliderService.savSlider(sliderDTO);
        return new ResponseEntity<>("THÊM SLIDER THÀNH CÔNG!", HttpStatus.CREATED);
    }

    @GetMapping("/slider/{sliderId}")
    public ResponseEntity<Slider> getSlider(@PathVariable Long sliderId) {
        return new ResponseEntity<>(sliderService.getSlider(sliderId), HttpStatus.OK);
    }

    @GetMapping("/sliders")
    public ResponseEntity<List<Slider>> getSliders() {
        return new ResponseEntity<>(sliderService.getSliders(), HttpStatus.OK);
    }

    @PutMapping("/admin/update-slider/{sliderId}")
    public ResponseEntity<Slider> updateSlider(@Valid @RequestBody Slider slider, @PathVariable Long sliderId) {
        return new ResponseEntity<>(sliderService.updateSlider(slider, sliderId), HttpStatus.OK);
    }

    @PutMapping("/admin/update-slider-status/{sliderId}")
    public ResponseEntity<String> updateSlider(@PathVariable Long sliderId) {
        sliderService.updateSliderStatus(sliderId);
        return new ResponseEntity<>("CẬP NHẬT TRẠNG THÁI SLIDER THÀNH CÔNG!", HttpStatus.OK);
    }
}
