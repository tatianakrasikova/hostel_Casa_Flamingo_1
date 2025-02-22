package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartItemBedDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.CartItemBed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BedMappingService.class})
public interface CartItemBedMappingService {

    CartItemBedDto mapEntityToDto(CartItemBed entity);
}
