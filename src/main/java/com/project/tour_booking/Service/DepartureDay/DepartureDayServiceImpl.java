package com.project.tour_booking.Service.DepartureDay;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.DepartureDayDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Exception.DepartureDayCannotEnableException;
import com.project.tour_booking.Exception.DepartureDayNotFoundException;
import com.project.tour_booking.Exception.TourNotEnableException;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Repository.BookingRepository;
import com.project.tour_booking.Repository.DepartureDayRepository;
import com.project.tour_booking.Repository.TourRepository;

import lombok.AllArgsConstructor;

@Service
@RequiredArgsConstructor
public class DepartureDayServiceImpl implements DepartureDayService {
    private final DepartureDayRepository departureDayRepository;
    private final TourRepository tourRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Override
    public DepartureDay saveDepartureDay(DepartureDayDTO departureDayDTO) {
        DepartureDay newDepartureDay = modelMapper.map(departureDayDTO, DepartureDay.class);
        newDepartureDay.setStatus(true);

        Optional<Tour> tourOptional = tourRepository.findById(departureDayDTO.getTourId());
        if (tourOptional.isPresent()) {
            if (tourOptional.get().getStatus()) {
                newDepartureDay.setTour(tourOptional.get());
            } else {
                throw new TourNotEnableException(departureDayDTO.getTourId());
            }
        } else
            throw new TourNotFoundException(departureDayDTO.getTourId());
        departureDayRepository.save(newDepartureDay);
        return newDepartureDay;
    }

    @Override
    public DepartureDay getDepartureDay(Long departureDayId) {
        Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
        if (departureDayOptional.isPresent())
            return departureDayOptional.get();
        else
            throw new DepartureDayNotFoundException(departureDayId);
    }

    @Override
    public List<DepartureDay> getDepartureDays() {
        return (List<DepartureDay>) departureDayRepository.findAll();
    }

    @Override
    public List<DepartureDay> getDepartureDaysByTourId(Long tourId) {
        return departureDayRepository.findAllByTourId(tourId);
    }

    @Override
    public DepartureDay updateDepartureDay(DepartureDayDTO departureDayDTO, Long departureDayId) {
        // Kiểm tra tồn tại
        Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
        if (departureDayOptional.isPresent()) {
            DepartureDay updateDepartureDay = departureDayOptional.get();

            // Kiểm tra ngày khởi hành có diễn ra chưa
            if (updateDepartureDay.getDepartureDay().isAfter(LocalDate.now())) {
                updateDepartureDay.setQuantity(departureDayDTO.getQuantity());
                updateDepartureDay.setDepartureDay(departureDayDTO.getDepartureDay());
                updateDepartureDay.setDepartureTime(departureDayDTO.getDepartureTime());

                // Kiểm tra có thay đổi status không
                if (departureDayDTO.getStatus() != updateDepartureDay.getStatus()) {
                    // Hủy status của booking
                    if (!departureDayDTO.getStatus()) {
                        updateDepartureDay.setStatus(false);
                        List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDayId).stream()
                                .filter(Booking::getStatus).toList();
                        if (!bookings.isEmpty()) {
                            int totalQuantity = 0;
                            for (Booking booking : bookings) {
                                booking.setStatus(false);
                                bookingRepository.save(booking);
                                totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
                            }
                            // Cập nhật trường số lượng của ngày khởi hành
                            updateDepartureDay.setQuantity(updateDepartureDay.getQuantity() + totalQuantity);
                        }
                    } else {
                        if (tourRepository.findById(departureDayDTO.getTourId()).get().getStatus())
                            updateDepartureDay.setStatus(true);
                        else
                            throw new TourNotEnableException(departureDayDTO.getTourId());
                    }
                }

                if (!Objects.equals(updateDepartureDay.getTour().getId(), departureDayDTO.getTourId())) {
                    Optional<Tour> tourOptional = tourRepository.findById(departureDayDTO.getTourId());
                    if (tourOptional.isPresent()) {
                        if (updateDepartureDay.getStatus()) {
                            if (tourRepository.findById(departureDayDTO.getTourId()).get().getStatus())
                                updateDepartureDay.setTour(tourOptional.get());
                            else
                                throw new TourNotEnableException(departureDayDTO.getTourId());
                        } else {
                            updateDepartureDay.setTour(tourOptional.get());
                        }
                    } else
                        throw new TourNotFoundException(departureDayDTO.getTourId());
                }
                return departureDayRepository.save(updateDepartureDay);
            } else
                throw new DepartureDayCannotEnableException(updateDepartureDay.getDepartureDay());
        } else
            throw new DepartureDayNotFoundException(departureDayId);
    }

    @Override
    public DepartureDay updateDepartureDayStatus(Long departureDayId) {
        // Kiểm tra tồn tại
        Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
        if (departureDayOptional.isPresent()) {
            DepartureDay departureDay = departureDayOptional.get();

            if (departureDay.getStatus()) {
                departureDay.setStatus(false);

                // Hủy các booking liên quan
                List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDayId).stream()
                        .filter(Booking::getStatus).toList();
                if (!bookings.isEmpty()) {
                    int totalQuantity = 0;
                    for (Booking booking : bookings) {
                        booking.setStatus(false);
                        bookingRepository.save(booking);
                        totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
                    }
                    // Cập nhật số lượng cho ngày khởi hành
                    departureDay.setQuantity(departureDay.getQuantity() + totalQuantity);
                }
            } else if (departureDay.getTour().getStatus()) {
                // Chỉ kích hoạt các ngày khởi hành chưa diễn ra
                if (departureDay.getDepartureDay().isAfter(LocalDate.now())) {
                    departureDay.setStatus(true);
                } else
                    throw new DepartureDayCannotEnableException(departureDay.getDepartureDay());
            } else
                throw new TourNotEnableException(departureDay.getTour().getId());
            departureDayRepository.save(departureDay);
            return departureDay;
        } else
            throw new DepartureDayNotFoundException(departureDayId);
    }
}
