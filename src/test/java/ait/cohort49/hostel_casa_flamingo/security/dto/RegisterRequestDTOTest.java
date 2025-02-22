package ait.cohort49.hostel_casa_flamingo.security.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterRequestDTOTest extends ValidationTestUtil<RegisterRequestDTO> {

    @Override
    protected RegisterRequestDTO createSubject() {
        return new RegisterRequestDTO(
                "test@example.com",
                "StrongPass1!",
                "+1234567890",
                "John",
                "Doe"
        );
    }

    @Nested
    @DisplayName("Password Validation Tests")
    class PasswordValidationTests {

        @Test
        @DisplayName("Should pass validation with valid password")
        void shouldPassValidationWithValidPassword() {
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    "test@example.com",
                    "ValidPass1!",
                    "+1234567890",
                    "John",
                    "Doe"
            );

            Map<String, List<String>> violations = getViolationMap(dto);

            assertThat(violations).doesNotContainKey("password");
        }

        @Test
        @DisplayName("Should fail validation when password is less than 8 characters")
        void shouldFailValidationWhenPasswordTooShort() {
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    "test@example.com",
                    "S1!",
                    "+1234567890",
                    "John",
                    "Doe"
            );

            Map<String, List<String>> violations = getViolationMap(dto);

            assertThat(violations).containsKey("password");
            validateMessage(violations.get("password"),
                    "Password must meet the specified requirements. Please ensure it contains at least 8 characters, one uppercase letter, one lowercase letter, and one number.");
        }

        @Test
        @DisplayName("Should fail validation when password has no digit")
        void shouldFailValidationWhenPasswordHasNoDigit() {
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    "test@example.com",
                    "InvalidPass!",
                    "+1234567890",
                    "John",
                    "Doe"
            );

            Map<String, List<String>> violations = getViolationMap(dto);

            assertThat(violations).containsKey("password");
            validateMessage(violations.get("password"),
                    "Password must meet the specified requirements. Please ensure it contains at least 8 characters, one uppercase letter, one lowercase letter, and one number.");
        }

        @Test
        @DisplayName("Should fail validation when password has no uppercase letter")
        void shouldFailValidationWhenPasswordHasNoUppercase() {
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    "test@example.com",
                    "invalidpass1!",
                    "+1234567890",
                    "John",
                    "Doe"
            );

            Map<String, List<String>> violations = getViolationMap(dto);

            assertThat(violations).containsKey("password");
            validateMessage(violations.get("password"),
                    "Password must meet the specified requirements. Please ensure it contains at least 8 characters, one uppercase letter, one lowercase letter, and one number.");
        }

        @Test
        @DisplayName("Should fail validation when password has no lowercase letter")
        void shouldFailValidationWhenPasswordHasNoLowercase() {
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    "test@example.com",
                    "INVALIDPASS1!",
                    "+1234567890",
                    "John",
                    "Doe"
            );

            Map<String, List<String>> violations = getViolationMap(dto);

            assertThat(violations).containsKey("password");
            validateMessage(violations.get("password"),
                    "Password must meet the specified requirements. Please ensure it contains at least 8 characters, one uppercase letter, one lowercase letter, and one number.");
        }

        @Test
        @DisplayName("Should fail validation when password has no special character")
        void shouldFailValidationWhenPasswordHasNoSpecialCharacter() {
            RegisterRequestDTO dto = new RegisterRequestDTO(
                    "test@example.com",
                    "ValidPass1",
                    "+1234567890",
                    "John",
                    "Doe"
            );

            Map<String, List<String>> violations = getViolationMap(dto);

            assertThat(violations).containsKey("password");
            validateMessage(violations.get("password"),
                    "Password must meet the specified requirements. Please ensure it contains at least 8 characters, one uppercase letter, one lowercase letter, and one number.");
        }
    }

    @Test
    @DisplayName("Should pass validation with valid data")
    void shouldPassValidationWithValidData() {
        RegisterRequestDTO dto = createSubject();
        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).isEmpty(); // Нет ошибок валидации
    }

    @Test
    @DisplayName("Should fail validation when email is blank")
    void shouldFailValidationWhenEmailIsBlank() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "",
                "StrongPass1!",
                "+1234567890",
                "John",
                "Doe"
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKey("userEmail");
        validateMessage(violations.get("userEmail"), "User email must not be blank.");
    }

    @Test
    @DisplayName("Should fail validation when email format is invalid")
    void shouldFailValidationWhenEmailFormatIsInvalid() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "invalid-email",
                "StrongPass1!",
                "+1234567890",
                "John",
                "Doe"
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKey("userEmail");
        validateMessage(violations.get("userEmail"), "Invalid email format. Please provide a valid email address.");
    }

    @Test
    @DisplayName("Should fail validation when password is blank")
    void shouldFailValidationWhenPasswordIsBlank() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "test@example.com",
                "",
                "+1234567890",
                "John",
                "Doe"
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKey("password");
        validateMessage(violations.get("password"), "Password must not be blank.");
    }

    @Test
    @DisplayName("Should fail validation when password does not meet requirements")
    void shouldFailValidationWhenPasswordDoesNotMeetRequirements() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "test@example.com",
                "weakpass",
                "+1234567890",
                "John",
                "Doe"
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKey("password");
        validateMessage(violations.get("password"),
                "Password must meet the specified requirements. Please ensure it contains at least 8 characters, one uppercase letter, one lowercase letter, and one number.");
    }

    @Test
    @DisplayName("Should fail validation when phone number is blank")
    void shouldFailValidationWhenPhoneNumberIsBlank() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "test@example.com",
                "StrongPass1!",
                "",
                "John",
                "Doe"
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKey("phoneNumber");
        validateMessage(violations.get("phoneNumber"), "Phone number must not be blank.");
    }

    @Test
    @DisplayName("Should fail validation when first name is blank")
    void shouldFailValidationWhenFirstNameIsBlank() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "test@example.com",
                "StrongPass1!",
                "+1234567890",
                "",
                "Doe"
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKey("firstName");
        validateMessage(violations.get("firstName"), "First name must not be blank.");
    }

    @Test
    @DisplayName("Should fail validation when last name is blank")
    void shouldFailValidationWhenLastNameIsBlank() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "test@example.com",
                "StrongPass1!",
                "+1234567890",
                "John",
                ""
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKey("lastName");
        validateMessage(violations.get("lastName"), "Last name must not be blank.");
    }

    @Test
    @DisplayName("Should fail validation for multiple blank fields")
    void shouldFailValidationForMultipleBlankFields() {
        RegisterRequestDTO dto = new RegisterRequestDTO(
                "",
                "",
                "",
                "",
                ""
        );

        Map<String, List<String>> violations = getViolationMap(dto);

        assertThat(violations).containsKeys("userEmail", "password", "phoneNumber", "firstName", "lastName");

        validateMessage(violations.get("userEmail"), "User email must not be blank.");
        validateMessage(violations.get("password"), "Password must not be blank.");
        validateMessage(violations.get("phoneNumber"), "Phone number must not be blank.");
        validateMessage(violations.get("firstName"), "First name must not be blank.");
        validateMessage(violations.get("lastName"), "Last name must not be blank.");
    }
}
