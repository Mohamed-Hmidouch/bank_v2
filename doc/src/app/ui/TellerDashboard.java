package app.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Interface utilisateur console pour les opérations TELLER
 * Affiche uniquement les cas d'usage autorisés pour le rôle TELLER
 */
public class TellerDashboard {
    
    private final Scanner scanner;
    
    public TellerDashboard() {
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Affiche le menu principal TELLER après authentification
     */
    public void showDashboard() {
        boolean continueSession = true;
        
        System.out.println("\nBienvenue dans le Dashboard TELLER");
        
        while (continueSession) {
            displayMenu();
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    createClientWithFirstAccount();
                    break;
                case 2:
                    createAdditionalAccount();
                    break;
                case 3:
                    makeDeposit();
                    break;
                case 4:
                    makeWithdrawal();
                    break;
                case 5:
                    makeInternalTransfer();
                    break;
                case 6:
                    requestCredit();
                    break;
                case 0:
                    System.out.println("Déconnexion du dashboard TELLER...");
                    continueSession = false;
                    break;
                default:
                    System.out.println("Option invalide ! Veuillez choisir entre 0 et 6.");
            }
        }
    }
    
    /**
     * Affiche le menu des options TELLER
     */
    private void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("            DASHBOARD TELLER");
        System.out.println("=".repeat(50));
        System.out.println("1. Créer client avec premier compte");
        System.out.println("2. Créer compte supplémentaire");
        System.out.println("3. Effectuer dépôt client");
        System.out.println("4. Effectuer retrait client");
        System.out.println("5. Virement interne client");
        System.out.println("6. Demande de crédit");
        System.out.println("0. Déconnexion");
        System.out.println("=".repeat(50));
        System.out.print("Votre choix: ");
    }
    
    /**
     * Récupère et valide le choix utilisateur
     */
    private int getMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Choix invalide
        }
    }
    
    /**
     * USE CASE 1: Créer un nouveau client avec son premier compte
     */
    private void createClientWithFirstAccount() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("     CRÉATION CLIENT + PREMIER COMPTE");
        System.out.println("=".repeat(40));
        
        // Saisie informations client
        System.out.print("Nom du client: ");
        String nom = scanner.nextLine().trim();
        if (nom.isEmpty()) {
            System.out.println("Le nom est obligatoire !");
            return;
        }
        
        System.out.print("Prénom du client: ");
        String prenom = scanner.nextLine().trim();
        if (prenom.isEmpty()) {
            System.out.println("Le prénom est obligatoire !");
            return;
        }
        
        System.out.print("Email du client: ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty() || !email.contains("@")) {
            System.out.println("Email valide obligatoire !");
            return;
        }
        
        System.out.print("Téléphone du client: ");
        String telephone = scanner.nextLine().trim();
        if (telephone.isEmpty()) {
            System.out.println("Le téléphone est obligatoire !");
            return;
        }
        
        // Saisie informations compte
        System.out.print("Type de compte (COURANT/EPARGNE): ");
        String typeCompte = scanner.nextLine().trim().toUpperCase();
        if (!typeCompte.equals("COURANT") && !typeCompte.equals("EPARGNE")) {
            System.out.println("Type de compte invalide ! (COURANT ou EPARGNE)");
            return;
        }
        
        System.out.print("Solde initial (€): ");
        BigDecimal soldeInitial;
        try {
            soldeInitial = new BigDecimal(scanner.nextLine().trim());
            if (soldeInitial.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Le solde initial ne peut pas être négatif !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Montant invalide !");
            return;
        }
        
        // TODO: Appel au controller/service
        System.out.println("Client et compte créés avec succès !");
        System.out.println("   Client: " + prenom + " " + nom);
        System.out.println("   Email: " + email);
        System.out.println("   Compte " + typeCompte + " avec solde: " + soldeInitial + "€");
    }
    
    /**
     * USE CASE 2: Créer un compte supplémentaire pour un client existant
     */
    private void createAdditionalAccount() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        CRÉATION COMPTE SUPPLÉMENTAIRE");
        System.out.println("=".repeat(40));
        
        System.out.print("ID du client: ");
        long clientId;
        try {
            clientId = Long.parseLong(scanner.nextLine().trim());
            if (clientId <= 0) {
                System.out.println("ID client invalide !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID client invalide !");
            return;
        }
        
        System.out.print("Type de compte (COURANT/EPARGNE): ");
        String typeCompte = scanner.nextLine().trim().toUpperCase();
        if (!typeCompte.equals("COURANT") && !typeCompte.equals("EPARGNE")) {
            System.out.println("Type de compte invalide ! (COURANT ou EPARGNE)");
            return;
        }
        
        System.out.print("Solde initial (€): ");
        BigDecimal soldeInitial;
        try {
            soldeInitial = new BigDecimal(scanner.nextLine().trim());
            if (soldeInitial.compareTo(BigDecimal.ZERO) < 0) {
                System.out.println("Le solde initial ne peut pas être négatif !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Montant invalide !");
            return;
        }
        
        // TODO: Appel au controller/service
        System.out.println("Compte supplémentaire créé avec succès !");
        System.out.println("   Client ID: " + clientId);
        System.out.println("   Nouveau compte " + typeCompte + " avec solde: " + soldeInitial + "€");
    }
    
    /**
     * USE CASE 3: Effectuer un dépôt sur un compte
     */
    private void makeDeposit() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           EFFECTUER DÉPÔT CLIENT");
        System.out.println("=".repeat(40));
        
        System.out.print("ID du compte client: ");
        long accountId;
        try {
            accountId = Long.parseLong(scanner.nextLine().trim());
            if (accountId <= 0) {
                System.out.println("ID compte invalide !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID compte invalide !");
            return;
        }
        
        System.out.print("Montant du dépôt (€): ");
        BigDecimal montantDepot;
        try {
            montantDepot = new BigDecimal(scanner.nextLine().trim());
            if (montantDepot.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Le montant doit être positif !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Montant invalide !");
            return;
        }
        
        // TODO: Appel au controller/service
        System.out.println("Dépôt client effectué avec succès !");
        System.out.println("   Compte ID: " + accountId);
        System.out.println("   Montant déposé: " + montantDepot + "€");
    }
    
    /**
     * USE CASE 4: Effectuer un retrait sur un compte
     */
    private void makeWithdrawal() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           EFFECTUER RETRAIT CLIENT");
        System.out.println("=".repeat(40));
        
        System.out.print("ID du compte client: ");
        long accountId;
        try {
            accountId = Long.parseLong(scanner.nextLine().trim());
            if (accountId <= 0) {
                System.out.println("ID compte invalide !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID compte invalide !");
            return;
        }
        
        System.out.print("Montant du retrait (€): ");
        BigDecimal montantRetrait;
        try {
            montantRetrait = new BigDecimal(scanner.nextLine().trim());
            if (montantRetrait.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Le montant doit être positif !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Montant invalide !");
            return;
        }
        
        // TODO: Appel au controller/service
        System.out.println("Retrait client effectué avec succès !");
        System.out.println("   Compte ID: " + accountId);
        System.out.println("   Montant retiré: " + montantRetrait + "€");
        System.out.println("   (Note: Validation du solde suffisant nécessaire)");
    }
    
    /**
     * USE CASE 5: Effectuer un virement interne entre comptes
     */
    private void makeInternalTransfer() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("          VIREMENT INTERNE CLIENT");
        System.out.println("=".repeat(40));
        
        System.out.print("ID du compte client source (débit): ");
        long compteSource;
        try {
            compteSource = Long.parseLong(scanner.nextLine().trim());
            if (compteSource <= 0) {
                System.out.println("ID compte source invalide !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID compte source invalide !");
            return;
        }
        
        System.out.print("ID du compte client destination (crédit): ");
        long compteDestination;
        try {
            compteDestination = Long.parseLong(scanner.nextLine().trim());
            if (compteDestination <= 0) {
                System.out.println("ID compte destination invalide !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID compte destination invalide !");
            return;
        }
        
        if (compteSource == compteDestination) {
            System.out.println("Les comptes source et destination doivent être différents !");
            return;
        }
        
        System.out.print("Montant du virement (€): ");
        BigDecimal montantVirement;
        try {
            montantVirement = new BigDecimal(scanner.nextLine().trim());
            if (montantVirement.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Le montant doit être positif !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Montant invalide !");
            return;
        }
        
        // TODO: Appel au controller/service
        System.out.println("Virement interne client effectué avec succès !");
        System.out.println("   Compte source: " + compteSource);
        System.out.println("   Compte destination: " + compteDestination);
        System.out.println("   Montant: " + montantVirement + "€");
    }
    
    /**
     * USE CASE 6: Initier une demande de crédit
     */
    private void requestCredit() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            DEMANDE DE CRÉDIT");
        System.out.println("=".repeat(40));
        
        System.out.print("ID du compte bénéficiaire: ");
        long accountId;
        try {
            accountId = Long.parseLong(scanner.nextLine().trim());
            if (accountId <= 0) {
                System.out.println("ID compte invalide !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("ID compte invalide !");
            return;
        }
        
        System.out.print("Montant demandé (€): ");
        BigDecimal montantCredit;
        try {
            montantCredit = new BigDecimal(scanner.nextLine().trim());
            if (montantCredit.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Le montant doit être positif !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Montant invalide !");
            return;
        }
        
        System.out.print("Type de crédit (PERSONNEL/IMMOBILIER/AUTO): ");
        String typeCredit = scanner.nextLine().trim().toUpperCase();
        if (!typeCredit.equals("PERSONNEL") && !typeCredit.equals("IMMOBILIER") && !typeCredit.equals("AUTO")) {
            System.out.println("Type de crédit invalide ! (PERSONNEL, IMMOBILIER ou AUTO)");
            return;
        }
        
        System.out.print("Taux d'intérêt (%): ");
        BigDecimal taux;
        try {
            taux = new BigDecimal(scanner.nextLine().trim());
            if (taux.compareTo(BigDecimal.ZERO) < 0 || taux.compareTo(new BigDecimal("100")) > 0) {
                System.out.println("Le taux doit être entre 0 et 100% !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Taux invalide !");
            return;
        }
        
        System.out.print("Durée en mois: ");
        int dureeMois;
        try {
            dureeMois = Integer.parseInt(scanner.nextLine().trim());
            if (dureeMois <= 0 || dureeMois > 360) { // Maximum 30 ans
                System.out.println("La durée doit être entre 1 et 360 mois !");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Durée invalide !");
            return;
        }
        
        // TODO: Appel au controller/service
        System.out.println("Demande de crédit initiée avec succès !");
        System.out.println("   Compte bénéficiaire: " + accountId);
        System.out.println("   Montant: " + montantCredit + "€");
        System.out.println("   Type: " + typeCredit);
        System.out.println("   Taux: " + taux + "%");
        System.out.println("   Durée: " + dureeMois + " mois");
        System.out.println("   Statut: EN ATTENTE D'APPROBATION (Manager)");
    }
}