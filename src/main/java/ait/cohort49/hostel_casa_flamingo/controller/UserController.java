package ait.cohort49.hostel_casa_flamingo.controller;

import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Controller for operations with users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get current user", description = "Retrieve user data for the currently authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)),
                            @Content(mediaType = "application/xml",
                                    schema = @Schema(implementation = UserDto.class))
                    }),
            @ApiResponse(responseCode = "401", description = "User not authenticated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not authenticated"))
            ),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "User not found")))
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public UserDto getMe(@AuthenticationPrincipal String userEmail) {
        return userService.findUserByEmailOrThrow(userEmail);
    }
}
