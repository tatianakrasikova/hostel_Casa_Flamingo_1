package ait.cohort49.hostel_casa_flamingo.security.dto;

import ait.cohort49.hostel_casa_flamingo.constants.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public record RegisterRequestDTO(
        @NotBlank(message = "{validation.email.not_blank}")
        @Pattern(
                regexp = "^(?!.*\\.\\.)([a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,})$",
                message = "{validation.email.invalid}"
        )
        @Schema(
                description = "User's email address",
                example = "newuser@example.com"
        )
        String userEmail,

        @NotBlank(message = "{validation.password.not_blank}")
        @Pattern(
                regexp = ValidationConstants.PASSWORD_REGEX,
                message = "{validation.password.invalid}"
        )
        @Schema(
                description = "User's password",
                example = "StrongPassword123!"
        )
        String password,

        @NotBlank(message = "{validation.phone_number.not_blank}")
        @Pattern(
                regexp = "^\\+?[0-9]{10,15}$",
                message = "{validation.phone_number.invalid}"
        )
        @Schema(
                description = "User's phone number",
                example = "+12345678901"
        )
        String phoneNumber,

        @NotBlank(message = "{validation.first_name.not_blank}")
        @Pattern(
                regexp = "^[A-Za-z]{2,50}$",
                message = "{validation.first_name.invalid}"
        )
        @Schema(
                description = "User's first name",
                example = "Alice"
        )
        String firstName,

        @NotBlank(message = "{validation.last_name.not_blank}")
        @Pattern(
                regexp = "^[A-Za-z]{2,50}$",
                message = "{validation.last_name.invalid}"
        )
        @Schema(
                description = "User's last name",
                example = "Smith"
        )
        String lastName
) {
}
