package app.services;

import app.models.User;
import app.repositories.AuthRepository;
import app.utils.ValidationUtils;

public class AuthService {

    private AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean login(String email, String password) {
        if (!ValidationUtils.isNotEmpty(email))
            throw new IllegalArgumentException(ValidationUtils.ErrorMessages.EMPTY_FIELD);

        if (!ValidationUtils.isValidEmail(email))
            throw new IllegalArgumentException(ValidationUtils.ErrorMessages.INVALID_EMAIL);

        if (!ValidationUtils.isValidPassword(password))
            throw new IllegalArgumentException(ValidationUtils.ErrorMessages.INVALID_PASSWORD);

        User user = authRepository.findByEmailAndPassword(email, password);

        if (user == null)
            throw new IllegalArgumentException("Invalide User");
        authRepository.updateLoggedInStatus(user.getId(), true);
        return true;
    }

    public void logout(long userId) {

        if (!ValidationUtils.isValidUserId(userId))
            throw new IllegalArgumentException(ValidationUtils.ErrorMessages.USER_ID_INVALID);
        User user = authRepository.findById(userId);
        if (user == null)
            throw new IllegalArgumentException(ValidationUtils.ErrorMessages.USER_NOT_FOUND);

        authRepository.updateLoggedInStatus(userId, false);
    }

    public boolean isUserLoggedIn(long userId) {
        if (!ValidationUtils.isValidUserId(userId)) {
            return false;
        }
        return authRepository.isUserLoggedIn(userId);
    }
}