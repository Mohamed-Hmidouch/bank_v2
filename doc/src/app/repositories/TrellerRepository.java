package app.repositories;

import app.models.Treller;
import app.models.User;
import app.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository optimisé pour Treller, exploitant l'héritage natif PostgreSQL.
 * Accès direct à la table treller (hérite des champs de users).
 * Toutes les méthodes retournent null si non trouvé, jamais d'exception métier pour "not found".
 */
public class TrellerRepository {

    /**
     * Recherche un Treller par son ID (table treller héritée de users)
     */
    public Treller findById(long id) {
        String sql = "SELECT * FROM treller WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTreller(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du Treller par ID : " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Recherche un Treller par email (table treller héritée de users)
     */
    public Treller findByEmail(String email) {
        String sql = "SELECT * FROM treller WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTreller(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche du Treller par email : " + e.getMessage(), e);
        }
        return null;
    }

    /**
     * Liste paginée des Trellers (table treller héritée de users)
     */
    public List<Treller> findAll(int limit, int offset) {
        String sql = "SELECT * FROM treller ORDER BY id LIMIT ? OFFSET ?";
        List<Treller> trellers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    trellers.add(mapResultSetToTreller(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la liste des Trellers : " + e.getMessage(), e);
        }
        return trellers;
    }

    /**
     * Mapping ResultSet → Treller (hérite de User)
     */
    private Treller mapResultSetToTreller(ResultSet rs) throws SQLException {
        Treller treller = new Treller();
        treller.setId(rs.getLong("id"));
        treller.setFullName(rs.getString("fullName"));
        treller.setRole(User.Role.valueOf(rs.getString("role"))); // Peut être hardcodé à Treller si besoin
        treller.setEmail(rs.getString("email"));
        treller.setPassword(rs.getString("password"));
        treller.setLoggedIn(rs.getBoolean("logged_in"));
        // Ajouter ici les champs propres à Treller si existants (ex: agence, codeGuichet, etc.)
        return treller;
    }

    /**
     * Retourne le Treller actuellement connecté (champ logged_in = true).
     * Retourne null si aucun Treller n'est connecté.
     */
    public Treller getConnectTreller() {
        String sql = "SELECT * FROM treller WHERE logged_in = true LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTreller(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du Treller connecté : " + e.getMessage(), e);
        }
        return null;
    }
}