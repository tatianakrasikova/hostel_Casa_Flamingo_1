package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.CartItemBed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CartItemBedRepository extends JpaRepository<CartItemBed, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItemBed c WHERE c.bed.id = :bedId")
    boolean deleteBedById(Long bedId);

    boolean existsByBedId(Long bedId);
}
