package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.ImageInfoDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ImageService {

    String uploadImageForBed(MultipartFile file, Bed bed);

    String uploadImageForRoom(MultipartFile file, Room room);

    List<ImageInfoDto> getImagesByBed(Long bedId);

    List<ImageInfoDto> getImagesByRoom(Long roomId);

    void deleteImage(Long id);

    String getImageUrl(Long imageId);
}
