package app;

import app.Controllers.AuthController;
import app.ui.AuthMenu;

public class Main {
    public static void main(String[] args) {

        // ✅ Main ne connaît QUE le contrôleur
        AuthController authController = new AuthController();
        
        // Start simplified auth menu
        final AuthMenu authMenu = new AuthMenu(authController);
        authMenu.showLoginMenu();
        
        System.exit(0);
    }
}
