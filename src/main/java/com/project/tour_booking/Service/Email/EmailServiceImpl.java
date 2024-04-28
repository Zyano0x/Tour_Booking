package com.project.tour_booking.Service.Email;

import com.project.tour_booking.Entity.Booking;
import com.project.tour_booking.Entity.SecureToken;
import com.project.tour_booking.Entity.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public BigDecimal refundMoney(BigDecimal refund, LocalDate departureDay) {
        long daysDifference = ChronoUnit.DAYS.between(LocalDate.now(), departureDay);

        float refundPercent;
        if (daysDifference >= 7) {
            refundPercent = 0.8f;
        } else {
            refundPercent = 0.7f;
        }
        return refund.multiply(new BigDecimal(refundPercent)).setScale(0, RoundingMode.HALF_UP);
    }

    @Async
    @Override
    public void sendEmailVerify(User user, SecureToken token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here: "
                + "http://localhost:1337/api/v1/auth/confirm-account?token=" + token.getToken());

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendEmailForgotPassword(User user, SecureToken token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset Password");
        mailMessage.setText("To reset password your account, please click here: "
                + "http://localhost:1337/api/v1/auth/reset-password?token=" + token.getToken());

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendEmailVerifyBooking(User user, Booking booking) {
        // Định dạng ngày theo định dạng "dd-MM-yyyy"
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Định dạng "HH:mm"
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("Thông Báo Đặt Tour Thành Công");
        mailMessage.setText("Ngày đặt: " + booking.getBookingDate().format(dateFormatter)
                + "\nTour: "
                + booking.getDepartureDay().getTour().getName() + "\nThời gian: "
                + booking.getDepartureDay().getTour().getTime() + "\nKhởi hành vào ngày ["
                + booking.getDepartureDay().getDepartureDay().format(dateFormatter)
                + "] lúc ["
                + booking.getDepartureDay().getDepartureTime().format(timeFormatter)
                + "] tại [" + booking.getDepartureDay().getTour().getDeparturePoint()
                + "]\nSố lượng người lớn: "
                + booking.getQuantityOfAdult() + "\nSố lượng trẻ em: "
                + booking.getQuantityOfChild() + "\nTổng tiền: "
                + booking.getTotalPrice() + " VND");

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendEmailUpdateBooking(User user, Booking booking) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Định dạng "HH:mm"
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("Cập Nhật Đơn Hàng");
        mailMessage.setText("Ngày đặt: " + booking.getBookingDate().format(dateFormatter)
                + "\nTour: "
                + booking.getDepartureDay().getTour().getName() + "\nThời gian: "
                + booking.getDepartureDay().getTour().getTime()
                + "\nKhởi hành vào ngày ["
                + booking.getDepartureDay().getDepartureDay().format(dateFormatter)
                + "] lúc ["
                + booking.getDepartureDay().getDepartureTime().format(timeFormatter)
                + "] tại [" + booking.getDepartureDay().getTour().getDeparturePoint()
                + "]\nSố lượng người lớn: "
                + booking.getQuantityOfAdult() + "\nSố lượng trẻ em: "
                + booking.getQuantityOfChild() + "\nTổng tiền: "
                + booking.getTotalPrice() + " VND");

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendEmailUpdateBookingStatus(User user, Booking booking, SecureToken token) {
        BigDecimal refund = refundMoney(booking.getTotalPrice(), booking.getDepartureDay().getDepartureDay());
        Long transactionCode = booking.getTransactionCode();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        // Định dạng "HH:mm"
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo(user.getUsername());
        mailMessage.setSubject("Xác Nhận Hủy Đơn Hàng");
        mailMessage.setText("Ngày đặt: " + booking.getBookingDate().format(dateFormatter) + "\nTour: "
                + booking.getDepartureDay().getTour().getName() + "\nThời gian: "
                + booking.getDepartureDay().getTour().getTime() + "\nKhởi hành vào ngày ["
                + booking.getDepartureDay().getDepartureDay().format(dateFormatter) + "] lúc ["
                + booking.getDepartureDay().getDepartureTime().format(timeFormatter)
                + "] tại [" + booking.getDepartureDay().getTour().getDeparturePoint() + "]\nSố lượng người lớn: "
                + booking.getQuantityOfAdult() + "\nSố lượng trẻ em: " + booking.getQuantityOfChild() + "\nTổng tiền: "
                + booking.getTotalPrice() + " VND\nHoàn trả: " + refund
                + ".00 VND\nĐể xác nhận hủy đơn, vui lòng nhấn vào link: "
                + "http://localhost:1337/api/v1/confirm-cancel?token="
                + token.getToken() + "&transaction=" + transactionCode);

        javaMailSender.send(mailMessage);
    }

    @Override
    public void sendEmailConfirmBookingCancel(Booking booking, Long code) {
        BigDecimal refund = refundMoney(booking.getTotalPrice(),
                booking.getDepartureDay().getDepartureDay());

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("no-reply@tourbooking.com");
        mailMessage.setTo("admin@tourbooking.com");
        mailMessage.setSubject("Xác Nhận Hủy Đơn Hàng");
        mailMessage.setText("Số hóa đơn xác nhận hủy: " + code
                + "\nSố tiền cần hoàn lại: " + refund + ".00 VND");

        javaMailSender.send(mailMessage);
    }
}
