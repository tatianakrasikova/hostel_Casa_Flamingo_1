package ait.cohort49.hostel_casa_flamingo.service.mapping;

import ait.cohort49.hostel_casa_flamingo.model.dto.RoleDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoleMappingService {

    @Mapping(target = "name", source = "title")
    RoleDto toDto(Role role);
}