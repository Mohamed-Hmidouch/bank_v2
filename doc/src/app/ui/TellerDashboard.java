package app.ui;

import app.Controllers.TellerController;
import app.models.Enums.CreditType;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Interface utilisateur console pour les opérations TELLER
 * Affiche uniquement les cas d'usage autorisés pour le rôle TELLER
 */
public class TellerDashboard {

    private final Scanner scanner;
    private final TellerController tellerController;

    public TellerDashboard() {
        this.scanner = new Scanner(System.in);
        this.tellerController = new TellerController();
    }

    public void showDashboard() {
        boolean continueSession = true;
        System.out.println("\nBienvenue dans le Dashboard TELLER");
        while (continueSession) {
            displayMenu();
            int choice = getMenuChoice();
            switch (choice) {
                case 1: createClientWithFirstAccount(); break;
                case 2: createAdditionalAccount(); break;
                case 3: makeDeposit(); break;
                case 4: makeWithdrawal(); break;
                case 5: makeInternalTransfer(); break;
                case 6: requestCredit(); break;
                case 0:
                    System.out.println("Déconnexion du dashboard TELLER...");
                    continueSession = false;
                    break;
                default:
                    System.out.println("Option invalide ! Veuillez choisir entre 0 et 6.");
            }
        }
    }

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

