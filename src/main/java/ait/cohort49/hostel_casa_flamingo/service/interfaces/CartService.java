package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface CartService {

    CartDto getCart(User user);

    Cart getCartEntity(User authUser);

    void addBedToCart(User authUser, Long bedId, LocalDate entryDate, LocalDate departureDate);

    void removeBedFromCart(User authUser, Long bedId);

    BigDecimal getTotalPrice(User authUser);

    void clearUserCart(User authUser);

    void delete(Cart cart);

    boolean isBedInCart(Long id);
}
