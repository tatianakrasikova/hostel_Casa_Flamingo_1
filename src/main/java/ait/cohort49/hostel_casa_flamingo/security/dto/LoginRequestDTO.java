package ait.cohort49.hostel_casa_flamingo.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO for user login request")
public record LoginRequestDTO(@NotBlank(message = "{validation.email.not_blank}")
                              @Email(message = "{validation.email.invalid}")
                              @Schema(
                                      description = "User's email address",
                                      example = "user@example.com"
                              )
                              String userEmail,


                              @NotBlank(message = "{validation.password.not_blank}")
                              @Schema(
                                      description = "User's password",
                                      example = "Password123!"
                              )
                              String password
) {
}
