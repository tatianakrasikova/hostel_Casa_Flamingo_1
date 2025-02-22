package ait.cohort49.hostel_casa_flamingo.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;

@Schema(description = "DTO representing the token response containing access and refresh tokens")
public class TokenResponseDTO {

    @Schema(
            description = "Refresh token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String refreshToken;

    @Schema(
            description = "Access token",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String accessToken;

    public TokenResponseDTO() {

    }

    public TokenResponseDTO(String accessToken, String refreshToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return String.format("TokenResponse: access_token: %s, refresh_token: %s", accessToken, refreshToken);
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TokenResponseDTO that)) return false;
        return Objects.equals(refreshToken, that.refreshToken) && Objects.equals(accessToken, that.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(refreshToken, accessToken);
    }
}
