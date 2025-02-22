package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUser(User authUser);

    @Query("SELECT COUNT(b) > 0 FROM Booking b " +
            "WHERE b.bed.id = :bedId " +
            "AND (b.entryDate <= :departureDate AND b.departureDate >= :entryDate)")
    boolean isBedBooked(@Param("bedId") Long bedId,
                        @Param("entryDate") LocalDate entryDate,
                        @Param("departureDate") LocalDate departureDate);

    boolean existsByIdAndDepartureDateAfter(Long bedId, LocalDate date);

    List<Booking> findBedByIdAndDepartureDateBefore(Long bedId, LocalDate date);
}
