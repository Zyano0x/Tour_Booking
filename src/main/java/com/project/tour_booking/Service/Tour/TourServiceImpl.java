package com.project.tour_booking.Service.Tour;

import com.project.tour_booking.DTO.TourDTO;
import com.project.tour_booking.Entity.*;
import com.project.tour_booking.Exception.*;
import com.project.tour_booking.Repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TourServiceImpl implements TourService {
    private TourRepository tourRepository;
    private TypeOfTourRepository typeOfTourRepository;
    private TourImageRepository tourImageRepository;
    private DepartureDayRepository departureDayRepository;
    private BookingRepository bookingRepository;
    private DestinationRepository destinationRepository;

    @Override
    public Tour saveTour(TourDTO tourDTO) {
        try {
            Tour tour = new Tour();
            tour.setStatus(tourDTO.getStatus());
            tour.setDateOfPosting(LocalDate.now());
            tour.setName(tourDTO.getName());
            tour.setThumbnail(tourDTO.getThumbnail());
            tour.setDescription(tourDTO.getDescription());
            tour.setService(tourDTO.getService());
            tour.setTime(tourDTO.getTime());
            tour.setSchedule(tourDTO.getSchedule());
            tour.setPriceForAdult(tourDTO.getPriceForAdult());
            tour.setPriceForChildren(tourDTO.getPriceForChildren());
            tour.setDeparturePoint(tourDTO.getDeparturePoint());
            tour.setIsHot(tourDTO.getIsHot());
            TypeOfTour typeOfTour = typeOfTourRepository.findById(tourDTO.getTotId())
                    .filter(TypeOfTour::getStatus)
                    .orElseThrow(() -> new TypeOfTourNotFoundException(tourDTO.getTotId()));
            tour.setTypeOfTour(typeOfTour);

            Destination destination = destinationRepository.findById(tourDTO.getDestinationId())
                    .filter(Destination::getStatus)
                    .orElseThrow(() -> new DestinationNotFoundException(tourDTO.getDestinationId()));
            tour.setDestination(destination);
            tourRepository.save(tour);

            // Tạo dữ liệu trong bảng ảnh
            for (String path : tourDTO.getImages()) {
                TourImage tourImage = new TourImage(path);
                tourImage.setTour(tour);
                tourImageRepository.save(tourImage);
            }

            return tour;
        } catch (TypeOfTourNotFoundException | DestinationNotFoundException | DesnationNotEnableException |
                 TypeOfTourNotEnableException e) {
            throw new TourSaveException("Error while saving tour: " + e.getMessage());
        }
    }

    @Override
    public Tour getTour(Long id) {
        Optional<Tour> tour = tourRepository.findById(id);
        if (tour.isPresent()) {
            return tour.get();
        } else throw new TourNotFoundException(id);
    }

    @Override
    public List<Tour> getTours() {
        return tourRepository.findAll();
    }

    @Override
    public List<Tour> getTourByTypeOfTourId(Long totId) {
        return tourRepository.findAllByTypeOfTourId(totId);
    }

    @Override
    public Tour updateTour(TourDTO tourDTO, Long tourId) {
        Optional<Tour> tourOptional = tourRepository.findById(tourId);
        if (tourOptional.isPresent()) {
            Tour updateTour = tourOptional.get();
            updateTour.setName(tourDTO.getName());
            updateTour.setThumbnail(tourDTO.getThumbnail());
            updateTour.setDescription(tourDTO.getDescription());
            updateTour.setService(tourDTO.getService());
            updateTour.setTime(tourDTO.getTime());
            updateTour.setSchedule(tourDTO.getSchedule());
            updateTour.setPriceForAdult(tourDTO.getPriceForAdult());
            updateTour.setPriceForChildren(tourDTO.getPriceForChildren());
            updateTour.setDeparturePoint(tourDTO.getDeparturePoint());
            updateTour.setEditDate(LocalDate.now());
            updateTour.setIsHot(tourDTO.getIsHot());

            // Cần tối ưu hơn về thuật toán
            List<String> tourImageStrings = tourDTO.getImages();
            tourImageRepository.deleteAllByTourId(tourId);
            for (String path : tourImageStrings) {
                TourImage tourImage = new TourImage(path);
                tourImage.setTour(updateTour);
                tourImageRepository.save(tourImage);
            }

            if (updateTour.getStatus() != tourDTO.getStatus()) {

                if (!tourDTO.getStatus()) {
                    updateTour.setStatus(false);
                    // Chỉ hủy các ngày khởi hành còn hạn và đang được kích hoạt
                    List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tourId).stream().filter(departureDay -> departureDay.getStatus() && departureDay.getDepartureDay().isAfter(LocalDate.now())).collect(Collectors.toList());
                    if (!departureDays.isEmpty()) {
                        for (DepartureDay departureDay : departureDays) {
                            departureDay.setStatus(false);

                            // Hủy các booking liên quan
                            List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId()).stream().filter(booking -> booking.getStatus()).collect(Collectors.toList());
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
                } else {
                    if (typeOfTourRepository.findById(tourDTO.getTotId()).get().getStatus() && destinationRepository.findById(tourDTO.getDestinationId()).get().getStatus()) {
                        updateTour.setStatus(true);
                    } else if (!typeOfTourRepository.findById(tourDTO.getTotId()).get().getStatus()) {
                        throw new TypeOfTourNotEnableException(tourDTO.getTotId());
                    } else if (!destinationRepository.findById(tourDTO.getDestinationId()).get().getStatus()) {
                        throw new DesnationNotEnableException(tourDTO.getDestinationId());
                    }
                }
            }

            if (updateTour.getTypeOfTour().getId() != tourDTO.getTotId()) {
                Optional<TypeOfTour> tOTOptional = typeOfTourRepository.findById(tourDTO.getTotId());
                if (tOTOptional.isPresent()) {
                    if (updateTour.getStatus()) {
                        if (typeOfTourRepository.findById(tourDTO.getTotId()).get().getStatus()) {
                            updateTour.setTypeOfTour(tOTOptional.get());
                        } else throw new TypeOfTourNotEnableException(tourDTO.getTotId());
                    } else updateTour.setTypeOfTour(tOTOptional.get());
                } else throw new TypeOfTourNotFoundException(tourDTO.getTotId());
            }

            if (updateTour.getDestination().getId() != tourDTO.getDestinationId()) {
                Optional<Destination> destinationOptional = destinationRepository.findById(tourDTO.getDestinationId());
                if (destinationOptional.isPresent()) {
                    if (updateTour.getStatus()) {
                        if (destinationRepository.findById(tourDTO.getDestinationId()).get().getStatus()) {
                            updateTour.setDestination(destinationOptional.get());
                        } else throw new DesnationNotEnableException(tourDTO.getTotId());
                    } else updateTour.setDestination(destinationOptional.get());
                } else throw new DestinationNotFoundException(tourDTO.getDestinationId());
            }

            return tourRepository.save(updateTour);
        } else throw new TourNotFoundException(tourId);
    }

    @Override
    public void updateTourStatus(Long tourId) {
        Optional<Tour> tourOptional = tourRepository.findById(tourId);
        if (tourOptional.isPresent()) {
            Tour updateTour = tourOptional.get();

            if (updateTour.getStatus()) {
                updateTour.setStatus(false);

                // Chỉ hủy các ngày khởi hành còn hạn và đang được kích hoạt
                List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tourId).stream().filter(departureDay -> departureDay.getStatus() && departureDay.getDepartureDay().isAfter(LocalDate.now())).collect(Collectors.toList());
                if (!departureDays.isEmpty()) {
                    for (DepartureDay departureDay : departureDays) {
                        departureDay.setStatus(false);

                        // Hủy các booking liên quan
                        List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId()).stream().filter(booking -> booking.getStatus()).collect(Collectors.toList());
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
            } else {
                if (updateTour.getTypeOfTour().getStatus() && updateTour.getDestination().getStatus()) {
                    updateTour.setStatus(true);
                } else if (!updateTour.getTypeOfTour().getStatus()) {
                    throw new TypeOfTourNotEnableException(updateTour.getTypeOfTour().getId());
                } else if (!updateTour.getDestination().getStatus()) {
                    throw new DesnationNotEnableException(updateTour.getDestination().getId());
                }
            }
            tourRepository.save(updateTour);
        } else throw new TourNotFoundException(tourId);
    }
}
