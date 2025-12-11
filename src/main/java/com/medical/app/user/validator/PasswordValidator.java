package com.medical.app.user.validator;

import com.medical.app.user.exception.InvalidPasswordException;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {

    public void validatePasswordStrength(String password) {
        if (!isLongEnough(password)) throw new InvalidPasswordException("Password too short! Eight characters or more!");
        if (!hasUppercase(password)) throw new InvalidPasswordException("Password needs at least one uppercase letter!");
        if (!hasLowercase(password)) throw new InvalidPasswordException("Password needs at least one lowercase letter!");
        if (!hasDigit(password)) throw new InvalidPasswordException("Password needs at least one digit!");
        if (!hasSpecialCharacter(password)) throw new InvalidPasswordException("Password needs at least one special character from @$!%*?&#");
    }

    private boolean isLongEnough(String password) {
        return password.length() >= 8;
    }

    private boolean hasUppercase(String password) {
        return password.matches(".*[A-Z].*");
    }

    private boolean hasLowercase(String password) {
        return password.matches(".*[a-z].*");
    }

    private boolean hasDigit(String password) {
        return password.matches(".*[0-9].*");
    }

    private boolean hasSpecialCharacter(String password) {
        return password.matches(".*[@$!%*?&#].*");
    }
}
