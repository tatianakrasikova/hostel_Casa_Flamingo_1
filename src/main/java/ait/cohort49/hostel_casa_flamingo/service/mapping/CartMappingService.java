package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.CartDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMappingService.class})
public interface CartMappingService {

    @Mapping(target = "totalPriceBeds", ignore = true)
    @Mapping(target = "countBeds", ignore = true)
    @Mapping(target = "userDto", source = "user")
    @Mapping(target = "beds", ignore = true)
    CartDto mapEntityToDto(Cart entity);
}
