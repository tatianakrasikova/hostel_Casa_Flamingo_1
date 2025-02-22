package ait.cohort49.hostel_casa_flamingo.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for refresh token request")
public record RefreshRequestDTO(
        @Schema(
                description = "Refresh token string",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        )
        String refreshToken) {
}
