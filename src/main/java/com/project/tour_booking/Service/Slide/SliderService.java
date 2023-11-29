package com.project.tour_booking.Service.Slide;

import java.util.List;

import com.project.tour_booking.DTO.SliderDTO;
import com.project.tour_booking.Entity.Slider;

public interface SliderService {
    void savSlider(SliderDTO sliderDTO);

    Slider getSlider(Long sliderId);

    List<Slider> getSliders();

    Slider updateSlider(Slider slider, Long sliderId);

    void updateSliderStatus(Long sliderId);
}
