package app.Controllers;

import app.services.TellerService;
import java.math.BigDecimal;

/**
 * CONTRÔLEUR TELLER : Gestion des validations techniques d'entrée
 * RESPONSABILITÉ : Validation format + sanitisation + appel service
 * 
 * ARCHITECTURE PATTERN : Controller gère ses dépendances
 * - TellerService : Délégation logique métier
 */
public class TellerController {

    // Pattern imposé : Controller gère ses dépendances
    private final TellerService tellerService;

    public TellerController() {
        this.tellerService = new TellerService();
    }

    /**
     * USE CASE 1 : Créer client avec premier compte
     * VALIDATION TECHNIQUE : Format, null checks, sanitisation
     */
    public boolean createClientWithFirstAccount(String nom, String prenom, String email, 
                                              String telephone, String typeCompte, BigDecimal soldeInitial) {
        try {            
            if (nom == null || nom.trim().isEmpty()) {
                throw new IllegalArgumentException("Le nom est obligatoire.");
            }
            if (prenom == null || prenom.trim().isEmpty()) {
                throw new IllegalArgumentException("Le prénom est obligatoire.");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("L'email est obligatoire.");
            }
            if (telephone == null || telephone.trim().isEmpty()) {
                throw new IllegalArgumentException("Le téléphone est obligatoire.");
            }
            if (typeCompte == null || typeCompte.trim().isEmpty()) {
                throw new IllegalArgumentException("Le type de compte est obligatoire.");
            }
            if (soldeInitial == null) {
                throw new IllegalArgumentException("Le solde initial est obligatoire.");
            }

            String nomClean = nom.trim();
            String prenomClean = prenom.trim();
            String emailClean = email.trim().toLowerCase();
            String telephoneClean = telephone.trim();
            String typeCompteClean = typeCompte.trim().toUpperCase();

            if (!emailClean.contains("@") || !emailClean.contains(".")) {
                throw new IllegalArgumentException("Format email invalide.");
            }

            if (telephoneClean.length() < 10) {
                throw new IllegalArgumentException("Le téléphone doit contenir au moins 10 caractères.");
            }

            if (!typeCompteClean.equals("COURANT") && !typeCompteClean.equals("EPARGNE")) {
                throw new IllegalArgumentException("Type de compte invalide. Utilisez COURANT ou EPARGNE.");
            }

            // ===== DÉLÉGATION SERVICE (VALIDATIONS MÉTIER) =====
            return tellerService.createClientWithFirstAccount(nomClean, prenomClean, emailClean, 
                                                            telephoneClean, typeCompteClean, soldeInitial);

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur validation : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Erreur système : " + e.getMessage());
            return false;
        }
    }

    /**
     * USE CASE 2 : Créer compte supplémentaire
     * VALIDATION TECHNIQUE : ClientId valide, type compte, solde
     */
    public boolean createAdditionalAccount(Long clientId, String typeCompte, BigDecimal soldeInitial) {
        try {
            // ===== VALIDATIONS TECHNIQUES =====
            if (clientId == null || clientId <= 0) {
                throw new IllegalArgumentException("ID client invalide.");
            }
            if (typeCompte == null || typeCompte.trim().isEmpty()) {
                throw new IllegalArgumentException("Le type de compte est obligatoire.");
            }
            if (soldeInitial == null) {
                throw new IllegalArgumentException("Le solde initial est obligatoire.");
            }

            // Sanitisation
            String typeCompteClean = typeCompte.trim().toUpperCase();
            
            // Validation type compte
            if (!typeCompteClean.equals("COURANT") && !typeCompteClean.equals("EPARGNE")) {
                throw new IllegalArgumentException("Type de compte invalide. Utilisez COURANT ou EPARGNE.");
            }

            // ===== DÉLÉGATION SERVICE =====
            return tellerService.createAdditionalAccount(clientId, typeCompteClean, soldeInitial);

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur validation : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Erreur système : " + e.getMessage());
            return false;
        }
    }

