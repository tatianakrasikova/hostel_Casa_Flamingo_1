package ait.cohort49.hostel_casa_flamingo.exception;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details of a validation violation")
public record Violation(
        @Schema(description = "Field where the error occurred")
        String fieldName,

        @Schema(description = "Error message for the field")
        String message) {
}
