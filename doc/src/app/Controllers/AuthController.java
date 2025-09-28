package app.Controllers;

import app.services.AuthService;
import app.models.User;

public class AuthController {
    private final AuthService authService;

    // ✅ AuthController ne connaît QUE AuthService et gère ses dépendances
    public AuthController(){
        this.authService = new AuthService();
    }

    public User authenticate(String username, String password){
        User user = authService.login(username, password);
        
        switch (user.getRole()) {
            case Admin:
                return user; // dashAdmin
            case Treller:
                return user; // dashTreller
            case Auditor:
                return user; // dashAuditor
            case Manager:
                return user; // dashManager
            default:
                return user; // loginMenu
        }
    }

    public void logout(long userId){
        authService.logout(userId);
    }
    
    public boolean getSession(long userId){
        return authService.isUserLoggedIn(userId);
    }
}
