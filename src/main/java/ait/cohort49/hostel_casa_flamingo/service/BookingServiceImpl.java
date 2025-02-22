package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.ImageInfoDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.CartItemBed;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.BookingRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BookingService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BookingMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CartService cartService;
    private final BookingMappingService bookingMappingService;
    private final BookingEmailService bookingEmailService;
    private final BedServiceImpl bedServiceImpl;
    private final ImageServiceImpl imageService;

    public BookingServiceImpl(BookingRepository bookingRepository,
                              CartService cartService,
                              BookingMappingService bookingMappingService,
                              BookingEmailService bookingEmailService,
                              BedServiceImpl bedServiceImpl, ImageServiceImpl imageService) {
        this.bookingRepository = bookingRepository;
        this.cartService = cartService;
        this.bookingMappingService = bookingMappingService;
        this.bookingEmailService = bookingEmailService;
        this.bedServiceImpl = bedServiceImpl;
        this.imageService = imageService;
    }

    @Override
    @Transactional
    public List<BookingDto> createBookingFromCart(User authUser) {

        Cart cart = cartService.getCartEntity(authUser);

        List<CartItemBed> cartItemBeds = cart.getCartItemBeds();
        if (cartItemBeds == null || cartItemBeds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Booking> bookingList = new ArrayList<>();
        for (CartItemBed cartItemBed : cartItemBeds) {
            Long bedId = cartItemBed.getBed().getId();
            LocalDate entryDate = cartItemBed.getEntryDate();
            LocalDate departureDate = cartItemBed.getDepartureDate();

            if (bookingRepository.isBedBooked(bedId, entryDate, departureDate)) {
                throw new RestException(HttpStatus.NOT_FOUND, "This bed for the dates from " + entryDate + " to " + departureDate + " is already booked.");
            }

            Booking booking = new Booking(
                    entryDate,
                    departureDate,
                    cartService.getTotalPrice(authUser),
                    authUser,
                    cartItemBed.getBed()
            );
            bookingList.add(booking);
        }

        bookingRepository.saveAll(bookingList);
        authUser.setCart(null);
        cartService.delete(cart);

        List<BookingDto> mappedBookingDtos = bookingList
                .stream()
                .map(bookingMappingService::mapEntityToDto)
                .toList();
        bookingEmailService.sendBookingConfirmationEmail(mappedBookingDtos, authUser);
        return mappedBookingDtos;
    }

    @Override
    public List<BookingDto> getBooking(User authUser) {
        return bookingRepository.findAllByUser(authUser)
                .stream()
                .map(booking -> {
                    BookingDto bookingDto = bookingMappingService.mapEntityToDto(booking);

                    if (booking.getBed() != null) {
                        List<ImageInfoDto> images = imageService.getImagesByBed(booking.getBed().getId());
                        List<String> imageUrls = images.stream()
                                .map(ImageInfoDto::getImageUrl)
                                .collect(Collectors.toList());

                        bookingDto.getBed().setImageUrls(imageUrls);
                    }
                    return bookingDto;
                })
                .toList();
    }

    @Override
    public boolean hasActiveBookings(Long bedId) {
        return bookingRepository.existsByIdAndDepartureDateAfter(bedId, LocalDate.now());
    }

    @Override
    @Transactional
    public List<Booking> getPastBookings(Long bedId) {
        return bookingRepository.findBedByIdAndDepartureDateBefore(bedId, LocalDate.now());
    }

    @Override
    @Transactional
    public void deletePastBookings(Long bedId) {
        List<Booking> pastBookings = getPastBookings(bedId);
        bookingRepository.deleteAll(pastBookings);
    }

    @Override
    @Transactional
    public void deleteBedWithoutFutureBookings(Long bedId) {
        if (!hasActiveBookings(bedId)) {
            deletePastBookings(bedId);
            bedServiceImpl.deleteBedById(bedId);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, "The bed is booked for today or in the future");
        }
    }
}

