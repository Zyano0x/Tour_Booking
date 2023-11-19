package com.project.tour_booking.Service.TypeOfTour;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.TypeOfTourDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.TypeOfTour;
import com.project.tour_booking.Exception.TypeOfTourNotFoundException;
import com.project.tour_booking.Repository.BookingRepository;
import com.project.tour_booking.Repository.DepartureDayRepository;
import com.project.tour_booking.Repository.TourRepository;
import com.project.tour_booking.Repository.TypeOfTourRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TypeOfTourServiceImpl implements TypeOfTourService {
  private TypeOfTourRepository typeOfTourRepository;
  private TourRepository tourRepository;
  private DepartureDayRepository departureDayRepository;
  private BookingRepository bookingRepository;

  @Override
  public void saveTOT(TypeOfTourDTO typeOfTourDTO) {
    TypeOfTour newTypeOfTour = new TypeOfTour();
    newTypeOfTour.setName(typeOfTourDTO.getName());
    newTypeOfTour.setDescription(typeOfTourDTO.getDescription());
    newTypeOfTour.setStatus(true);
    typeOfTourRepository.save(newTypeOfTour);
  }

  @Override
  public TypeOfTour getTOT(Long totId) {
    Optional<TypeOfTour> tot = typeOfTourRepository.findById(totId);
    if (tot.isPresent())
      return tot.get();
    else
      throw new TypeOfTourNotFoundException(totId);
  }

  @Override
  public List<TypeOfTour> getTOTS() {
    return (List<TypeOfTour>) typeOfTourRepository.findAll();
  }

  @Override
  public TypeOfTour updateStatusTOT(Long totId) {
    Optional<TypeOfTour> typeOfTourOptional = typeOfTourRepository.findById(totId);
    if (typeOfTourOptional.isPresent()) {
      TypeOfTour updateTOT = typeOfTourOptional.get();
      if (updateTOT.getStatus()) {
        updateTOT.setStatus(false);

        // Tìm và disable các tour thuộc typeOfTour
        List<Tour> tours = tourRepository.findByTypeOfTourId(totId);
        for (Tour tour : tours) {
          if (tour.getStatus() == true) {
            tour.setStatus(false);

            // Hủy các departureDay của tour
            List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId());
            for (DepartureDay departureDay : departureDays) {
              // Chỉ hủy các departureDay còn hiệu lực
              if (departureDay.getDepartureDay().isAfter(LocalDate.now()) &&
                  departureDay.getStatus() == true) {

                departureDay.setStatus(false);

                // Hủy các booking liên quan và cập nhật trường số lượng của departureDay
                List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId());
                Integer totalQuantity = 0;
                for (Booking booking : bookings) {
                  if (booking.getStatus() == true) {
                    booking.setStatus(false);
                    totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
                  }
                }
                departureDay.setQuantity(departureDay.getQuantity() + totalQuantity);
                departureDayRepository.save(departureDay);
              }
            }

            tourRepository.save(tour);
          }
        }
      } else {
        updateTOT.setStatus(true);

        // Tìm và disable các tour thuộc typeOfTour
        List<Tour> tours = tourRepository.findByTypeOfTourId(totId);
        for (Tour tour : tours) {
          tour.setStatus(true);

          List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId());
          for (DepartureDay departureDay : departureDays) {
            if (departureDay.getDepartureDay().isAfter(LocalDate.now()) &&
                departureDay.getStatus() == false) {
              departureDay.setStatus(true);
              departureDayRepository.save(departureDay);
            }
          }

          tourRepository.save(tour);
        }
      }

      return typeOfTourRepository.save(updateTOT);
    } else
      throw new TypeOfTourNotFoundException(totId);
  }

  @Override
  public TypeOfTour updateTOT(TypeOfTour typeOfTour, Long totId) {
    Optional<TypeOfTour> typeOfTourOptional = typeOfTourRepository.findById(totId);
    if (typeOfTourOptional.isPresent()) {
      TypeOfTour updateTOT = typeOfTourOptional.get();

      updateTOT.setName(typeOfTour.getName());
      updateTOT.setDescription(typeOfTour.getDescription());

      if (updateTOT.getStatus() != typeOfTour.getStatus()) {
        updateTOT.setStatus(typeOfTour.getStatus());

        if (updateTOT.getStatus() == false) {
          // Tìm và disable các tour thuộc typeOfTour
          List<Tour> tours = tourRepository.findByTypeOfTourId(totId);
          for (Tour tour : tours) {
            if (tour.getStatus() == true) {
              tour.setStatus(false);

              // Hủy các departureDay của tour
              List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId());
              for (DepartureDay departureDay : departureDays) {
                // Chỉ hủy các departureDay còn hiệu lực
                if (departureDay.getDepartureDay().isAfter(LocalDate.now()) &&
                    departureDay.getStatus() == true) {

                  departureDay.setStatus(false);

                  // Hủy các booking liên quan và cập nhật trường số lượng của departureDay
                  List<Booking> bookings = bookingRepository.findAllByDepartureDayId(departureDay.getId());
                  Integer totalQuantity = 0;
                  for (Booking booking : bookings) {
                    if (booking.getStatus() == true) {
                      booking.setStatus(false);
                      totalQuantity += booking.getQuantityOfAdult() + booking.getQuantityOfChild();
                    }
                  }
                  departureDay.setQuantity(departureDay.getQuantity() + totalQuantity);
                  departureDayRepository.save(departureDay);
                }
              }

              tourRepository.save(tour);
            }
          }
        } else {
          // Tìm và disable các tour thuộc typeOfTour
          List<Tour> tours = tourRepository.findByTypeOfTourId(totId);
          for (Tour tour : tours) {
            tour.setStatus(true);

            List<DepartureDay> departureDays = departureDayRepository.findAllByTourId(tour.getId());
            for (DepartureDay departureDay : departureDays) {
              if (departureDay.getDepartureDay().isAfter(LocalDate.now()) &&
                  departureDay.getStatus() == false) {
                departureDay.setStatus(true);
                departureDayRepository.save(departureDay);
              }
            }
            tourRepository.save(tour);
          }
        }
      }

      return typeOfTourRepository.save(updateTOT);
    } else
      throw new TypeOfTourNotFoundException(totId);
  }
}
