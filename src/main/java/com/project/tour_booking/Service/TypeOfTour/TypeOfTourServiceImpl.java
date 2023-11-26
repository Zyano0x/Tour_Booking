package com.project.tour_booking.Service.TypeOfTour;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
  public TypeOfTour updateTOTStatus(Long totId) {
    Optional<TypeOfTour> typeOfTourOptional = typeOfTourRepository.findById(totId);
    if (typeOfTourOptional.isPresent()) {
      TypeOfTour updateTOT = typeOfTourOptional.get();
      if (updateTOT.getStatus()) {
        updateTOT.setStatus(false);

        // Tìm và disable các tour thuộc loại tour
        List<Tour> tours = tourRepository.findAllByTypeOfTourId(totId).stream()
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
        updateTOT.setStatus(true);
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

        if (!updateTOT.getStatus()) {
          // Tìm và disable các tour thuộc loại tour
          List<Tour> tours = tourRepository.findAllByTypeOfTourId(totId).stream()
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

      return typeOfTourRepository.save(updateTOT);
    } else
      throw new TypeOfTourNotFoundException(totId);
  }
}
