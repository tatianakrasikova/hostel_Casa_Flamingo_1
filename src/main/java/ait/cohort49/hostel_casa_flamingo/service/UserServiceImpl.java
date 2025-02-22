package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.ConfirmationCode;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.UserRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.ConfirmationService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.UserMappingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMappingService userMappingService;
    private final ConfirmationService confirmationService;

    public UserServiceImpl(UserRepository userRepository,
                           UserMappingService userMappingService,
                           ConfirmationService confirmationService) {
        this.userRepository = userRepository;
        this.userMappingService = userMappingService;
        this.confirmationService = confirmationService;
        System.out.println("UserServiceImpl initialized");
    }

    @Override
    public UserDto findUserByEmailOrThrow(String userEmail) {
        User user = userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with email: " + userEmail + " not found"));
        return userMappingService.mapEntityToDto(user);
    }

    @Override
    public User getUserByEmailOrThrow(String userEmail) {
        return userRepository.findUserByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User with email: " + userEmail + " not found"));
    }

    @Override
    public UserDto confirmUser(String confirmationToken) {
        ConfirmationCode confirmationCode = confirmationService.validateToken(confirmationToken);

        User user = confirmationCode.getUser();
        if (null != user) {
            user.setIsConfirmed(true);
            userRepository.save(user);
            return userMappingService.mapEntityToDto(user);
        } else {
            throw new RestException(HttpStatus.NOT_FOUND, "User for this confirmation code not found");
        }
    }
}
