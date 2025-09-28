package app.repositories.interfaces;

import app.models.Rapport;
import app.models.Account;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Interface pour la gestion des rapports d'audit.
 * Définit les opérations pour créer, consulter et gérer les rapports.
 */
public interface RapportInterface {
    
    /**
     * Sauvegarde un nouveau rapport dans la base de données.
     * @param rapport L'objet Rapport à sauvegarder
     * @throws RuntimeException si erreur de sauvegarde
     */
    void save(Rapport rapport);
    
    /**
     * Recherche un rapport par son identifiant unique.
     * @param id L'UUID du rapport recherché
     * @return Rapport trouvé ou null si inexistant
     */
    Rapport findById(UUID id);
    
    /**
     * Récupère tous les rapports créés par un auditeur spécifique.
     * @param auditorId L'UUID de l'auditeur
     * @return Liste des rapports de l'auditeur, triés par date récente
     */
    List<Rapport> findByAuditorId(UUID auditorId);
    
    /**
     * Récupère tous les rapports dans une période donnée.
     * @param dateDebut Date de début de la période
     * @param dateFin Date de fin de la période
     * @return Liste des rapports dans la période
     */
    List<Rapport> findByDateRange(LocalDate dateDebut, LocalDate dateFin);
    
    /**
     * Recherche des rapports par titre (recherche partielle).
     * @param titrePartiel Partie du titre recherché
     * @return Liste des rapports correspondants
     */
    List<Rapport> findByTitreContaining(String titrePartiel);
    
    /**
     * Récupère tous les rapports du système.
     * @return Liste complète des rapports, triés par date récente
     */
    List<Rapport> findAll();
    
    /**
     * Met à jour un rapport existant.
     * @param rapport L'objet Rapport avec les nouvelles données
     * @throws RuntimeException si rapport inexistant
     */
    void update(Rapport rapport);
    
    /**
     * Supprime un rapport (suppression logique ou physique selon business rule).
     * @param id L'UUID du rapport à supprimer
     * @throws RuntimeException si rapport inexistant
     */
    void delete(UUID id);
    
    /**
     * Associe un rapport à un ou plusieurs comptes (relation Many-to-Many).
     * @param rapportId L'UUID du rapport
     * @param accountIds Liste des UUIDs des comptes à associer
     * @throws RuntimeException si rapport ou comptes inexistants
     */
    void associateWithAccounts(UUID rapportId, List<UUID> accountIds);
    
    /**
     * Récupère tous les comptes associés à un rapport.
     * @param rapportId L'UUID du rapport
     * @return Liste des comptes liés au rapport
     */
    List<Account> getAssociatedAccounts(UUID rapportId);
    
    /**
     * Compte le nombre total de rapports dans le système.
     * @return Nombre total de rapports
     */
    long countAllReports();
}