package com.project.tour_booking.Service.Booking;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.DepartureDay;
import com.project.tour_booking.Entity.Tour;
import com.project.tour_booking.Entity.User;
import com.project.tour_booking.Exception.BookingNotFoundException;
import com.project.tour_booking.Exception.BookingQuantityException;
import com.project.tour_booking.Exception.BookingStatusException;
import com.project.tour_booking.Exception.DepartureDayCannotEnableException;
import com.project.tour_booking.Exception.DepartureDayNotFoundException;
import com.project.tour_booking.Exception.DepartureDayNotEnableStatusException;
import com.project.tour_booking.Exception.TourNotFoundException;
import com.project.tour_booking.Exception.UserNotFoundException;
import com.project.tour_booking.Repository.BookingRepository;
import com.project.tour_booking.Repository.DepartureDayRepository;
import com.project.tour_booking.Repository.TourRepository;
import com.project.tour_booking.Repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
  private BookingRepository bookingRepository;
  private TourRepository tourRepository;
  private DepartureDayRepository departureDayRepository;
  private UserRepository userRepository;

  @Override
  public void saveBooking(BookingDTO bookingDTO) {

    // Kiểm tra tồn tại
    Optional<User> userOptional = userRepository.findById(bookingDTO.getUserId());
    if (userOptional.isPresent()) {
      Booking booking = new Booking();
      booking.setUser(userOptional.get());

      // Kiểm tra tồn tại
      Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(bookingDTO.getDepartureDayId());
      if (departureDayOptional.isPresent()) {
        DepartureDay departureDay = departureDayOptional.get();
        // Kiểm tra status của departureDay
        if (departureDay.getDepartureDay().isAfter(LocalDate.now())) {
          if (departureDay.getStatus() == true) {
            booking.setDepartureDay(departureDay);

            if ((bookingDTO.getQuantityOfAdult() + bookingDTO.getQuantityOfChild()) <= departureDay.getQuantity()) {
              booking.setQuantityOfAdult(bookingDTO.getQuantityOfAdult());
              booking.setQuantityOfChild(bookingDTO.getQuantityOfChild());

              // Cập nhật số lượng của departureDay
              departureDay.setQuantity(departureDay.getQuantity() - (booking.getQuantityOfAdult() +
                  booking.getQuantityOfChild()));

              // Kiểm tra tồn tại
              Optional<Tour> tourOptional = tourRepository.findById(departureDay.getTour().getId());
              if (tourOptional.isPresent()) {
                Tour tour = tourOptional.get();

                // Set tổng tiền cho booking
                booking.setTotalPrice((new BigDecimal(booking.getQuantityOfAdult()).multiply(tour.getPriceForAdult())
                    .add(new BigDecimal(booking.getQuantityOfChild()).multiply(tour.getPriceForChildren()))));

                // Set các trường khác
                booking.setBookingDate(LocalDate.now());
                booking.setStatus(true);

                // Lưu vào csdl
                departureDayRepository.save(departureDay);
                bookingRepository.save(booking);
              } else
                throw new TourNotFoundException(departureDay.getTour().getId());
            } else
              throw new BookingQuantityException();
          } else
            throw new DepartureDayNotEnableStatusException(departureDay.getDepartureDay());
        } else
          throw new DepartureDayCannotEnableException(departureDay.getDepartureDay());
      } else
        throw new DepartureDayNotFoundException(bookingDTO.getDepartureDayId());
    } else
      throw new UserNotFoundException(bookingDTO.getUserId());

  }

  @Override
  public Booking getBooking(Long bookingId) {
    Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
    if (bookingOptional.isPresent())
      return bookingOptional.get();
    else
      throw new BookingNotFoundException(bookingId);
  }

  @Override
  public List<Booking> getBookings() {
    return (List<Booking>) bookingRepository.findAll();
  }

  @Override
  public List<Booking> getAllBookingByUserId(Long userId) {
    return bookingRepository.findAllByUserId(userId);
  }

  @Override
  public List<Booking> getBookingIsValidOfUserId(Long userId) {
    return bookingRepository.findAllByUserId(userId).stream()
        .filter(booking -> booking.getStatus())
        .collect(Collectors.toList());
  }

  @Override
  public List<Booking> getAllBookingByTourId(Long tourId) {
    List<Booking> bookings = (List<Booking>) bookingRepository.findAll();
    return bookings.stream().filter(booking -> booking.getDepartureDay().getTour().getId().equals(tourId))
        .collect(Collectors.toList());
  }

  @Override
  public List<Booking> getBookingByUserIdAndTourId(Long userId, Long tourId) {
    List<Booking> bookings = bookingRepository.findAllByUserId(userId);
    return bookings.stream().filter(booking -> booking.getDepartureDay().getTour().getId().equals(tourId))
        .collect(Collectors.toList());
  }

  @Override
  public void updateBooking(BookingDTO bookingDTO, Long bookingId) {
    // Kiểm tra tồn tại
    Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
    if (bookingOptional.isPresent()) {
      // Kiểm tra status
      if (bookingOptional.get().getStatus()) {
        Booking presentBooking = bookingOptional.get();

        Long presentDepartureId = presentBooking.getDepartureDay().getId();

        // Kiểm tra có cập nhật ngày khởi hành không
        if (bookingDTO.getDepartureDayId() != presentDepartureId) {
          Optional<DepartureDay> departureDayOptional = departureDayRepository.findById(bookingDTO.getDepartureDayId());
          // Kiểm tra tồn tại của departureDay mới
          if (departureDayOptional.isPresent()) {
            DepartureDay newDepartureDay = departureDayOptional.get();
            if (newDepartureDay.getDepartureDay().isAfter(LocalDate.now())) {
              // Kiểm tra status của departureDay
              if (newDepartureDay.getStatus() == true) {
                Integer totalQuantityOfPresentBooking = presentBooking.getQuantityOfAdult()
                    + presentBooking.getQuantityOfChild();
                // Trả lại số lượng cho ngày khởi hàng cũ
                DepartureDay presentDepartureDay = departureDayRepository.findById(presentDepartureId).get();
                presentDepartureDay.setQuantity(presentDepartureDay.getQuantity() + totalQuantityOfPresentBooking);

                // Cập nhật số lượng cho ngày khởi hàng mới
                newDepartureDay.setQuantity(newDepartureDay.getQuantity() - totalQuantityOfPresentBooking);

                // Cập nhật departure mới cho booking
                presentBooking.setDepartureDay(newDepartureDay);
              } else
                throw new DepartureDayNotEnableStatusException(newDepartureDay.getDepartureDay());
            } else
              throw new DepartureDayCannotEnableException(newDepartureDay.getDepartureDay());
          } else
            throw new DepartureDayNotFoundException(bookingDTO.getDepartureDayId());
        }

        Integer updateQuantityOfAdult = bookingDTO.getQuantityOfAdult() - presentBooking.getQuantityOfAdult();
        Integer updateQuantityOfChild = bookingDTO.getQuantityOfChild() - presentBooking.getQuantityOfChild();
        Integer updateTotalQuantity = updateQuantityOfAdult + updateQuantityOfChild;

        DepartureDay updateDepartureDay = departureDayRepository.findById(bookingDTO.getDepartureDayId()).get();

        // Bắt đầu kiểm tra và cập nhật trường số lượng người lớn ở booking
        Integer departureQuantity = updateDepartureDay.getQuantity();
        if (updateTotalQuantity <= departureQuantity) {

          // Cập nhật trường số lượng người lớn của booking
          presentBooking.setQuantityOfAdult(bookingDTO.getQuantityOfAdult());

          // Cập nhật trường số lượng trẻ con của booking
          presentBooking.setQuantityOfChild(bookingDTO.getQuantityOfChild());

          // Cập nhật trường số lượng của departureDay
          updateDepartureDay.setQuantity(departureQuantity - updateTotalQuantity);
          departureDayRepository.save(updateDepartureDay);

          // Cập nhật tổng tiền của booking
          presentBooking.setTotalPrice(
              presentBooking.getTotalPrice()
                  .add((new BigDecimal(updateQuantityOfAdult)
                      .multiply(tourRepository.findById(updateDepartureDay.getTour().getId()).get()
                          .getPriceForAdult()))
                      .add(new BigDecimal(updateQuantityOfChild)
                          .multiply(tourRepository.findById(updateDepartureDay.getTour().getId()).get()
                              .getPriceForChildren()))));

          bookingRepository.save(presentBooking);
        } else
          throw new BookingQuantityException();
      } else
        throw new BookingStatusException();
    } else
      throw new BookingNotFoundException(bookingId);
  }

  @Transactional
  @Override
  public void updateStatusBooking(Long bookingId) {
    Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
    if (bookingOptional.isPresent()) {

      Booking updateStatusBooking = bookingOptional.get();
      // Lấy departureDay để cập nhật
      DepartureDay updateQuantityDepartureDay = departureDayRepository
          .findById(updateStatusBooking.getDepartureDay().getId()).get();

      // Lấy số tổng số lượng khách của booking
      Integer total = updateStatusBooking.getQuantityOfAdult() +
          updateStatusBooking.getQuantityOfChild();

      // Kiểm tra và cập nhật trạng thái
      if (updateStatusBooking.getStatus()) {
        updateStatusBooking.setStatus(false);
        updateQuantityDepartureDay.setQuantity(updateQuantityDepartureDay.getQuantity() + total);
      } else {
        // Kiểm tra xem departure có còn hiệu lực
        if (updateStatusBooking.getDepartureDay().getDepartureDay().isAfter(LocalDate.now())) {
          if (updateStatusBooking.getDepartureDay().getStatus() == true) {
            updateStatusBooking.setStatus(true);
            updateQuantityDepartureDay.setQuantity(updateQuantityDepartureDay.getQuantity() - total);
          } else
            throw new DepartureDayNotEnableStatusException(updateStatusBooking.getDepartureDay().getDepartureDay());
        } else
          throw new DepartureDayCannotEnableException(updateStatusBooking.getDepartureDay().getDepartureDay());
      }

      // Lưu vào csdl
      departureDayRepository.save(updateQuantityDepartureDay);
      bookingRepository.save(updateStatusBooking);
    } else
      throw new BookingNotFoundException(bookingId);
  }
}