package com.project.tour_booking.Exception;

public class TourSaveException extends RuntimeException {
    public TourSaveException(String message) {
        super("Error while saving tour: " + message);
    }
}
