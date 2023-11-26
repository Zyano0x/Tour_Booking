package com.project.tour_booking.Service.Destination;

import java.util.List;

import com.project.tour_booking.DTO.DestinationDTO;
import com.project.tour_booking.Entity.Destination;

public interface DestinationService {
  void saveDestination(DestinationDTO destinationDTO);

  Destination getDestination(Long destinationId);

  List<Destination> getDestinations();

  Destination updateDestination(Destination destination, Long destinationId);

  Destination updateDestinationStatus(Long destinationId);
}
