package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}