    /**
     * USE CASE 3 : Effectuer dépôt
     * VALIDATION TECHNIQUE : AccountId valide, montant positif
     */
    public boolean makeDeposit(Long accountId, BigDecimal montant) {
        try {
            // ===== VALIDATIONS TECHNIQUES =====
            if (accountId == null || accountId <= 0) {
                throw new IllegalArgumentException("ID compte invalide.");
            }
            if (montant == null) {
                throw new IllegalArgumentException("Le montant est obligatoire.");
            }
            if (montant.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Le montant doit être positif.");
            }

            // ===== DÉLÉGATION SERVICE =====
            return tellerService.makeDeposit(accountId, montant);

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur validation : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Erreur système : " + e.getMessage());
            return false;
        }
    }

    /**
     * USE CASE 4 : Effectuer retrait
     * VALIDATION TECHNIQUE : AccountId valide, montant positif
     */
    public boolean makeWithdrawal(Long accountId, BigDecimal montant) {
        try {
            // ===== VALIDATIONS TECHNIQUES =====
            if (accountId == null || accountId <= 0) {
                throw new IllegalArgumentException("ID compte invalide.");
            }
            if (montant == null) {
                throw new IllegalArgumentException("Le montant est obligatoire.");
            }
            if (montant.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Le montant doit être positif.");
            }

            // ===== DÉLÉGATION SERVICE =====
            return tellerService.makeWithdrawal(accountId, montant);

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur validation : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Erreur système : " + e.getMessage());
            return false;
        }
    }

    /**
     * USE CASE 5 : Virement interne
     * VALIDATION TECHNIQUE : 2 comptes différents, montant positif
     */
    public boolean makeInternalTransfer(Long compteSource, Long compteDestination, BigDecimal montant) {
        try {
            // ===== VALIDATIONS TECHNIQUES =====
            if (compteSource == null || compteSource <= 0) {
                throw new IllegalArgumentException("ID compte source invalide.");
            }
            if (compteDestination == null || compteDestination <= 0) {
                throw new IllegalArgumentException("ID compte destination invalide.");
            }
            if (compteSource.equals(compteDestination)) {
                throw new IllegalArgumentException("Les comptes source et destination doivent être différents.");
            }
            if (montant == null) {
                throw new IllegalArgumentException("Le montant est obligatoire.");
            }
            if (montant.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Le montant doit être positif.");
            }

            // ===== DÉLÉGATION SERVICE =====
            return tellerService.makeInternalTransfer(compteSource, compteDestination, montant);

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur validation : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Erreur système : " + e.getMessage());
            return false;
        }
    }

    /**
     * USE CASE 6 : Demande de crédit
     * VALIDATION TECHNIQUE : AccountId valide, montant/taux/durée positifs
     */
    public boolean requestCredit(Long accountId, BigDecimal montant, BigDecimal taux, int dureeMois) {
        try {
            // ===== VALIDATIONS TECHNIQUES =====
            if (accountId == null || accountId <= 0) {
                throw new IllegalArgumentException("ID compte invalide.");
            }
            if (montant == null) {
                throw new IllegalArgumentException("Le montant est obligatoire.");
            }
            if (montant.compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Le montant doit être positif.");
            }
            if (taux == null) {
                throw new IllegalArgumentException("Le taux est obligatoire.");
            }
            if (taux.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Le taux ne peut pas être négatif.");
            }
            if (dureeMois <= 0) {
                throw new IllegalArgumentException("La durée doit être positive.");
            }

            // ===== DÉLÉGATION SERVICE =====
            return tellerService.requestCredit(accountId, montant, taux, dureeMois);

        } catch (IllegalArgumentException e) {
            System.err.println("Erreur validation : " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Erreur système : " + e.getMessage());
            return false;
        }
    }
}