package app.repositories.interfaces;

import app.models.Echeance;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Interface pour la gestion des échéances de crédit.
 * Définit les opérations pour le suivi et paiement des échéances.
 */
public interface EcheanceInterface {
    
    /**
     * Sauvegarde une nouvelle échéance dans la base de données.
     * @param echeance L'objet Echeance à sauvegarder
     * @throws RuntimeException si erreur de sauvegarde
     */
    void save(Echeance echeance);
    
    /**
     * Recherche une échéance par son identifiant unique.
     * @param id L'UUID de l'échéance recherchée
     * @return Echeance trouvée ou null si inexistante
     */
    Echeance findById(UUID id);
    
    /**
     * Récupère toutes les échéances d'un crédit spécifique.
     * @param creditId L'UUID du crédit
     * @return Liste des échéances du crédit, triées par numéro
     */
    List<Echeance> findByCreditId(UUID creditId);
    
    /**
     * Récupère les échéances non payées d'un crédit.
     * @param creditId L'UUID du crédit
     * @return Liste des échéances impayées
     */
    List<Echeance> findUnpaidByCreditId(UUID creditId);
    
    /**
     * Récupère les échéances échues (date dépassée et non payées).
     * @param dateReference La date de référence (généralement aujourd'hui)
     * @return Liste des échéances en retard
     */
    List<Echeance> findOverdueBefore(LocalDate dateReference);
    
    /**
     * Récupère les échéances à venir dans une période donnée.
     * @param dateDebut Date de début de la période
     * @param dateFin Date de fin de la période
     * @return Liste des échéances dans la période
     */
    List<Echeance> findUpcomingBetween(LocalDate dateDebut, LocalDate dateFin);
    
    /**
     * Met à jour une échéance (notamment pour marquer comme payée).
     * @param echeance L'objet Echeance avec les nouvelles données
     * @throws RuntimeException si échéance inexistante
     */
    void update(Echeance echeance);
    
    /**
     * Marque une échéance comme payée avec date de paiement.
     * @param echeanceId L'UUID de l'échéance
     * @param datePaiement La date effective du paiement
     * @throws RuntimeException si échéance inexistante ou déjà payée
     */
    void markAsPaid(UUID echeanceId, LocalDate datePaiement);
    
    /**
     * Calcule le montant total des échéances impayées pour un crédit.
     * @param creditId L'UUID du crédit
     * @return Montant total des échéances non payées
     */
    double getTotalUnpaidAmount(UUID creditId);
}