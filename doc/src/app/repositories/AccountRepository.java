package app.repositories;

import app.repositories.interfaces.AccountInterface;
import app.models.Account;
import app.models.Enums.AccountType;
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
            stmt.setString(2, "Active");
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
        String sql = "SELECT * FROM account WHERE id = ?";
        Account account = new Account();
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setLong(1, id);
                try(ResultSet rs = statement.executeQuery()){
                    if(rs.next()){
                        account.setId(rs.getLong("id"));
                        account.setSolde(rs.getBigDecimal("solde"));
                        account.setStatus(rs.getString("status"));
                        account.setClientId(rs.getLong("clientId"));
                        account.setType(AccountType.valueOf(rs.getString("type")));
                    }
                    return account;
                }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur de reccupation du account d'id : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Account> findByClientId(Long clientId) {
        String sql = "SELECT * FROM account WHERE clientId = ? AND deleted_at IS NULL";
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getLong("id"));
                account.setSolde(rs.getBigDecimal("solde"));
                account.setStatus(rs.getString("status"));
                account.setClientId(rs.getLong("clientId"));
                account.setType(AccountType.valueOf(rs.getString("type")));
                accounts.add(account);
            }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des comptes du client : " + e.getMessage(), e);
        }
        return accounts;
    }

    @Override
    public boolean update(Account account) {
        String sql = "UPDATE account SET solde = ?, status = ?, type = ?::account_type WHERE id = ? AND deleted_at IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, account.getSolde());
            stmt.setString(2, account.getStatus());
            stmt.setString(3, account.getType().toString());
            stmt.setLong(4, account.getId());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du compte : " + e.getMessage(), e);
        }
    }

    @Override
    public void updateSolde(Long accountId, BigDecimal nouveauSolde) {
        String sql = "UPDATE account SET solde = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, nouveauSolde.setScale(2, java.math.RoundingMode.HALF_UP));
            stmt.setLong(2, accountId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour du solde : " + e.getMessage(), e);
        }
    }

    @Override
    public boolean isAccountActive(Long accountId) {
        String sql = "SELECT status FROM account WHERE id = ? AND deleted_at IS NULL";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return "Active".equalsIgnoreCase(rs.getString("status"));
            }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la vérification du statut du compte : " + e.getMessage(), e);
        }
        return false;
    }
}