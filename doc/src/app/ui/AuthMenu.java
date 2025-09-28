package app.ui;

import app.Controllers.AuthController;
import app.Controllers.TellerController;
import app.models.User;
import app.ui.TellerDashboard;
import app.utils.ValidationUtils;
import java.util.Scanner;

public class AuthMenu {
    private Scanner scanner = new Scanner(System.in);
    private AuthController authController;

    public AuthMenu(AuthController authController) {
        this.authController = authController;
    }

    public void showLoginMenu() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("    MENU D'AUTHENTIFICATION");
            System.out.println("=".repeat(40));
            System.out.println("1. Se connecter");
            System.out.println("2. Quitter");
            System.out.println("=".repeat(40));
            System.out.print("Votre choix: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                
                switch (choice) {
                    case 1:
                        performLogin();
                        break;
                    case 2:
                        running = false;
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide!");
            }
        }
    }
    
    private void performLogin() {
        System.out.println("\n" + "=".repeat(30));
        System.out.println("        CONNEXION");
        System.out.println("=".repeat(30));
        
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        
        // Utiliser ValidationUtils au lieu de validation manuelle
        if (!ValidationUtils.isNotEmpty(email)) {
            System.out.println("❌ Email obligatoire!");
            return;
        }
        
        if (!ValidationUtils.isValidEmail(email)) {
            System.out.println("❌ Email invalide!");
            return;
        }
        
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine().trim();
        
        if (!ValidationUtils.isNotEmpty(password)) {
            System.out.println("❌ Mot de passe obligatoire!");
            return;
        }
        
        try {
            // AuthController retourne User maintenant, pas boolean
            User user = authController.authenticate(email, password);
            System.out.println("✅ Connexion réussie!");
            System.out.println("   Email: " + email);
            System.out.println("   Rôle: " + user.getRole());
            
            // Redirection selon le rôle
            redirectToRoleDashboard(user);
            
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la connexion: " + e.getMessage());
        }
    }
    
    /**
     * Redirection vers le bon dashboard selon le rôle
     */
    private void redirectToRoleDashboard(User user) {
        switch (user.getRole()) {
            case Treller:
                System.out.println("\n🏦 Accès au Dashboard TELLER...");
                TellerDashboard tellerDashboard = new TellerDashboard();
                tellerDashboard.showDashboard();
                break;
            case Admin:
                System.out.println("\n🔐 Accès Dashboard ADMIN (non implémenté)");
                break;
            case Manager:
                System.out.println("\n💼 Accès Dashboard MANAGER (non implémenté)");
                break;
            case Auditor:
                System.out.println("\n📊 Accès Dashboard AUDITEUR (non implémenté)");
                break;
            default:
                System.out.println("❌ Rôle inconnu: " + user.getRole());
        }
    }
}