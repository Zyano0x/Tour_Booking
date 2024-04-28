package com.project.tour_booking.Service.Booking;

import com.project.tour_booking.DTO.BookingDTO;
import com.project.tour_booking.Entity.*;
import com.project.tour_booking.Exception.*;
import com.project.tour_booking.Repository.*;
import com.project.tour_booking.Service.Email.EmailService;
import com.project.tour_booking.Service.SecureToken.SecureTokenService;
import com.project.tour_booking.Service.User.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private BookingRepository bookingRepository;
    private TourRepository tourRepository;
    private DepartureDayRepository departureDayRepository;
    private UserRepository userRepository;
    private SecureTokenRepository secureTokenRepository;
    private SecureTokenService secureTokenService;
    private EmailService emailService;

    public UserServiceImpl.VerificationResult validateToken(String confirmationToken) {
        SecureToken token = secureTokenRepository.findByToken(confirmationToken);

        if (token == null) {
            return UserServiceImpl.VerificationResult.INVALID_TOKEN;
        }

        Calendar calendar = Calendar.getInstance();

        if ((token.getExpireTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return UserServiceImpl.VerificationResult.EXPIRED;
        }

        return UserServiceImpl.VerificationResult.VALID;
    }

    @Override
    public Booking saveBooking(BookingDTO bookingDTO) {
        // Kiểm tra tồn tại
        Optional<User> userOptional = userRepository.findById(bookingDTO.getUserId());
        if (userOptional.isPresent()) {
            Booking booking = new Booking();
            booking.setUser(userOptional.get());

            // Kiểm tra tồn tại
            Optional<DepartureDay> departureDayOptional = departureDayRepository
                    .findById(bookingDTO.getDepartureDayId());
            if (departureDayOptional.isPresent()) {
                DepartureDay departureDay = departureDayOptional.get();
                // Kiểm tra ngày khởi hành đã diễn ra chưa
                if (departureDay.getDepartureDay().isAfter(LocalDate.now())) {
                    if (departureDay.getStatus()) {
                        booking.setDepartureDay(departureDay);
                        if (bookingDTO.getQuantityOfAdult() + bookingDTO.getQuantityOfChild() > 0) {
                            if ((bookingDTO.getQuantityOfAdult() + bookingDTO.getQuantityOfChild()) <= departureDay
                                    .getQuantity()) {
                                booking.setQuantityOfAdult(bookingDTO.getQuantityOfAdult());
                                booking.setQuantityOfChild(bookingDTO.getQuantityOfChild());
                                booking.setTransactionCode(bookingDTO.getTransactionCode());

                                // Cập nhật số lượng của ngày khởi hành
                                departureDay.setQuantity(departureDay.getQuantity()
                                        - (booking.getQuantityOfAdult() + booking.getQuantityOfChild()));

                                // Kiểm tra tồn tại
                                Optional<Tour> tourOptional = tourRepository.findById(departureDay.getTour().getId());
                                if (tourOptional.isPresent()) {
                                    Tour tour = tourOptional.get();

                                    // Set tổng tiền cho booking
                                    booking.setTotalPrice((new BigDecimal(booking.getQuantityOfAdult())
                                            .multiply(tour.getPriceForAdult())
                                            .add(new BigDecimal(booking.getQuantityOfChild())
                                                    .multiply(tour.getPriceForChildren()))));

                                    // Set các trường khác
                                    booking.setBookingDate(LocalDate.now());
                                    booking.setStatus(true);

                                    // Lưu vào csdl
                                    departureDayRepository.save(departureDay);
                                    bookingRepository.save(booking);

                                    // Gửi mail
                                    User user = booking.getUser();

                                    emailService.sendEmailVerifyBooking(user, booking);
                                    return booking;
                                } else
                                    throw new TourNotFoundException(departureDay.getTour().getId());
                            } else
                                throw new BookingNotEnoughQuantityException(departureDay.getDepartureDay());
                        } else
                            throw new BookingInvalidQuantityException();
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
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getAllBookingByUserId(Long userId) {
        return bookingRepository.findAllByUserId(userId);
    }

    @Override
    public List<Booking> getBookingIsValidOfUserId(Long userId) {
        return bookingRepository.findAllByUserId(userId).stream().filter(Booking::getStatus)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getAllBookingByTourId(Long tourId) {
        List<Booking> bookings = bookingRepository.findAll();
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
    public Booking updateBooking(BookingDTO bookingDTO, Long bookingId) {
        // Kiểm tra tồn tại
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            // Kiểm tra status
            if (bookingOptional.get().getStatus()) {
                Booking presentBooking = bookingOptional.get();

                Long presentDepartureId = presentBooking.getDepartureDay().getId();

                // Kiểm tra có cập nhật ngày khởi hành không
                if (bookingDTO.getDepartureDayId() != presentDepartureId) {
                    Optional<DepartureDay> departureDayOptional = departureDayRepository
                            .findById(bookingDTO.getDepartureDayId());
                    // Kiểm tra tồn tại của departureDay mới
                    if (departureDayOptional.isPresent()) {
                        DepartureDay newDepartureDay = departureDayOptional.get();
                        // Kiểm tra ngày khởi hành đã diễn ra chưa
                        if (newDepartureDay.getDepartureDay().isAfter(LocalDate.now())) {
                            // Kiểm tra status của departureDay
                            if (newDepartureDay.getStatus()) {
                                Integer totalQuantityOfPresentBooking = presentBooking.getQuantityOfAdult()
                                        + presentBooking.getQuantityOfChild();

                                // Trả lại số lượng cho ngày khởi hàng cũ
                                DepartureDay presentDepartureDay = departureDayRepository.findById(presentDepartureId)
                                        .get();
                                presentDepartureDay
                                        .setQuantity(presentDepartureDay.getQuantity() + totalQuantityOfPresentBooking);

                                // Cập nhật số lượng cho ngày khởi hàng mới
                                newDepartureDay
                                        .setQuantity(newDepartureDay.getQuantity() - totalQuantityOfPresentBooking);

                                // Cập nhật departure mới cho booking
                                presentBooking.setDepartureDay(newDepartureDay);
                                presentBooking.setTotalPrice(
                                        (new BigDecimal(presentBooking.getQuantityOfAdult()).multiply(tourRepository
                                                .findById(newDepartureDay.getTour().getId()).get().getPriceForAdult()))
                                                .add(new BigDecimal(presentBooking.getQuantityOfChild()).multiply(
                                                        tourRepository.findById(newDepartureDay.getTour().getId()).get()
                                                                .getPriceForChildren())));

                                User user = presentBooking.getUser();

                                emailService.sendEmailUpdateBooking(user, presentBooking);
                            } else
                                throw new DepartureDayNotEnableStatusException(newDepartureDay.getDepartureDay());
                        } else
                            throw new DepartureDayCannotEnableException(newDepartureDay.getDepartureDay());
                    } else
                        throw new DepartureDayNotFoundException(bookingDTO.getDepartureDayId());
                }

                int updateQuantityOfAdult = bookingDTO.getQuantityOfAdult() - presentBooking.getQuantityOfAdult();
                int updateQuantityOfChild = bookingDTO.getQuantityOfChild() - presentBooking.getQuantityOfChild();
                int updateTotalQuantity = updateQuantityOfAdult + updateQuantityOfChild;

                DepartureDay updateDepartureDay = departureDayRepository.findById(bookingDTO.getDepartureDayId()).get();

                // Bắt đầu kiểm tra và cập nhật trường số lượng người lớn ở booking
                int departureQuantity = updateDepartureDay.getQuantity();
                if (updateTotalQuantity != 0) {
                    if (updateTotalQuantity <= departureQuantity) {

                        // Cập nhật trường số lượng người lớn của booking
                        presentBooking.setQuantityOfAdult(bookingDTO.getQuantityOfAdult());

                        // Cập nhật trường số lượng trẻ con của booking
                        presentBooking.setQuantityOfChild(bookingDTO.getQuantityOfChild());

                        // Cập nhật trường số lượng của departureDay
                        updateDepartureDay.setQuantity(departureQuantity - updateTotalQuantity);
                        departureDayRepository.save(updateDepartureDay);

                        // Cập nhật tổng tiền của booking
                        presentBooking.setTotalPrice(presentBooking.getTotalPrice()
                                .add((new BigDecimal(updateQuantityOfAdult).multiply(tourRepository
                                        .findById(updateDepartureDay.getTour().getId()).get().getPriceForAdult()))
                                        .add(new BigDecimal(updateQuantityOfChild)
                                                .multiply(tourRepository.findById(updateDepartureDay.getTour().getId())
                                                        .get().getPriceForChildren()))));
                    } else
                        throw new BookingNotEnoughQuantityException(updateDepartureDay.getDepartureDay());
                }
                return bookingRepository.save(presentBooking);
            } else
                throw new BookingStatusException();
        } else
            throw new BookingNotFoundException(bookingId);
    }

    @Override
    public void updateBookingStatus(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        User user = booking.getUser();

        SecureToken token = new SecureToken(user);
        secureTokenRepository.save(token);

        emailService.sendEmailUpdateBookingStatus(user, booking, token);
    }

    @Override
    public Booking confirmCancel(Long transactionCode, String confirmationToken) {
        Optional<Booking> booking = bookingRepository.findByTransactionCode(transactionCode);
        if (booking.isPresent()) {
            Booking updateStatusBooking = booking.get();

            // Lấy ngày khởi hành để cập nhật
            DepartureDay updateQuantityDepartureDay = departureDayRepository
                    .findById(updateStatusBooking.getDepartureDay().getId()).get();

            // Lấy số tổng số lượng khách của booking
            Integer total = updateStatusBooking.getQuantityOfAdult() + updateStatusBooking.getQuantityOfChild();

            // Kiểm tra và cập nhật trạng thái
            if (updateStatusBooking.getStatus()) {
                updateStatusBooking.setStatus(false);
                updateQuantityDepartureDay.setQuantity(updateQuantityDepartureDay.getQuantity() + total);
            } else {
                // Kiểm tra xem ngày khởi hành đã diễn ra chưa
                if (updateStatusBooking.getDepartureDay().getDepartureDay().isAfter(LocalDate.now())) {
                    // Kiểm tra trạng thái ngày khởi hành
                    if (updateStatusBooking.getDepartureDay().getStatus()) {
                        updateStatusBooking.setStatus(true);
                        updateQuantityDepartureDay.setQuantity(updateQuantityDepartureDay.getQuantity() - total);
                    } else
                        throw new DepartureDayNotEnableStatusException(
                                updateStatusBooking.getDepartureDay().getDepartureDay());
                } else
                    throw new DepartureDayCannotEnableException(
                            updateStatusBooking.getDepartureDay().getDepartureDay());
            }

            if (validateToken(confirmationToken) == UserServiceImpl.VerificationResult.INVALID_TOKEN) {
                throw new TokenException("Invalid verification token.");
            } else if (validateToken(confirmationToken) == UserServiceImpl.VerificationResult.EXPIRED) {
                throw new TokenException("Verification token already expired.");
            } else if (validateToken(confirmationToken) == UserServiceImpl.VerificationResult.VALID) {
                SecureToken token = secureTokenRepository.findByToken(confirmationToken);

                // Lưu vào csdl
                departureDayRepository.save(updateQuantityDepartureDay);
                bookingRepository.save(updateStatusBooking);
                secureTokenService.removeToken(token);

                emailService.sendEmailConfirmBookingCancel(updateStatusBooking, transactionCode);

                return updateStatusBooking;
            }
        }
        throw new BookingNotFoundException(transactionCode);
    }
}