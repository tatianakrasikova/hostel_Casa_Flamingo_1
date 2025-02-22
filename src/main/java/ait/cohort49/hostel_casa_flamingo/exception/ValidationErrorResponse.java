package ait.cohort49.hostel_casa_flamingo.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "Response for validation errors")
public class ValidationErrorResponse {

    @Schema(description = "List of validation errors")
    private List<Violation> errors = new ArrayList<>();

    public List<Violation> getErrors() {
        return errors;
    }

    public void setErrors(List<Violation> errors) {
        this.errors = errors;
    }
}

