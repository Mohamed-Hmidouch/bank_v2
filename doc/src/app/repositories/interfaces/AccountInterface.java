package app.repositories.interfaces;

import app.models.Account;
import java.math.BigDecimal;
import java.util.List;

/**
 * Interface pour les opérations CRUD sur Account
 * ESSENTIEL pour TELLER : consultation et mise à jour soldes
 */
public interface AccountInterface {
    
    /**
     * Sauvegarder un nouveau compte dans la base de données.
     * Utilisé par TELLER lors de création client avec premier compte
     * @param account L'objet Account à sauvegarder
     * @throws RuntimeException si erreur de sauvegarde
     */
    void save(Account account);
    
    /**
     * Consulter un compte par son ID
     * Utilisé par TELLER pour vérifier solde avant opérations
     * @param id L'ID du compte
     * @return Le compte trouvé ou null si inexistant
     */
    Account findById(Long id);
    
    /**
     * Rechercher tous les comptes d'un client spécifique.
     * ESSENTIEL pour TELLER : création compte supplémentaire, virement interne
     * @param clientId L'ID du client propriétaire
     * @return Liste des comptes du client (peut être vide)
     */
    List<Account> findByClientId(Long clientId);
    
    /**
     * Mettre à jour un compte (principalement le solde)
     * CRITIQUE après dépôt/retrait/virement pour maintenir cohérence
     * @param account Le compte avec nouvelles données
     * @return true si mise à jour réussie
     */
    boolean update(Account account);
    
    /**
     * Mettre à jour uniquement le solde d'un compte (optimisé).
     * Méthode rapide pour les opérations TELLER fréquentes
     * @param accountId L'ID du compte
     * @param nouveauSolde Le nouveau solde
     * @throws RuntimeException si compte inexistant ou solde invalide
     */
    void updateSolde(Long accountId, BigDecimal nouveauSolde);
    
    /**
     * Vérifier si un compte est actif (autorise les opérations).
     * Validation OBLIGATOIRE avant toute opération TELLER
     * @param accountId L'ID du compte à vérifier
     * @return true si compte actif, false sinon
     */
    boolean isAccountActive(Long accountId);
}