package app.Controllers;

import app.services.AuthService;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    public boolean authenticate(String username, String password){
        return authService.login(username, password);
    }

    public void logout(long userId){
        authService.logout(userId);
    }
    
    public boolean getSession(long userId){
        return authService.isUserLoggedIn(userId);
    }
}
