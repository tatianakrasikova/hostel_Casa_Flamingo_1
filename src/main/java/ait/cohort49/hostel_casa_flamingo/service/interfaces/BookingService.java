package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.util.List;

public interface BookingService {

    List<BookingDto> createBookingFromCart(User authUser);

    List<BookingDto> getBooking(User authUser);

    boolean hasActiveBookings(Long bedId);

    List<Booking> getPastBookings(Long bedId);

    void deletePastBookings(Long bedId);

    void deleteBedWithoutFutureBookings(Long bedId);
}
