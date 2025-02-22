package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.ImageInfoDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.ImageRepository;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.ImageService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.S3StorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final S3StorageService s3StorageService;
    private final BedRepository bedRepository;
    private final RoomRepository roomRepository;

    @Value("${s3.bucketName}")
    private String bucketName;

    public ImageServiceImpl(ImageRepository imageRepository, S3StorageService s3StorageService, BedRepository bedRepository, RoomRepository roomRepository) {
        this.imageRepository = imageRepository;
        this.s3StorageService = s3StorageService;
        this.bedRepository = bedRepository;
        this.roomRepository = roomRepository;
    }


    @Override
    public String uploadImageForBed(MultipartFile file, Bed bed) {
        String fileOriginalFilename = file.getOriginalFilename();
        try {
            String s3Path = "beds/" + bed.getId() + "/images/" + UUID.randomUUID() + "_" + fileOriginalFilename;
            s3StorageService.uploadFile(s3Path, file);

            Image image = new Image(s3Path, fileOriginalFilename, bucketName, bed);
            imageRepository.save(image);
            return s3StorageService.getImageUrl(s3Path);
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while uploading image", e);
        }
    }

    @Override
    public String uploadImageForRoom(MultipartFile file, Room room) {
        String fileOriginalFilename = file.getOriginalFilename();
        try {
            String s3Path = "rooms/" + room.getId() + "/images/" + UUID.randomUUID() + "_" + fileOriginalFilename;
            s3StorageService.uploadFile(s3Path, file);

            Image image = new Image(s3Path, fileOriginalFilename, bucketName, room);
            imageRepository.save(image);
            return s3StorageService.getImageUrl(s3Path);
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while uploading image for room", e);
        }
    }

    @Override
    public List<ImageInfoDto> getImagesByBed(Long bedId) {
        Bed bed = bedRepository.findById(bedId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "This bed is not found"));

        return imageRepository.findAllByBed(bed)
                .stream()
                .map(image -> {
                    String imageUrl = s3StorageService.getImageUrl(image.getS3Path());
                    return new ImageInfoDto(image.getId(), imageUrl);
                })
                .toList();
    }

    @Override
    public List<ImageInfoDto> getImagesByRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "This room is not found"));

        return imageRepository.findAllByRoom(room)
                .stream()
                .map(image -> {
                    String imageUrl = s3StorageService.getImageUrl(image.getS3Path());
                    return new ImageInfoDto(image.getId(), imageUrl);
                })
                .toList();
    }

    @Override
    public void deleteImage(Long id) {

        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Image by id: " + id + " not found"));

        s3StorageService.deleteFile(image.getS3Path());

        imageRepository.delete(image);
    }


    @Override
    public String getImageUrl(Long imageId) {

        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Image by id: " + imageId + " not found"));

        return s3StorageService.getImageUrl(image.getS3Path());
    }
}
