package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CartItemBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.CartItemBed;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.CartItemBedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.CartRepository;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.CartMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
public class CartServiceImpl implements CartService {

    private final BedService bedService;
    private final CartRepository cartRepository;
    private final CartMappingService cartMappingService;
    private final UserRepository userRepository;
    private final CartItemBedRepository cartItemBedRepository;


    public CartServiceImpl(BedService bedService, CartRepository cartRepository, CartMappingService cartMappingService, UserRepository userRepository, CartItemBedRepository cartItemBedRepository) {
        this.bedService = bedService;
        this.cartRepository = cartRepository;
        this.cartMappingService = cartMappingService;
        this.userRepository = userRepository;
        this.cartItemBedRepository = cartItemBedRepository;
    }

    @Override
    public CartDto getCart(User authUser) {
        Cart userCart = getCartEntity(authUser);
        CartDto cartDto = cartMappingService.mapEntityToDto(userCart);

        BigDecimal totalPriceBeds = BigDecimal.ZERO;

        for (CartItemBed cartItemBed : userCart.getCartItemBeds()) {
            BigDecimal totalCostForBed = calculateTotalCostForBed(cartItemBed);

            Bed cartItemBedBed = cartItemBed.getBed();
            BedDto bedDto = bedService.mapBedToDtoWithImages(cartItemBedBed);
            bedDto.setDescription(cartItemBedBed.getDescription());
            bedDto.setPrice(totalCostForBed);

            CartItemBedDto cartItemBedDto = new CartItemBedDto();
            cartItemBedDto.setId(cartItemBed.getId());
            cartItemBedDto.setEntryDate(cartItemBed.getEntryDate());
            cartItemBedDto.setDepartureDate(cartItemBed.getDepartureDate());
            cartItemBedDto.setBed(bedDto);

            cartDto.getBeds().add(cartItemBedDto);
            totalPriceBeds = totalPriceBeds.add(totalCostForBed);
        }

        long countBeds = userCart.getCartItemBeds().size();
        cartDto.setCountBeds(countBeds);
        cartDto.setTotalPriceBeds(totalPriceBeds);
        return cartDto;
    }

    private BigDecimal calculateTotalCostForBed(CartItemBed cartItemBed) {
        BigDecimal bedPrice = cartItemBed.getBed().getPrice();
        long daysBetween = ChronoUnit.DAYS.between(cartItemBed.getEntryDate(), cartItemBed.getDepartureDate());
        if (daysBetween <= 0) {
            daysBetween = 1;
        }
        return bedPrice.multiply(BigDecimal.valueOf(daysBetween));
    }

    @Override
    public Cart getCartEntity(User authUser) {
        return cartRepository.findByUser(authUser)
                .orElseGet(() -> {
                            Cart userCart = new Cart();
                            userCart.setUser(authUser);
                            cartRepository.save(userCart);
                            authUser.setCart(userCart);
                            userRepository.save(authUser);
                            return userCart;
                        }
                );
    }

    @Override
    public void addBedToCart(User authUser, Long bedId, LocalDate entryDate, LocalDate departureDate) {

        Bed foundBed = bedService.getBedOrThrow(bedId);
        Cart userCart = getCartEntity(authUser);

        /**
         * Проверка на пересечение дат с уже забронированными периодами
         */
        boolean isOverlapping = userCart.getCartItemBeds()
                .stream()
                .anyMatch(cartItemBed ->
                        cartItemBed.getBed().equals(foundBed) &&
                                !(cartItemBed.getDepartureDate().isBefore(entryDate) || cartItemBed.getEntryDate().isAfter(departureDate))
                );
        if (isOverlapping) {
            throw new RestException("This bed is already booked for the selected dates from " + entryDate + " to " + departureDate);
        }

        /**
         * Проверка на пересечение с другими бронированиями в базе (не только в корзине текущего пользователя)
         */
        boolean isBooked = cartItemBedRepository.findAll().stream()
                .anyMatch(cartItemBed ->
                        cartItemBed.getBed().equals(foundBed) &&
                                !(cartItemBed.getDepartureDate().isBefore(entryDate) || cartItemBed.getEntryDate().isAfter(departureDate))
                );

        if (isBooked) {
            throw new RestException("This bed is already booked for the selected dates.");
        }

        CartItemBed newCartItemBed = new CartItemBed();
        newCartItemBed.setBed(foundBed);
        newCartItemBed.setCart(userCart);
        newCartItemBed.setEntryDate(entryDate);
        newCartItemBed.setDepartureDate(departureDate);

        cartItemBedRepository.save(newCartItemBed);
        userCart.getCartItemBeds().add(newCartItemBed);
        cartRepository.save(userCart);
    }

    @Override
    public void removeBedFromCart(User authUser, Long bedId) {
        Bed foundBed = bedService.getBedOrThrow(bedId);
        Cart userCart = getCartEntity(authUser);

        List<CartItemBed> cartItemBeds = userCart.getCartItemBeds();
        Optional<CartItemBed> itemBed = cartItemBeds.stream()
                .filter(cartItemBed -> cartItemBed.getBed().equals(foundBed))
                .findFirst();
        itemBed.ifPresent(cartItemBeds::remove);
        cartRepository.save(userCart);
    }

    @Override
    public BigDecimal getTotalPrice(User authUser) {
        Cart userCart = getCartEntity(authUser);

        return userCart.getCartItemBeds()
                .stream()
                .map(this::calculateTotalCostForBed)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public void clearUserCart(User authUser) {
        Cart cartEntity = getCartEntity(authUser);
        cartEntity.getCartItemBeds().clear();

        cartRepository.save(cartEntity);
    }

    @Override
    public void delete(Cart cart) {
        cartRepository.delete(cart);
    }

    @Override
    public boolean isBedInCart(Long bedId) {
        return cartItemBedRepository.existsByBedId(bedId);
    }
}
