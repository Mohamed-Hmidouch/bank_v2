package app.repositories.interfaces;

import app.models.Statistique;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Interface pour la gestion des statistiques du système bancaire.
 * Définit les opérations pour calculer, sauvegarder et consulter les statistiques.
 */
public interface StatistiqueInterface {
    
    /**
     * Sauvegarde une nouvelle statistique dans la base de données.
     * @param statistique L'objet Statistique à sauvegarder
     * @throws RuntimeException si erreur de sauvegarde
     */
    void save(Statistique statistique);
    
    /**
     * Recherche une statistique par son identifiant unique.
     * @param id L'UUID de la statistique recherchée
     * @return Statistique trouvée ou null si inexistante
     */
    Statistique findById(UUID id);
    
    /**
     * Récupère toutes les statistiques créées par un auditeur spécifique.
     * @param auditorId L'UUID de l'auditeur
     * @return Liste des statistiques de l'auditeur, triées par date de calcul
     */
    List<Statistique> findByAuditorId(UUID auditorId);
    
    /**
     * Récupère les statistiques par type (ex: "TRANSACTIONS_MONTHLY", "ACCOUNTS_SUMMARY").
     * @param type Le type de statistique recherché
     * @return Liste des statistiques du type donné
     */
    List<Statistique> findByType(String type);
    
    /**
     * Récupère les statistiques calculées dans une période donnée.
     * @param dateDebut Date de début de la période
     * @param dateFin Date de fin de la période
     * @return Liste des statistiques dans la période
     */
    List<Statistique> findByDateCalculRange(LocalDate dateDebut, LocalDate dateFin);
    
    /**
     * Récupère la statistique la plus récente d'un type donné.
     * @param type Le type de statistique recherché
     * @return Statistique la plus récente ou null si aucune
     */
    Statistique findLatestByType(String type);
    
    /**
     * Récupère toutes les statistiques du système.
     * @return Liste complète des statistiques, triées par date récente
     */
    List<Statistique> findAll();
    
    /**
     * Met à jour une statistique existante.
     * @param statistique L'objet Statistique avec les nouvelles données
     * @throws RuntimeException si statistique inexistante
     */
    void update(Statistique statistique);
    
    /**
     * Supprime une statistique obsolète.
     * @param id L'UUID de la statistique à supprimer
     * @throws RuntimeException si statistique inexistante
     */
    void delete(UUID id);
    
    /**
     * Supprime toutes les statistiques anciennes d'un type donné.
     * @param type Le type de statistique à nettoyer
     * @param keepMostRecent Nombre de statistiques récentes à conserver
     * @throws RuntimeException si erreur de suppression
     */
    void deleteOldStatisticsByType(String type, int keepMostRecent);
    
    /**
     * Compte le nombre de statistiques par type.
     * @param type Le type de statistique à compter
     * @return Nombre de statistiques du type donné
     */
    long countByType(String type);
    
    /**
     * Vérifie si une statistique d'un type donné existe pour une date.
     * @param type Le type de statistique
     * @param dateCalcul La date de calcul à vérifier
     * @return true si une statistique existe, false sinon
     */
    boolean existsByTypeAndDate(String type, LocalDate dateCalcul);
}