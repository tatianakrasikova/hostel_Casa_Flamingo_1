package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.AvailableBedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import ait.cohort49.hostel_casa_flamingo.model.entity.Image;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import ait.cohort49.hostel_casa_flamingo.repository.BedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.BookingRepository;
import ait.cohort49.hostel_casa_flamingo.repository.CartItemBedRepository;
import ait.cohort49.hostel_casa_flamingo.repository.RoomRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BedService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoomService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.S3StorageService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.AvailableBedMappingService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BedMappingService;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
public class BedServiceImpl implements BedService {

    private final BedRepository bedRepository;
    private final BedMappingService bedMappingService;
    private final RoomService roomService;
    private final S3StorageService s3StorageService;
    private final AvailableBedMappingService availableBedMappingService;
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final CartItemBedRepository cartItemBedRepository;

    public BedServiceImpl(BedRepository bedRepository,
                          BedMappingService bedMappingService,
                          @Lazy RoomService roomService,
                          S3StorageService s3StorageService,
                          AvailableBedMappingService availableBedMappingService,
                          RoomRepository roomRepository,
                          BookingRepository bookingRepository,
                          CartItemBedRepository cartItemBedRepository) {
        this.bedRepository = bedRepository;
        this.bedMappingService = bedMappingService;
        this.roomService = roomService;
        this.s3StorageService = s3StorageService;
        this.availableBedMappingService = availableBedMappingService;
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
        this.cartItemBedRepository = cartItemBedRepository;
    }

    @Override
    public BedDto saveBed(CreateBedDto createBedDto) {
        Bed bed = bedMappingService.mapDtoToEntity(createBedDto);
        Room room = roomService.findByIdOrThrow(createBedDto.getRoomId());
        bed.setRoom(room);

        if (bedRepository.existsByRoomIdAndNumber(room.getId(), bed.getNumber())) {
            throw new RestException(HttpStatus.CONFLICT, "Bed number already exists in this room");
        }
        return bedMappingService.mapEntityToDto(bedRepository.save(bed));
    }

    @Override
    public Bed getBedOrThrow(long id) {
        return bedRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Bed by id: " + id + " not found"));
    }

    @Override
    public BedDto getBedById(Long id) {
        Bed bed = getBedOrThrow(id);
        return mapBedToDtoWithImages(bed);
    }

    @Override
    public List<BedDto> getAllBeds() {
        return bedRepository.findAll().stream()
                .map(this::mapBedToDtoWithImages)
                .toList();
    }

    @Override
    public BedDto updateBed(Long id, BedDto bedDto) {
        Bed existingBed = bedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bed not found by id: " + id));

        existingBed.setNumber(bedDto.getNumber());
        existingBed.setType(bedDto.getType());
        existingBed.setPrice(bedDto.getPrice());

        Bed savedBed = bedRepository.save(existingBed);

        return bedMappingService.mapEntityToDto(savedBed);
    }

    @Override
    public void deleteBedById(Long id) {
        Bed bed = getBedOrThrow(id);
        deleteBed(bed);
    }

    @Transactional
    @Override
    public void deleteBed(Bed bed) {
        Long bedId = bed.getId();
        if (hasActiveBookings(bedId)) {
            throw new RestException(HttpStatus.BAD_REQUEST, "This bed is already booked");
        }

        if (isBedInCart(bedId)) {
            throw new RestException(HttpStatus.BAD_REQUEST, "This bed is already booked");
        }

        List<Booking> pastBookings = getPastBookings(bedId);
        {
            if (pastBookings != null && !pastBookings.isEmpty())
                deletePastBookings(pastBookings);
        }
        bedRepository.delete(bed);
    }

    @Override
    public List<AvailableBedDto> getAvailableBeds(Long id, LocalDate entryDate, LocalDate departureDate) {

        roomRepository.findById(id)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Room with id " + id + " not found"));

        return bedRepository.findAvailableBedsByRoomId(id, entryDate, departureDate).stream()
                .map(bed -> {
                    AvailableBedDto availableBedDto = availableBedMappingService.mapBedToAvailableBedDto(bed);
                    availableBedDto.setAvailable(true);
                    return availableBedDto;
                })
                .toList();
    }

    public boolean isBedInCart(Long bedId) {
        return cartItemBedRepository.existsByBedId(bedId);
    }

    public boolean hasActiveBookings(Long bedId) {
        return bookingRepository.existsByIdAndDepartureDateAfter(bedId, LocalDate.now());
    }

    @Transactional
    public List<Booking> getPastBookings(Long bedId) {
        return bookingRepository.findBedByIdAndDepartureDateBefore(bedId, LocalDate.now());
    }

    public void deletePastBookings(List<Booking> pastBookings) {
        bookingRepository.deleteAll(pastBookings);
    }

    @Override
    public BedDto mapBedToDtoWithImages(Bed bed) {
        List<Image> bedImages = bed.getImages();
        List<String> bedImagesUrls = s3StorageService.getImageUrl(bedImages);
        BedDto bedDto = bedMappingService.mapEntityToDto(bed);
        bedDto.setImageUrls(bedImagesUrls);
        return bedDto;
    }
}
