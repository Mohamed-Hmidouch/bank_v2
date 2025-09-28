package app.repositories.interfaces;

import app.models.Historique;

/**
 * Interface pour l'historisation des opérations bancaires
 * ESSENTIEL pour TELLER selon cahier des charges :
 * - Traçabilité de toutes les opérations effectuées
 * - Audit et conformité réglementaire
 * - Suivi des actions pour contrôle qualité
 */
public interface HistoriqueInterface {
    
    /**
     * Enregistrer une entrée d'historique pour traçabilité
     * OBLIGATOIRE après chaque opération TELLER (dépôt, retrait, virement, crédit)
     * @param historique L'entrée d'historique à sauvegarder
     * @return Historique sauvegardé avec ID généré
     */
    Historique save(Historique historique);
}