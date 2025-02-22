package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.S3StorageService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.RoomMappingService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMappingService roomMappingService;
    private final BedRepository bedRepository;
    private final S3StorageService s3StorageService;
    private final BedService bedService;

    public RoomServiceImpl(RoomRepository roomRepository,
                           RoomMappingService roomMappingService,
                           BedRepository bedRepository,
                           S3StorageService s3StorageService,
                           @Lazy BedService bedService) {
        this.roomRepository = roomRepository;
        this.roomMappingService = roomMappingService;
        this.bedRepository = bedRepository;
        this.s3StorageService = s3StorageService;
        this.bedService = bedService;
    }

    @Override
    public RoomDto getRoomById(Long id) {
        Room room = findByIdOrThrow(id);
        return mapBedToDtoWithImagesForRoom(room);
    }

    @Override
    public List<RoomDto> getAllRooms() {
        return roomRepository.findAllSortedByRoomNumber()
                .stream()
                .map(this::mapBedToDtoWithImagesForRoom)
                .toList();
    }

    private RoomDto mapBedToDtoWithImagesForRoom(Room room) {
        List<Image> roomImages = room.getImages();
        List<String> roomImagesUrls = s3StorageService.getImageUrl(roomImages);

        BigDecimal priceRoom = room.getBeds()
                .stream()
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        RoomDto roomDto = roomMappingService.mapEntityToDto(room);
        roomDto.setImageUrls(roomImagesUrls);
        roomDto.setPrice(priceRoom);
        roomDto.setDescription(room.getDescription());

        for (BedDto bedDto : roomDto.getBeds()) {
            Bed bed = bedRepository.findById(bedDto.getId())
                    .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bed not found with id: " + bedDto.getId()));
            BedDto bedImagesUrls = bedService.mapBedToDtoWithImages(bed);
            bedDto.setImageUrls(bedImagesUrls.getImageUrls());
        }
        return roomDto;
    }

    @Override
    public RoomDto createRoom(CreateOrUpdateRoomDto createRoomDto) {
        Room room = roomMappingService.mapDtoToEntity(createRoomDto);
        return roomMappingService.mapEntityToDto(roomRepository.save(room));
    }

    @Override
    @Transactional
    public RoomDto updateRoom(Long id, RoomDto requestDto) {
        Room existingRoom = findByIdOrThrow(id);

        existingRoom.setNumber(requestDto.getNumber());
        existingRoom.setType(requestDto.getType());

        Room savedRoom = roomRepository.save(existingRoom);
        return roomMappingService.mapEntityToDto(savedRoom);
    }

    @Override
    @Transactional
    public void deleteRoom(Long id) {
        Room room = findByIdOrThrow(id);
        List<Bed> beds = room.getBeds();

        for (Bed bed : beds) {
            bedService.deleteBed(bed);
        }
        roomRepository.delete(room);
    }

    @Override
    public Room findByIdOrThrow(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Room by id: " + id + " not found"));
    }

    @Override
    public BigDecimal getTotalBedPriceForRoom(Long roomId) {
        Room selectedRoom = findByIdOrThrow(roomId);
        return getTotalBedPriceForRoom(selectedRoom);
    }

    @Override
    public BigDecimal getTotalBedPriceForRoom(Room room) {
        return room.getBeds()
                .stream()
                .map(Bed::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
