package app.repositories.interfaces;

import app.models.Credit;
import java.util.List;

/**
 * Interface pour les opérations CRUD sur Credit
 * Essentielle pour TELLER : initier demandes de crédit et consulter crédits clients
 * Pas de gestion d'échéances (responsabilité Manager)
 */
public interface CreditInterface {
    
    /**
     * Enregistrer une nouvelle demande de crédit initiée par TELLER
     * @param credit La demande de crédit à sauvegarder
     * @return Credit sauvegardé avec ID généré
     */
    Credit save(Credit credit);
    
    /**
     * Lister tous les crédits associés à un compte (lecture seule pour TELLER)
     * Permet au TELLER de voir l'historique des crédits d'un client
     * @param accountId L'ID du compte
     * @return Liste des crédits du compte
     */
    List<Credit> findByAccountId(long accountId);
}