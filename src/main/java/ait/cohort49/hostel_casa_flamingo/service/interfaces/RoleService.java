package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.entity.Role;

public interface RoleService {
    Role findRoleByTitleOrThrow(String title);
}
