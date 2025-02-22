package ait.cohort49.hostel_casa_flamingo.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

/**
 * GlobalExceptionHandlerController is a class that handles global exceptions and provides appropriate responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandlerController {

    /**
     * Handles and provides a custom exception response for RestException.
     *
     * @param ex The RestException to handle.
     * @return A ResponseEntity containing an ExceptionResponseDto, representing the custom exception response.
     */
    @ExceptionHandler(RestException.class)
    public ResponseEntity<ExceptionResponseDto> handleCustomException(RestException ex) {
        final String message = ex.getMessage();
        final HttpStatus httpStatus = ex.getHttpStatus();
        return ResponseEntity.status(httpStatus).body(new ExceptionResponseDto(message, httpStatus.value()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleConstraintValidationException(ConstraintViolationException ex) {
        return createValidationErrorResponse(
                ex.getConstraintViolations().stream()
                        .map(violation -> new Violation(violation.getPropertyPath().toString(), violation.getMessage()))
                        .toList());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return createValidationErrorResponse(
                ex.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> new Violation(fieldError.getField(), fieldError.getDefaultMessage()))
                        .toList());
    }

    private static ValidationErrorResponse createValidationErrorResponse(List<Violation> violations) {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setErrors(violations);
        return response;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionResponseDto handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        final String message = ex.getMessage();
        return new ExceptionResponseDto(message, HttpStatus.BAD_REQUEST.value());
    }
}
