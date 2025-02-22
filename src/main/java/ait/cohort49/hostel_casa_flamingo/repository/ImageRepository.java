package ait.cohort49.hostel_casa_flamingo.repository;

import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("select i from Image i where i.bed = ?1")
    List<Image> findAllByBed(Bed bed);

    @Query("select i from Image i where i.room = ?1")
    List<Image> findAllByRoom(Room room);
}
