package app;

import app.repositories.AuthRepository;
import app.Controllers.AuthController;
import app.services.AuthService;
import app.ui.AuthMenu;
import app.utils.DatabaseConnection;

public class Main {
    public static void main(String[] args) {

        // Repositories
        final AuthRepository authRepository = new AuthRepository();

        // Services
        final AuthService authService = new AuthService(authRepository);
        
        // Controllers
        final AuthController authController = new AuthController(authService);

        // Test database connection
        DatabaseConnection.getConnection();
        
        // Start simplified auth menu
        final AuthMenu authMenu = new AuthMenu(authController);
        authMenu.showLoginMenu();
        
        System.exit(0);
    }
}
