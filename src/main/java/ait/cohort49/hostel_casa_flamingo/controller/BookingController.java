package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BookingService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final UserService userService;

    public BookingController(BookingService bookingService, UserService userService) {
        this.bookingService = bookingService;
        this.userService = userService;
    }

    @PostMapping("/confirm")
    @PreAuthorize("isAuthenticated()")
    public List<BookingDto> createBooking(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return bookingService.createBookingFromCart(user);
    }

    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public List<BookingDto> getBooking(@AuthenticationPrincipal String userEmail) {
        User user = userService.getUserByEmailOrThrow(userEmail);
        return bookingService.getBooking(user);
    }
}
