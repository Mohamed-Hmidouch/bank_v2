package app.ui;

import app.Controllers.AuthController;
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
        
        if (email.isEmpty()) {
            System.out.println("❌ Email obligatoire!");
            return;
        }
        
        System.out.print("Mot de passe: ");
        String password = scanner.nextLine().trim();
        
        if (password.isEmpty()) {
            System.out.println("❌ Mot de passe obligatoire!");
            return;
        }
        
        try {
            boolean success = authController.authenticate(email, password);
            if (success) {
                System.out.println("✅ Connexion réussie!");
                System.out.println("   Email: " + email);
            } else {
                System.out.println("❌ Email ou mot de passe incorrect!");
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la connexion: " + e.getMessage());
        }
    }
}