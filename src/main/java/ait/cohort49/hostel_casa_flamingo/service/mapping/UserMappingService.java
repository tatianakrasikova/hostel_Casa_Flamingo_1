package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {RoleMappingService.class})
public interface UserMappingService {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "password", ignore = true)
    User mapDtoToEntity(UserDto userDto);

    UserDto mapEntityToDto(User entity);

}
