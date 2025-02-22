package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.AvailableBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AvailableBedMappingService {

    @Mapping(target = "isAvailable", ignore = true)
    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "imageUrls", ignore = true)
    AvailableBedDto mapBedToAvailableBedDto(Bed bed);
}



