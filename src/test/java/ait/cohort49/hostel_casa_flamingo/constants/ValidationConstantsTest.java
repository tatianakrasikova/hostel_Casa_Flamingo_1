package ait.cohort49.hostel_casa_flamingo.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordRegexTest {

    private static final Pattern PASSWORD_PATTERN = Pattern.compile(ValidationConstants.PASSWORD_REGEX);

    @Test
    @DisplayName("Should match valid passwords")
    void shouldMatchValidPasswords() {
        // Тестовые данные с корректными паролями
        String[] validPasswords = {
                "StrongPass1!",
                "P@ssw0rd123",
                "Valid#123",
                "My$ecureP@ss1",
                "ComplexP@55word!"
        };

        for (String password : validPasswords) {
            assertThat(PASSWORD_PATTERN.matcher(password).matches())
                    .as("Expected password '%s' to match the pattern", password)
                    .isTrue();
        }
    }

    @Test
    @DisplayName("Should not match invalid passwords")
    void shouldNotMatchInvalidPasswords() {
        // Тестовые данные с некорректными паролями
        String[] invalidPasswords = {
                "short1!",         // Менее 8 символов
                "NoSpecialChar1",  // Нет специального символа
                "no,uppercase1!",  // Нет заглавной буквы
                "NOLOWERCASE1!",   // Нет строчной буквы
                "NoNumbers!",      // Нет цифры
                "     ",           // Только пробелы
                "",                // Пустая строка
                "1234567890",      // Только цифры
                "AllLowercase!",   // Только строчные буквы и символ
                "ALLUPPERCASE1!",  // Только заглавные буквы и цифры
        };

        for (String password : invalidPasswords) {
            assertThat(PASSWORD_PATTERN.matcher(password).matches())
                    .as("Expected password '%s' to NOT match the pattern", password)
                    .isFalse();
        }
    }
}
