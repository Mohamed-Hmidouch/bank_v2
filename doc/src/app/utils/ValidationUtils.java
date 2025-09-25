package app.utils;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Classe utilitaire pour centraliser toutes les validations
 * Suit les bonnes pratiques définies dans les instructions Copilot
 */
public class ValidationUtils {
    
    // Patterns de validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
    
    private static final int MIN_PASSWORD_LENGTH = 6;
    
    /**
     * Valide la longueur minimale d'un mot de passe
     * @param password Le mot de passe à valider
     * @return true si valide, false sinon
     */
    public static boolean isValidPassword(String password) {
        return password != null && password.trim().length() >= MIN_PASSWORD_LENGTH;
    }
    
    /**
     * Valide le format d'un email
     * @param email L'email à valider
     * @return true si valide, false sinon
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Valide qu'un montant est positif (règle métier bancaire)
     * @param montant Le montant BigDecimal à valider
     * @return true si > 0, false sinon
     */
    public static boolean isValidMontant(BigDecimal montant) {
        return montant != null && montant.compareTo(BigDecimal.ZERO) > 0;
    }
    
    /**
     * Valide qu'un solde est non-négatif
     * @param solde Le solde BigDecimal à valider
     * @return true si >= 0, false sinon
     */
    public static boolean isValidSolde(BigDecimal solde) {
        return solde != null && solde.compareTo(BigDecimal.ZERO) >= 0;
    }
    
