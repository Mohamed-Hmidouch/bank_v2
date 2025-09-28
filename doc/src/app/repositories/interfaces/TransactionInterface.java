package app.repositories.interfaces;

import app.models.Transaction;
import java.util.List;

/**
 * Interface pour les opérations CRUD sur Transaction
 * CRUCIAL pour TELLER : enregistrement de toutes les opérations financières
 */
public interface TransactionInterface {
    
    /**
     * Enregistrer une transaction (dépôt, retrait, virement)
     * OBLIGATOIRE pour chaque opération financière du TELLER
     * @param transaction La transaction à sauvegarder
     * @return Transaction sauvegardée avec ID généré
     */
    Transaction save(Transaction transaction);
    
    /**
     * Récupérer l'historique des transactions d'un compte
     * Optionnel mais utile pour consultation historique simple
     * @param accountId L'ID du compte
     * @return Liste des transactions du compte
     */
    List<Transaction> findByAccountId(long accountId);
}