package ait.cohort49.hostel_casa_flamingo.exception;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Andrej Reutow
 * created on 07.12.2023
 */

@Schema(description = "DTO for representing exception responses")
public record ExceptionResponseDto(
        @Schema(description = "Error message explaining the exception", example = "Invalid input data")
        String message,
        @Schema(description = "HTTP status code associated with the exception", example = "400")
        Integer statusCode
) {
}
