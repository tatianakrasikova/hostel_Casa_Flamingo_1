package ait.cohort49.hostel_casa_flamingo.service.mapping;

public abstract class UserEmailUtils {

    private UserEmailUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Normalizes the provided user email by converting it to lowercase and trimming
     * any leading or trailing whitespace.
     *
     * @param userEmail the email of the user to be normalized; can be null
     * @return the normalized email as a lowercase, trimmed string,
     *         or null if the input is null
     */
    public static String normalizeUserEmail(String userEmail) {
        if (userEmail == null) {
            return null;
        }
        return userEmail.toLowerCase().trim();
    }
}
