package app.repositories;

import app.repositories.interfaces.AccountInterface;
import app.models.Account;
import app.utils.DatabaseConnection;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class AccountRepository implements AccountInterface {
    
    /**
     * MÉTHODE CLÉ : Sauvegarde un compte ET établit la liaison avec le client
     * Cette méthode unique gère à la fois la création du compte 
     * ET sa liaison automatique avec le client via clientId (Foreign Key)
     * 
     * UTILISÉE POUR :
     * - USE CASE 1: Créer client avec premier compte
     * - USE CASE 2: Créer compte supplémentaire pour client existant
     */
    @Override
    public void save(Account account) {
        String sql = "INSERT INTO account (solde, status, clientId, type) VALUES (?, ?, ?, ?::account_type)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Paramètres pour création + liaison automatique
            stmt.setBigDecimal(1, account.getSolde());
            stmt.setString(2, "ACTIVE");
            stmt.setLong(3, account.getClientId()); // ← LIAISON CLIENT ICI
            stmt.setString(4, account.getType().toString()); // COURANT ou EPARGNE
            
            stmt.executeUpdate();
            
            // Récupérer l'ID généré et l'affecter à l'objet
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong(1));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erreur création compte : " + e.getMessage(), e);
        }
    }

    @Override
    public Account findById(Long id) {
        // TODO: Implémenter findById
        return null;
    }

    @Override
    public List<Account> findByClientId(Long clientId) {
        // TODO: Implémenter findByClientId
        return new ArrayList<>();
    }

    @Override
    public boolean update(Account account) {
        // TODO: Implémenter update
        return false;
    }

    @Override
    public void updateSolde(Long accountId, BigDecimal nouveauSolde) {
        // TODO: Implémenter updateSolde
    }

    @Override
    public boolean isAccountActive(Long accountId) {
        // TODO: Implémenter isAccountActive
        return false;
    }
}