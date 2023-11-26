package com.project.tour_booking.Service.Destination;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.DestinationDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Destination;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Exception.DestinationNotFoundException;
import com.project.tour_booking.Exception.TypeOfTourNotFoundException;
import com.project.tour_booking.Repository.BookingRepository;
import com.project.tour_booking.Repository.DepartureDayRepository;
import com.project.tour_booking.Repository.DestinationRepository;
import com.project.tour_booking.Repository.TourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DestinationServiceImpl implements DestinationService {
  private DestinationRepository destinationRepository;
  private TourRepository tourRepository;
  private DepartureDayRepository departureDayRepository;
  private BookingRepository bookingRepository;

  @Override
  public void saveDestination(DestinationDTO destinationDTO) {
    Destination destination = new Destination();
    destination.setName(destinationDTO.getName());
    destination.setStatus(true);
    destinationRepository.save(destination);
  }

  @Override
  public Destination getDestination(Long destinationId) {
    Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
    if (destinationOptional.isPresent())
      return destinationOptional.get();
    else
      throw new DestinationNotFoundException(destinationId);
  }

  @Override
  public List<Destination> getDestinations() {
    return (List<Destination>) destinationRepository.findAll();
  }

  @Override
  public Destination updateDestination(Destination destination, Long destinationId) {
    Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);

    if (destinationOptional.isPresent()) {
      Destination updateDestination = destinationOptional.get();

      updateDestination.setName(destination.getName());

      if (updateDestination.getStatus() != destination.getStatus()) {
        updateDestination.setStatus(destination.getStatus());

        if (!updateDestination.getStatus()) {
          // Tìm và disable các tour thuộc địa điểm
          List<Tour> tours = tourRepository.findAllByDestinationId(destinationId).stream()
              .filter(
                  tour -> tour.getStatus())
              .collect(Collectors.toList());
          if (!tours.isEmpty()) {
            for (Tour tour : tours) {
              tour.setStatus(false);

              // Chỉ hủy các ngày khởi hành còn hạn và đang được kích hoạt
              List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId()).stream()
                  .filter(
                      departureDay -> departureDay.getStatus()
                          && departureDay.getDepartureDay().isAfter(LocalDate.now()))
                  .collect(Collectors.toList());
              for (DepartureDay departureDay : departureDays) {
                departureDay.setStatus(false);

                // Hủy các booking liên quan
                List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId()).stream()
                    .filter(booking -> booking.getStatus()).collect(Collectors.toList());
                if (!bookings.isEmpty()) {
                  Integer totalQuantity = 0;
                  for (Booking booking : bookings) {
                    booking.setStatus(false);
                    bookingRepository.save(booking);
                    totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
                  }
                  // Cập nhật trường số lượng cho ngày khởi hành
                  departureDay.setQuantity(departureDay.getQuantity() + totalQuantity);
                }
                departureDayRepository.save(departureDay);
              }

              tourRepository.save(tour);
            }
          }
        }
      }

      return destinationRepository.save(updateDestination);
    } else
      throw new TypeOfTourNotFoundException(destinationId);
  }

  @Override
  public Destination updateDestinationStatus(Long destinationId) {
    Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
    if (destinationOptional.isPresent()) {
      Destination updateDestination = destinationOptional.get();
      if (updateDestination.getStatus()) {
        updateDestination.setStatus(false);

        // Tìm và disable các tour thuộc địa điểm
        List<Tour> tours = tourRepository.findAllByDestinationId(destinationId).stream()
            .filter(
                tour -> tour.getStatus())
            .collect(Collectors.toList());
        if (!tours.isEmpty()) {
          for (Tour tour : tours) {
            tour.setStatus(false);

            // Chỉ hủy các ngày khởi hành còn hạn và đang được kích hoạt
            List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId()).stream()
                .filter(
                    departureDay -> departureDay.getStatus()
                        && departureDay.getDepartureDay().isAfter(LocalDate.now()))
                .collect(Collectors.toList());
            if (!departureDays.isEmpty()) {
              for (DepartureDay departureDay : departureDays) {
                departureDay.setStatus(false);

                // Hủy các booking liên quan
                List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId()).stream()
                    .filter(booking -> booking.getStatus()).collect(Collectors.toList());
                if (!bookings.isEmpty()) {
                  Integer totalQuantity = 0;
                  for (Booking booking : bookings) {
                    booking.setStatus(false);
                    bookingRepository.save(booking);
                    totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
                  }
                  // Cập nhật trường số lượng cho ngày khởi hành
                  departureDay.setQuantity(departureDay.getQuantity() + totalQuantity);
                }
                departureDayRepository.save(departureDay);
              }
            }

            tourRepository.save(tour);
          }
        }
      } else {
        updateDestination.setStatus(true);
      }

      return destinationRepository.save(updateDestination);
    } else
      throw new DestinationNotFoundException(destinationId);
  }

}
