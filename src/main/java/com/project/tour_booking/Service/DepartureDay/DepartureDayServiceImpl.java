package com.project.tour_booking.Service.DepartureDay;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.DepartureDayDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Exception.DepartureDayCannotEnableException;
import com.project.tour_booking.Exception.DepartureDayNotFoundException;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Repository.BookingRepository;
import com.project.tour_booking.Repository.DepartureDayRepository;
import com.project.tour_booking.Repository.TourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DepartureDayServiceImpl implements DepartureDayService {
  private DepartureDayRepository departureDayRepository;
  private TourRepository tourRepository;
  private BookingRepository bookingRepository;

  @Override
  public void saveDepartureDay(DepartureDayDTO departureDayDTO) {
    DepartureDay newDepartureDay = new DepartureDay(departureDayDTO.getQuantity(), departureDayDTO.getDepartureDay(),
        true);

    Optional<Tour> tourOptional = tourRepository.findById(departureDayDTO.getTourId());
    if (tourOptional.isPresent())
      newDepartureDay.setTour(tourOptional.get());
    else
      throw new TourNotFoundException(departureDayDTO.getTourId());
    departureDayRepository.save(newDepartureDay);
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
  public List<DepartureDay> getDepartureDaysByTourId(Long tourId) {
    return departureDayRepository.findAllByTourId(tourId);
  }

  @Override
  public DepartureDay updateDepartureDay(DepartureDayDTO departureDayDTO, Long departureDayId) {
    // Kiểm tra tồn tại
    Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
    if (departureDayOptional.isPresent()) {
      DepartureDay updateDepartureDay = departureDayOptional.get();

      // Kiểm tra departureDay có còn hiệu lực
      if (updateDepartureDay.getDepartureDay().isAfter(LocalDate.now())) {
        updateDepartureDay.setQuantity(departureDayDTO.getQuantity());
        updateDepartureDay.setDepartureDay(departureDayDTO.getDepartureDay());

        // Kiểm tra có thay đổi status không
        if (departureDayDTO.getStatus() != updateDepartureDay.getStatus()) {
          // Set status cho departure
          updateDepartureDay.setStatus(departureDayDTO.getStatus());

          // Hủy status của booking và trường số lượng của departureDay
          if (departureDayDTO.getStatus() == false) {
            List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDayId);
            Integer totalQuantity = 0;
            for (Booking booking : bookings) {
              booking.setStatus(false);
              totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
              bookingRepository.save(booking);
            }
            updateDepartureDay.setQuantity(updateDepartureDay.getQuantity() + totalQuantity);
          }
        }

        Optional<Tour> tourOptional = tourRepository.findById(departureDayDTO.getTourId());
        if (tourOptional.isPresent())
          updateDepartureDay.setTour(tourOptional.get());
        else
          throw new TourNotFoundException(departureDayDTO.getTourId());
        return departureDayRepository.save(updateDepartureDay);
      } else
        throw new DepartureDayCannotEnableException(updateDepartureDay.getDepartureDay());
    } else
      throw new DepartureDayNotFoundException(departureDayId);
  }

  @Override
  public DepartureDay updateStatusDepartureDay(Long departureDayId) {
    // Kiểm tra tồn tại
    Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(departureDayId);
    if (departureDayOptional.isPresent()) {
      DepartureDay departureDay = departureDayOptional.get();

      if (departureDay.getStatus()) {
        departureDay.setStatus(false);

        // Hủy các booking liên quan và cập nhật số lượng cho departureDay
        List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDayId);
        Integer totalQuantity = 0;
        for (Booking booking : bookings) {
          booking.setStatus(false);
          totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
          bookingRepository.save(booking);
        }
        departureDay.setQuantity(departureDay.getQuantity() + totalQuantity);
      } else {
        if (departureDay.getDepartureDay().isAfter(LocalDate.now())) {
          departureDay.setStatus(true);
        } else
          throw new DepartureDayCannotEnableException(departureDay.getDepartureDay());
      }
      return departureDayRepository.save(departureDay);
    } else
      throw new DepartureDayNotFoundException(departureDayId);
  }
}
