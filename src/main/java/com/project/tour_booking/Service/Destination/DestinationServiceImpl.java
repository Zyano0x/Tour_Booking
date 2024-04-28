package com.project.tour_booking.Service.Destination;

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
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {
    private final DestinationRepository destinationRepository;
    private final TourRepository tourRepository;
    private final DepartureDayRepository departureDayRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Override
    public Destination saveDestination(DestinationDTO destinationDTO) {
        Destination destination = modelMapper.map(destinationDTO, Destination.class);
        destination.setStatus(true);

        destinationRepository.save(destination);

        return destination;
    }

    @Override
    public Destination getDestination(Long destinationId) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
        if (destinationOptional.isPresent()) {
            return destinationOptional.get();
        }
        else throw new DestinationNotFoundException(destinationId);
    }

    @Override
    public List<Destination> getDestinations() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination updateDestination(Destination destination, Long destinationId) {
        Optional<Destination> destinationOpt = destinationRepository.findById(destinationId);
        if (destinationOpt.isPresent()) {
            Destination currDestination = destinationOpt.get();
            currDestination.setName(destination.getName());
            currDestination.setThumbnail(destination.getThumbnail());
            currDestination.setIsHot(destination.getIsHot());

            if (currDestination.getStatus() != destination.getStatus()) {
                currDestination.setStatus(destination.getStatus());
                if (!currDestination.getStatus()) {
                    // Tìm và disable các tour thuộc địa điểm
                    List<Tour> tours = tourRepository.findAllByDestinationId(destinationId).stream().filter(Tour::getStatus).toList();
                    if (!tours.isEmpty()) {
                        for (Tour tour : tours) {
                            tour.setStatus(false);

                            // Chỉ hủy các ngày khởi hành còn hạn và đang được kích hoạt
                            List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId()).stream().filter(departureDay -> departureDay.getStatus() && departureDay.getDepartureDay().isAfter(LocalDate.now())).toList();
                            for (DepartureDay departureDay : departureDays) {
                                departureDay.setStatus(false);

                                // Hủy các booking liên quan
                                List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId()).stream().filter(Booking::getStatus).toList();
                                if (!bookings.isEmpty()) {
                                    int totalQuantity = 0;
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
            destinationRepository.save(currDestination);
            return currDestination;
        } else throw new TypeOfTourNotFoundException(destinationId);
    }

    @Override
    public Destination updateDestinationStatus(Long destinationId) {
        Optional<Destination> destinationOptional = destinationRepository.findById(destinationId);
        if (destinationOptional.isPresent()) {
            Destination updateDestination = destinationOptional.get();
            if (updateDestination.getStatus()) {
                updateDestination.setStatus(false);
                updateDestination.setIsHot(false);

                // Tìm và disable các tour thuộc địa điểm
                List<Tour> tours = tourRepository.findAllByDestinationId(destinationId).stream().filter(Tour::getStatus).toList();
                if (!tours.isEmpty()) {
                    for (Tour tour : tours) {
                        tour.setStatus(false);

                        // Chỉ hủy các ngày khởi hành còn hạn và đang được kích hoạt
                        List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId()).stream().filter(departureDay -> departureDay.getStatus() && departureDay.getDepartureDay().isAfter(LocalDate.now())).toList();
                        if (!departureDays.isEmpty()) {
                            for (DepartureDay departureDay : departureDays) {
                                departureDay.setStatus(false);

                                // Hủy các booking liên quan
                                List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId()).stream().filter(Booking::getStatus).toList();
                                if (!bookings.isEmpty()) {
                                    int totalQuantity = 0;
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
            destinationRepository.save(updateDestination);
            return updateDestination;
        } else throw new DestinationNotFoundException(destinationId);
    }
}
