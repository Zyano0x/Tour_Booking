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
import java.time.LocalDate;
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
    public void saveBooking(BookingDTO bookingDTO) {

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
        return bookingRepository.findAllByUserId(userId).stream().filter(booking -> booking.getStatus())
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
                bookingRepository.save(presentBooking);
            } else
                throw new BookingStatusException();
        } else
            throw new BookingNotFoundException(bookingId);
    }

    @Override
    public ResponseEntity<?> updateBookingStatus(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        User user = booking.getUser();
        Long transactionCode = booking.getTransactionCode();

        SecureToken token = new SecureToken(user);
        secureTokenRepository.save(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("Xác Nhận Hủy Đơn Hàng");
        mailMessage.setText(
                "Để xác nhận hủy đơn, vui lòng nhấn vào link: " + "http://localhost:1337/api/confirm-cancel?token="
                        + token.getToken() + "&transaction=" + transactionCode);

        emailService.sendEmail(mailMessage);

        return new ResponseEntity<>("Đã gửi email xác nhận hủy đặt vé", HttpStatus.OK);
    }

    @Transactional
    @Override
    public ResponseEntity<?> confirmCancel(Long transactionCode, String confirmationToken) {
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
                return new ResponseEntity<>("Invalid verification token.", HttpStatus.BAD_REQUEST);
            } else if (validateToken(confirmationToken) == UserServiceImpl.VerificationResult.EXPIRED) {
                return new ResponseEntity<>("Verification token already expired.", HttpStatus.BAD_REQUEST);
            } else if (validateToken(confirmationToken) == UserServiceImpl.VerificationResult.VALID) {
                SecureToken token = secureTokenRepository.findByToken(confirmationToken);
                BigDecimal refund = updateStatusBooking.getTotalPrice();

                // Lưu vào csdl
                departureDayRepository.save(updateQuantityDepartureDay);
                bookingRepository.save(updateStatusBooking);
                secureTokenService.removeToken(token);

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setFrom("no-reply@tourbooking.com");
                mailMessage.setTo("duypham22102@gmail.com");
                mailMessage.setSubject("Xác Nhận Hủy Đơn Hàng");
                mailMessage.setText("Số hóa đơn xác nhận hủy: " + transactionCode
                        + "\nSố tiền cần hoàn lại: " + refund + "VND");

                emailService.sendEmail(mailMessage);

                return new ResponseEntity<>("Đã xác nhận hủy", HttpStatus.OK);
            }
        } else
            throw new BookingNotFoundException(transactionCode);

        return new ResponseEntity<>("Hủy thất bại", HttpStatus.BAD_REQUEST);
    }
}