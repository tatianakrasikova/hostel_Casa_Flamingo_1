package ait.cohort49.hostel_casa_flamingo.constants;

public abstract class ValidationConstants {

    /**
     * Регулярное выражение для проверки пароля.
     * <p>
     * Требования:
     * <ul>
     *   <li>Хотя бы одна цифра</li>
     *   <li>Хотя бы одна строчная буква</li>
     *   <li>Хотя бы одна заглавная буква</li>
     *   <li>Хотя бы один специальный символ</li>
     *   <li>Минимальная длина пароля: 8 символов</li>
     * </ul>
     */
    public static final String PASSWORD_REGEX =
            "^(?=.*[0-9])" +
                    "(?=.*[a-z])" +
                    "(?=.*[A-Z])" +
                    "(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])"
                    + ".{8,}$";


}
