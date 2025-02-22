package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

public interface UserService {

    UserDto findUserByEmailOrThrow(String userEmail);

    User getUserByEmailOrThrow(String userEmail);

    UserDto confirmUser(String confirmationToken);
}
