package account.validator.user;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class ValidateUserPassword {
    public static String getMessage(String password, String oldPassword, PasswordEncoder encoder) {
        if (checkIfPasswordIsBreached(password)) {
            return "The password is in the hacker's database!";
        }

        if (password.length() < 12) {
            return "Password length must be 12 chars minimum!";
        }

        if (!oldPassword.isEmpty()
                && encoder.matches(password, oldPassword)) {
            return "The passwords must be different!";
        }

        return "";
    }

    /**
     * For testing only
     */
    private static boolean checkIfPasswordIsBreached(String password) {
        List<String> breachedPassword = List.of("PasswordForJanuary", "PasswordForFebruary", "PasswordForMarch", "PasswordForApril",
                "PasswordForMay", "PasswordForJune", "PasswordForJuly", "PasswordForAugust",
                "PasswordForSeptember", "PasswordForOctober", "PasswordForNovember", "PasswordForDecember");

        return breachedPassword.contains(password);
    }
}
