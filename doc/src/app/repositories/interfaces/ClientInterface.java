package app.repositories.interfaces;

import app.models.Client;
import java.util.List;

/**
 * Interface pour la gestion des clients dans le système bancaire.
 * Définit les opérations CRUD et de recherche pour les entités Client.
 */
public interface ClientInterface {
    
    /**
     * Sauvegarde un nouveau client dans la base de données.
     * @param client L'objet Client à sauvegarder
     * @throws RuntimeException si erreur de sauvegarde
     */
    void save(Client client);
        
    /**
     * Recherche un client par son email (unique).
     * @param email L'email du client recherché
     * @return Client trouvé ou null si inexistant
     */
    Client findByEmail(String email);
    
    
    /**
     * Récupère tous les clients de la banque.
     * @return Liste complète des clients
     */
    List<Client> findAll();
    
    /**
     * Met à jour les informations d'un client existant.
     * @param id L'ID du client à mettre à jour
     * @param client L'objet Client avec les nouvelles données
     * @throws RuntimeException si client inexistant ou erreur de mise à jour
     */
    void update(Long id, Client client);
    
    /**
     * Suppression logique d'un client (désactivation).
     * @param id L'ID du client à désactiver
     * @throws RuntimeException si client inexistant
     */
    void delete(Long id);
}