package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.BedDto;
import ait.cohort49.hostel_casa_flamingo.model.dto.CreateBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Bed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface BedMappingService {

    @Mapping(target = "room.id", source = "roomId")
    @Mapping(target = "images", ignore = true)
    Bed mapDtoToEntity(CreateBedDto createBedDto);

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "imageUrls", ignore = true)
    BedDto mapEntityToDto(Bed entity);
}
