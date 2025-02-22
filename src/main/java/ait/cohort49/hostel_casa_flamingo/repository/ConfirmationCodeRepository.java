package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {

    Optional<ConfirmationCode> findByCode(String code);
}