    private int getMenuChoice() {
        try { return Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }

    private void createClientWithFirstAccount() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("     CRÉATION CLIENT + PREMIER COMPTE");
        System.out.println("=".repeat(40));

        System.out.print("Nom du client: ");
        String nom = scanner.nextLine().trim();
        System.out.print("Prénom du client: ");
        String prenom = scanner.nextLine().trim();
        System.out.print("Email du client: ");
        String email = scanner.nextLine().trim();
        System.out.print("Téléphone du client: ");
        String telephone = scanner.nextLine().trim();
    System.out.println("Type de compte : 1 = COURANT, 2 = EPARGNE");
    System.out.print("Votre choix (1/2): ");
    String typeChoice = scanner.nextLine().trim();
    String typeCompte;
    if ("1".equals(typeChoice)) typeCompte = "COURANT";
    else if ("2".equals(typeChoice)) typeCompte = "EPARGNE";
    else { System.out.println("Choix de type invalide !"); return; }
        System.out.print("Solde initial (€): ");
        BigDecimal soldeInitial;
        try { soldeInitial = new BigDecimal(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Montant invalide !"); return; }

        boolean success = tellerController.createClientWithFirstAccount(nom, prenom, email, telephone, typeCompte, soldeInitial);
        if (success) {
            System.out.println("Client et compte créés avec succès !");
            System.out.println("   Client: " + prenom + " " + nom);
            System.out.println("   Email: " + email);
            System.out.println("   Compte " + typeCompte + " avec solde: " + soldeInitial + "€");
        } else {
            System.out.println("Échec de la création du client ou du compte.");
        }
    }

    private void createAdditionalAccount() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("        CRÉATION COMPTE SUPPLÉMENTAIRE");
        System.out.println("=".repeat(40));

        System.out.print("ID du client: ");
        long clientId;
        try { clientId = Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID client invalide !"); return; }

    System.out.println("Type de compte : 1 = COURANT, 2 = EPARGNE");
    System.out.print("Votre choix (1/2): ");
    String typeChoice = scanner.nextLine().trim();
    String typeCompte;
    if ("1".equals(typeChoice)) typeCompte = "COURANT";
    else if ("2".equals(typeChoice)) typeCompte = "EPARGNE";
    else { System.out.println("Choix de type invalide !"); return; }
        System.out.print("Solde initial (€): ");
        BigDecimal soldeInitial;
        try { soldeInitial = new BigDecimal(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Montant invalide !"); return; }

        boolean success = tellerController.createAdditionalAccount(clientId, typeCompte, soldeInitial);
        if (success) {
            System.out.println("Compte supplémentaire créé avec succès !");
        } else {
            System.out.println("Échec de la création du compte supplémentaire.");
        }
    }

    private void makeDeposit() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           EFFECTUER DÉPÔT CLIENT");
        System.out.println("=".repeat(40));

        System.out.print("ID du compte client: ");
        long accountId;
        try { accountId = Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID compte invalide !"); return; }

        System.out.print("Montant du dépôt (€): ");
        BigDecimal montantDepot;
        try { montantDepot = new BigDecimal(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Montant invalide !"); return; }

        boolean success = tellerController.makeDeposit(accountId, montantDepot);
        if (success) {
            System.out.println("Dépôt client effectué avec succès !");
        } else {
            System.out.println("Échec du dépôt client.");
        }
    }

    private void makeWithdrawal() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("           EFFECTUER RETRAIT CLIENT");
        System.out.println("=".repeat(40));

        System.out.print("ID du compte client: ");
        long accountId;
        try { accountId = Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID compte invalide !"); return; }

        System.out.print("Montant du retrait (€): ");
        BigDecimal montantRetrait;
        try { montantRetrait = new BigDecimal(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Montant invalide !"); return; }

        boolean success = tellerController.makeWithdrawal(accountId, montantRetrait);
        if (success) {
            System.out.println("Retrait client effectué avec succès !");
        } else {
            System.out.println("Échec du retrait client.");
        }
    }

    private void makeInternalTransfer() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("          VIREMENT INTERNE CLIENT");
        System.out.println("=".repeat(40));

        System.out.print("ID du compte client source (débit): ");
        long compteSource;
        try { compteSource = Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID compte source invalide !"); return; }

        System.out.print("ID du compte client destination (crédit): ");
        long compteDestination;
        try { compteDestination = Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID compte destination invalide !"); return; }

        System.out.print("Montant du virement (€): ");
        BigDecimal montantVirement;
        try { montantVirement = new BigDecimal(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Montant invalide !"); return; }

        boolean success = tellerController.makeInternalTransfer(compteSource, compteDestination, montantVirement);
        if (success) {
            System.out.println("Virement interne client effectué avec succès !");
        } else {
            System.out.println("Échec du virement interne client.");
        }
    }

    /**
     * USE CASE 6: Initier une demande de crédit, version ENUM (CONSTANT/DEGRESSIF)
     */
    private void requestCredit() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("            DEMANDE DE CRÉDIT");
        System.out.println("=".repeat(40));

        System.out.print("ID du compte bénéficiaire: ");
        long accountId;
        try { accountId = Long.parseLong(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("ID compte invalide !"); return; }

        System.out.print("Montant demandé (€): ");
        BigDecimal montantCredit;
        try { montantCredit = new BigDecimal(scanner.nextLine().trim()); }
        catch (NumberFormatException e) { System.out.println("Montant invalide !"); return; }

        System.out.print("Type de crédit (CONSTANT/DEGRESSIF): ");
        String typeCreditStr = scanner.nextLine().trim().toUpperCase();
        if (!typeCreditStr.equals("CONSTANT") && !typeCreditStr.equals("DEGRESSIF")) {
            System.out.println("Type de crédit invalide ! (CONSTANT ou DEGRESSIF)");
            return;
        }
        CreditType typeCredit = CreditType.valueOf(typeCreditStr);

        System.out.print("Taux d'intérêt (%): ");
        BigDecimal taux;
        try {
            taux = new BigDecimal(scanner.nextLine().trim());
            if (taux.compareTo(BigDecimal.ZERO) < 0 || taux.compareTo(new BigDecimal("100")) > 0) {
                System.out.println("Le taux doit être entre 0 et 100% !");
                return;
            }
        } catch (NumberFormatException e) { System.out.println("Taux invalide !"); return; }

        System.out.print("Durée en mois: ");
        int dureeMois;
        try {
            dureeMois = Integer.parseInt(scanner.nextLine().trim());
            if (dureeMois <= 0 || dureeMois > 360) {
                System.out.println("La durée doit être entre 1 et 360 mois !");
                return;
            }
        } catch (NumberFormatException e) { System.out.println("Durée invalide !"); return; }

        boolean success = tellerController.requestCredit(accountId, montantCredit, taux, dureeMois, typeCredit);
        if (success) {
            System.out.println("Demande de crédit initiée avec succès !");
            System.out.println("   Compte bénéficiaire: " + accountId);
            System.out.println("   Montant: " + montantCredit + "€");
            System.out.println("   Type: " + typeCredit);
            System.out.println("   Taux: " + taux + "%");
            System.out.println("   Durée: " + dureeMois + " mois");
            System.out.println("   Statut: EN ATTENTE D'APPROBATION (Manager)");
        } else {
            System.out.println("Échec de l'initiation de la demande de crédit.");
        }
    }
}