package ait.cohort49.hostel_casa_flamingo.exception;

import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.text.MessageFormat;

/**
 * @author Andrej Reutow
 * created on 06.12.2023
 */
public class RestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatus httpStatus;

    public RestException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public RestException(HttpStatus httpStatus, String message, Object... args) {
        super(MessageFormat.format(message, args));
        this.httpStatus = httpStatus;
    }

    public RestException(String message, Object... args) {
        super(MessageFormat.format(message, args));
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public RestException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
