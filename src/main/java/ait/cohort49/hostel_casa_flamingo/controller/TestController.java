package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth-test")
public class TestController {

    private final UserService userService;

    public TestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto testAuthAdmin(@AuthenticationPrincipal String userEmail) {
        return userService.findUserByEmailOrThrow(userEmail);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public UserDto testAuthUser(@AuthenticationPrincipal String userEmail) {
        return userService.findUserByEmailOrThrow(userEmail);
    }

    @GetMapping("/anyone")
    @PreAuthorize("isAuthenticated()")
    public UserDto testAuth(@AuthenticationPrincipal String userEmail) {
        return userService.findUserByEmailOrThrow(userEmail);
    }

    @GetMapping("/no-auth")
    public String testNoAuth() {
        return "No auth";
    }
}