    /**
     * Valide le format UUID
     * @param uuidString La chaîne UUID à valider
     * @return true si format valide, false sinon
     */
    public static boolean isValidUUID(String uuidString) {
        if (uuidString == null || uuidString.trim().isEmpty()) {
            return false;
        }
        
        try {
            UUID.fromString(uuidString.trim());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
    
    /**
     * Valide qu'une chaîne n'est pas vide
     * @param value La valeur à valider
     * @return true si non-null et non-vide, false sinon
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    /**
     * Valide qu'un entier est dans une plage donnée
     * @param value La valeur à valider
     * @param min Valeur minimale (inclusive)
     * @param max Valeur maximale (inclusive)
     * @return true si dans la plage, false sinon
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
    
    /**
     * Valide qu'une chaîne représente un entier valide
     * @param numberString La chaîne à valider
     * @return true si entier valide, false sinon
     */
    public static boolean isValidInteger(String numberString) {
        if (numberString == null || numberString.trim().isEmpty()) {
            return false;
        }
        
        try {
            Integer.parseInt(numberString.trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Parse un entier de façon sécurisée
     * @param numberString La chaîne à parser
     * @param defaultValue Valeur par défaut si parsing échoue
     * @return L'entier parsé ou la valeur par défaut
     */
    public static int parseIntSafely(String numberString, int defaultValue) {
        if (!isValidInteger(numberString)) {
            return defaultValue;
        }
        
        try {
            return Integer.parseInt(numberString.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
    
    /**
     * Valide qu'une réponse de confirmation est positive
     * @param response La réponse utilisateur
     * @return true si confirmation positive, false sinon
     */
    public static boolean isPositiveConfirmation(String response) {
        if (response == null) {
            return false;
        }
        
        String normalized = response.trim().toLowerCase();
        return normalized.equals("o") || normalized.equals("oui") || 
               normalized.equals("y") || normalized.equals("yes");
    }
    
    // ==================== VALIDATIONS MÉTIER BANCAIRE ====================
    
    /**
     * Valide qu'un utilisateur ID est valide (long positif)
     * @param userId L'ID utilisateur à valider
     * @return true si > 0, false sinon
     */
    public static boolean isValidUserId(long userId) {
        return userId > 0;
    }
    
    /**
     * Valide qu'un solde est suffisant pour un retrait/virement
     * @param soldeActuel Le solde actuel du compte
     * @param montantOperation Le montant à débiter
     * @return true si solde suffisant, false sinon
     */
    public static boolean isSoldeSuffisant(BigDecimal soldeActuel, BigDecimal montantOperation) {
        if (soldeActuel == null || montantOperation == null) {
            return false;
        }
        return soldeActuel.compareTo(montantOperation) >= 0;
    }
    
    /**
     * Valide qu'un solde permet la clôture d'un compte (doit être = 0)
     * @param solde Le solde à vérifier
     * @return true si solde = 0, false sinon
     */
    public static boolean isClotureAutorisee(BigDecimal solde) {
        return solde != null && solde.compareTo(BigDecimal.ZERO) == 0;
    }
    
    /**
     * Valide le format d'un identifiant de compte bancaire (BK-XXXX-1234)
     * @param accountId L'identifiant compte à valider
     * @return true si format valide, false sinon
     */
    public static boolean isValidAccountId(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            return false;
        }
        
        // Pattern : BK-XXXX-XXXX (BK suivi de 2 groupes de 4 chiffres)
        Pattern accountPattern = Pattern.compile("^BK-\\d{4}-\\d{4}$");
        return accountPattern.matcher(accountId.trim()).matches();
    }
    
    /**
     * Valide qu'un montant respecte la précision bancaire (max 2 décimales)
     * @param montant Le montant BigDecimal à valider
     * @return true si scale <= 2, false sinon
     */
    public static boolean isValidPrecisionBancaire(BigDecimal montant) {
        return montant != null && montant.scale() <= 2;
    }
    
    /**
     * Valide qu'un type de rôle utilisateur est autorisé pour une opération
     * @param userRole Le rôle de l'utilisateur
     * @param operationsAutorisees Les rôles autorisés pour l'opération
     * @return true si autorisé, false sinon
     */
    public static boolean isOperationAutorisee(String userRole, String... operationsAutorisees) {
        if (userRole == null || operationsAutorisees == null) {
            return false;
        }
        
        for (String roleAutorise : operationsAutorisees) {
            if (userRole.equalsIgnoreCase(roleAutorise)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Valide qu'une description de transaction n'est pas trop longue
     * @param description La description à valider
     * @param maxLength Longueur maximale autorisée
     * @return true si <= maxLength, false sinon
     */
    public static boolean isValidTransactionDescription(String description, int maxLength) {
        if (description == null) {
            return true; // Description optionnelle
        }
        return description.length() <= maxLength;
    }
    
    /**
     * Valide qu'un compte est dans un état actif pour les opérations
     * @param isActive Statut du compte
     * @return true si actif, false sinon
     */
    public static boolean isCompteActif(boolean isActive) {
        return isActive;
    }
    
    /**
     * Valide qu'un utilisateur est propriétaire du compte (sécurité)
     * @param userId ID de l'utilisateur
     * @param compteUserId ID du propriétaire du compte
     * @return true si propriétaire, false sinon
     */
    public static boolean isProprietaireCompte(long userId, long compteUserId) {
        return userId > 0 && compteUserId > 0 && userId == compteUserId;
    }
    
    /**
     * Valide qu'une session utilisateur est valide (connecté)
     * @param user L'utilisateur à vérifier
     * @return true si connecté, false sinon
     */
    public static boolean isSessionValide(app.models.User user) {
        return user != null && user.isLoggedIn();
    }
    
    /**
     * Messages d'erreur standardisés pour les validations
     */
    public static class ErrorMessages {
        // Erreurs d'authentification
        public static final String INVALID_PASSWORD = "Le mot de passe doit contenir au moins " + MIN_PASSWORD_LENGTH + " caractères!";
        public static final String INVALID_EMAIL = "Format d'email invalide!";
        public static final String INVALID_UUID = "Format d'ID invalide! Utilisez un UUID valide.";
        public static final String EMPTY_FIELD = "Ce champ ne peut pas être vide!";
        public static final String INVALID_NUMBER = "Veuillez entrer un nombre valide!";
        public static final String PASSWORDS_NOT_MATCH = "Les mots de passe ne correspondent pas!";
        public static final String CURRENT_PASSWORD_INCORRECT = "Mot de passe actuel incorrect!";
        
        // Erreurs métier bancaires
        public static final String INVALID_MONTANT = "Le montant doit être positif!";
        public static final String SOLDE_INSUFFISANT = "Solde insuffisant pour cette opération!";
        public static final String CLOTURE_IMPOSSIBLE = "Impossible de clôturer un compte avec un solde non nul!";
        public static final String INVALID_ACCOUNT_ID = "Format d'identifiant compte invalide! (Format attendu: BK-XXXX-XXXX)";
        public static final String INVALID_PRECISION = "Le montant ne peut pas avoir plus de 2 décimales!";
        public static final String OPERATION_NON_AUTORISEE = "Vous n'êtes pas autorisé à effectuer cette opération!";
        public static final String DESCRIPTION_TROP_LONGUE = "La description est trop longue!";
        public static final String COMPTE_INACTIF = "Impossible d'effectuer des opérations sur un compte inactif!";
        public static final String ACCES_REFUSE = "Vous n'êtes pas propriétaire de ce compte!";
        public static final String SESSION_INVALIDE = "Votre session a expiré. Veuillez vous reconnecter!";
        public static final String USER_ID_INVALID = "ID utilisateur invalide!";
        public static final String USER_NOT_FOUND = "Utilisateur non trouvé!";
    }
}