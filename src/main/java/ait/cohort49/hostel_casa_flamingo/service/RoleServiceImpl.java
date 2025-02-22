package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.entity.Role;
import ait.cohort49.hostel_casa_flamingo.repository.RoleRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findRoleByTitleOrThrow(String title) {
        return roleRepository.findRoleByTitleIgnoreCase(title)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "Role not found"));
    }
}
