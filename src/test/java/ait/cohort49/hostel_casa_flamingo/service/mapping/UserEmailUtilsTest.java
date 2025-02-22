package ait.cohort49.hostel_casa_flamingo.service.mapping;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserEmailUtilsTest {

    @DisplayName("Test normalization of valid email inputs")
    @ParameterizedTest(name = "Input: \"{0}\", Expected result: \"{1}\"")
    @MethodSource("provideEmailsForNormalization")
    void testNormalizeUserEmailWithValidInputs(String inputEmail, String expectedEmail) {
        String result = UserEmailUtils.normalizeUserEmail(inputEmail);
        assertEquals(expectedEmail, result);
    }

    @DisplayName("Test normalization with null input")
    @ParameterizedTest
    @NullSource
    void testNormalizeUserEmailWithNullInput(String inputEmail) {
        String result = UserEmailUtils.normalizeUserEmail(inputEmail);
        assertNull(result);
    }

    static Stream<Object[]> provideEmailsForNormalization() {
        return Stream.of(
                new Object[]{"  TEST.Email@example.com  ", "test.email@example.com"},
                new Object[]{"normalized.email@example.com", "normalized.email@example.com"},
                new Object[]{"   ", ""},
                new Object[]{"  user123@example.com  ", "user123@example.com"}
        );
    }
}