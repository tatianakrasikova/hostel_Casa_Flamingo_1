package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.CreateOrUpdateRoomDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.RoomDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = BedMappingService.class)
public interface RoomMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "beds", ignore = true)
    @Mapping(target = "images", ignore = true)
    Room mapDtoToEntity(CreateOrUpdateRoomDto roomDto);

    @Mapping(target = "price", ignore = true)
    @Mapping(target = "imageUrls", ignore = true)
    RoomDto mapEntityToDto(Room entity);
}
