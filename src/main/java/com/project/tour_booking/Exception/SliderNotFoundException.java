package com.project.tour_booking.Exception;

public class SliderNotFoundException extends RuntimeException {
    public SliderNotFoundException(Long id) {
        super("Slider có mã '" + id + "' không tồn tại!");
    }
}
