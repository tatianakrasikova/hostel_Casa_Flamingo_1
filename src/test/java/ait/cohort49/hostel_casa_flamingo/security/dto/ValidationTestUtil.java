package ait.cohort49.hostel_casa_flamingo.security.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

abstract class ValidationTestUtil<T> {
    protected static Validator validator;

    @BeforeEach
    public void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        } catch (Exception e) {
            throw new IllegalArgumentException("Error initializing validator", e);
        }
    }

    @Test
    @DisplayName("Should have no validation errors")
    protected void testValid() {
        T subject = createSubject();
        assertNotNull(subject, "Entity(subject) cannot be null, please check your test data and createSubject implementation!");

        Map<String, List<String>> pathListMap = getViolationMap(subject);
        assertTrue(pathListMap.isEmpty());
    }

    protected Set<ConstraintViolation<T>> validatesubject(T subject) {
        return validator.validate(subject);
    }

    protected Map<String, List<String>> convertToMap(Set<ConstraintViolation<T>> violations) {
        Map<String, List<String>> pathListMap = new HashMap<>();
        for (ConstraintViolation<T> violation : violations) {
            String key = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            pathListMap.computeIfAbsent(key, k -> new ArrayList<>()).add(message);
        }
        return pathListMap;
    }

    protected Map<String, List<String>> getViolationMap(T subject) {
        Set<ConstraintViolation<T>> validationErrors = validatesubject(subject);
        return convertToMap(validationErrors);
    }

    protected void validateMessage(List<String> messages, String searchMessage) {
        assertTrue(messages.contains(searchMessage),
                "\nExcepted message '" + searchMessage +
                        "' not encountered in messages:\n" +
                        messages.stream().collect(Collectors.joining("\n", "\t- ", ";")));
    }

    protected abstract T createSubject();
}
