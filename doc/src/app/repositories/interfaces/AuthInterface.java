package app.repositories.interfaces;

import app.models.User;


public interface AuthInterface {

    // SESSION - Récupérer utilisateur connecté par ID 
    User findById(long id);

    // LOGIN - Email + Password pour connexion
    User findByEmailAndPassword(String email, String password);

    // SESSION - Mise à jour du statut de connexion en base
    void updateLoggedInStatus(long userId, boolean loggedIn);

    // Vérifie si l'utilisateur est actuellement connecté
    boolean isUserLoggedIn(long userId);
}