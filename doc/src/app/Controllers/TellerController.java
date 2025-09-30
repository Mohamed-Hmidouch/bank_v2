package app.Controllers;

import app.services.TellerService;
import java.math.BigDecimal;

/**
 * CONTRÔLEUR TELLER : Thin Controller, délégation au service métier
 */
public class TellerController {

    private final TellerService tellerService;

    public TellerController() {
        this.tellerService = new TellerService();
    }

    private boolean validateString(String val) {
        return val != null && !val.trim().isEmpty();
    }

    private boolean handleException(Runnable r) {
        try {
            r.run();
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Erreur validation : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erreur système : " + e.getMessage());
        }
        return false;
    }

    public boolean createClientWithFirstAccount(String nom, String prenom, String email,
                                               String telephone, String typeCompte, BigDecimal soldeInitial) {
        return handleException(() -> {
            if (!validateString(nom) || !validateString(prenom) ||
                !validateString(email) || !validateString(telephone) ||
                !validateString(typeCompte) || soldeInitial == null)
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            if (!email.contains("@") || !email.contains("."))
                throw new IllegalArgumentException("Format email invalide.");
            if (telephone.length() < 10)
                throw new IllegalArgumentException("Le téléphone doit contenir au moins 10 caractères.");
            String type = typeCompte.trim().toUpperCase();
            if (!type.equals("COURANT") && !type.equals("EPARGNE"))
                throw new IllegalArgumentException("Type de compte invalide.");
            tellerService.createClientWithFirstAccount(
                nom.trim(), prenom.trim(), email.trim().toLowerCase(),
                telephone.trim(), type, soldeInitial
            );
        });
    }

    public boolean createAdditionalAccount(Long clientId, String typeCompte, BigDecimal soldeInitial) {
        return handleException(() -> {
            if (clientId == null || clientId <= 0 || !validateString(typeCompte) || soldeInitial == null)
                throw new IllegalArgumentException("Tous les champs sont obligatoires.");
            String type = typeCompte.trim().toUpperCase();
            if (!type.equals("COURANT") && !type.equals("EPARGNE"))
                throw new IllegalArgumentException("Type de compte invalide.");
            tellerService.createAdditionalAccount(clientId, type, soldeInitial);
        });
    }

    public boolean makeDeposit(Long accountId, BigDecimal montant) {
        return handleException(() -> {
            if (accountId == null || accountId <= 0 || montant == null || montant.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("ID compte invalide ou montant non positif.");
            tellerService.makeDeposit(accountId, montant);
        });
    }

    public boolean makeWithdrawal(Long accountId, BigDecimal montant) {
        return handleException(() -> {
            if (accountId == null || accountId <= 0 || montant == null || montant.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("ID compte invalide ou montant non positif.");
            tellerService.makeWithdrawal(accountId, montant);
        });
    }

    public boolean makeInternalTransfer(Long compteSource, Long compteDestination, BigDecimal montant) {
        return handleException(() -> {
            if (compteSource == null || compteSource <= 0 || compteDestination == null || compteDestination <= 0)
                throw new IllegalArgumentException("ID comptes invalides.");
            if (compteSource.equals(compteDestination))
                throw new IllegalArgumentException("Comptes source et destination doivent être différents.");
            if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0)
                throw new IllegalArgumentException("Montant non positif.");
            tellerService.makeInternalTransfer(compteSource, compteDestination, montant);
        });
    }

    public boolean requestCredit(Long accountId, Long creeBy, BigDecimal montant, BigDecimal taux, int dureeMois) {
        return handleException(() -> {
            if (accountId == null || accountId <= 0 || creeBy == null || creeBy <= 0 ||
                montant == null || montant.compareTo(BigDecimal.ZERO) <= 0 ||
                taux == null || taux.compareTo(BigDecimal.ZERO) < 0 || dureeMois <= 0)
                throw new IllegalArgumentException("Paramètres crédit invalides.");
            tellerService.requestCredit(accountId, creeBy, montant, taux, dureeMois);
        });
    }
}