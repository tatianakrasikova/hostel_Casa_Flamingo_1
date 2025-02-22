package ait.cohort49.hostel_casa_flamingo.security.controller;

import ait.cohort49.hostel_casa_flamingo.exception.ExceptionResponseDto;
import ait.cohort49.hostel_casa_flamingo.exception.ValidationErrorResponse;
import ait.cohort49.hostel_casa_flamingo.model.dto.UserDto;
import ait.cohort49.hostel_casa_flamingo.security.dto.LoginRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RefreshRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.RegisterRequestDTO;
import ait.cohort49.hostel_casa_flamingo.security.dto.TokenResponseDTO;
import ait.cohort49.hostel_casa_flamingo.security.service.AuthService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @Operation(
            summary = "User Registration",
            description = "Registers a new user using the provided registration details. A confirmation email is sent upon successful registration.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User successfully registered",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "409", description = "Validation error or user with the provided email already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto register(
            @RequestBody(
                    description = "Registration details for the new user",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RegisterRequestDTO.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody
            RegisterRequestDTO registerRequestDTO) {
        return authService.register(registerRequestDTO);
    }

    @Operation(
            summary = "User Confirmation",
            description = "Confirms the user registration using the confirmation code provided via email.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully confirmed",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))),
                    @ApiResponse(responseCode = "404", description = "Confirmation code not found or invalid",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    @GetMapping("/confirm")
    public UserDto confirm(
            @Parameter(
                    in = ParameterIn.QUERY,
                    description = "Confirmation code sent via email",
                    required = true,
                    schema = @Schema(type = "string")
            )
            @RequestParam
            String code) {
        return userService.confirmUser(code);
    }

    @Operation(
            summary = "User Login",
            description = "Authenticates the user using email and password. Returns an access token and refresh token upon successful authentication.",
            security = @SecurityRequirement(name = ""),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully authenticated",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ValidationErrorResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid email or password",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    @PostMapping("/login")
    public TokenResponseDTO login(
            @RequestBody(
                    description = "User credentials for authentication (email and password)",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginRequestDTO.class))
            )
            @Valid
            @org.springframework.web.bind.annotation.RequestBody
            LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }


    @Operation(
            summary = "Refresh Access Token",
            description = "Refreshes the access token using the provided refresh token.",
            security = @SecurityRequirement(name = ""),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Access token successfully refreshed",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenResponseDTO.class))),
                    @ApiResponse(responseCode = "403", description = "Invalid or expired refresh token",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Validation error",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionResponseDto.class)))
            }
    )
    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken(
            @RequestBody(
                    description = "Object containing the refresh token",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = RefreshRequestDTO.class))
            )
            @Valid @org.springframework.web.bind.annotation.RequestBody
            RefreshRequestDTO refreshRequestDTO) {
        return authService.refreshAccessToken(refreshRequestDTO.refreshToken());
    }
}
