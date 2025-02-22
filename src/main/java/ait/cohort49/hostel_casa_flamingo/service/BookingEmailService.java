package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.EmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingEmailService {

    private final EmailService emailService;

    public BookingEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    @Async
    public void sendBookingConfirmationEmail(List<BookingDto> bookings, User user) {
        emailService.sendBookingConfirmEmail(bookings, user);
    }
}
