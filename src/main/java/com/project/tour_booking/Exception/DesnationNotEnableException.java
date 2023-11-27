package com.project.tour_booking.Exception;

public class DesnationNotEnableException extends RuntimeException {
    public DesnationNotEnableException(Long id) {
        super("Địa điểm có mã '" + id + "' đã bị vô hiệu hóa!");
    }
}
