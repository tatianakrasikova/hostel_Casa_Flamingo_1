package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.util.List;

public interface EmailService {

    void sendConfirmationEmail(User user, String code);

    void sendBookingConfirmEmail(List<BookingDto> bookings, User user);
}
