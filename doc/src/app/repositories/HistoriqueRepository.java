package app.repositories;

import app.repositories.interfaces.HistoriqueInterface;
import app.models.Historique;
import app.models.Auditor;
import app.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation pour l'historisation des opérations
 * Fournit des utilitaires pour enregistrer et lire les historiques
 * Bonnes pratiques : try-with-resources, mapping défensif, messages FR
 */
public class HistoriqueRepository implements HistoriqueInterface {

    @Override
    public Historique save(Historique historique) {
        String sql = "INSERT INTO historique (description, date, accountid, auditor_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, historique.getDescription());
            stmt.setTimestamp(2, Timestamp.valueOf(historique.getDate()));
            stmt.setLong(3, historique.getAccountId());
            if (historique.getAuditor() != null) {
                stmt.setLong(4, historique.getAuditor().getId());
            } else {
                stmt.setNull(4, Types.BIGINT);
            }

            int rows = stmt.executeUpdate();
            if (rows == 0) {
                throw new RuntimeException("Échec sauvegarde historique");
            }

            try (ResultSet gk = stmt.getGeneratedKeys()) {
                if (gk.next()) {
                    historique.setId(gk.getLong(1));
                }
            }

            return historique;

        } catch (SQLException e) {
            throw new RuntimeException("Erreur sauvegarde historique : " + e.getMessage(), e);
        }
    }

    /**
     * Récupère l'historique complet d'un compte trié par date descendante
     */
    public List<Historique> findByAccountId(long accountId) {
        String sql = "SELECT h.id, h.description, h.date, h.accountid, h.auditor_id, a.fullname as auditor_name " +
                "FROM historique h LEFT JOIN auditor a ON h.auditor_id = a.id " +
                "WHERE h.accountid = ? AND h.date IS NOT NULL ORDER BY h.date DESC";
        List<Historique> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Historique h = new Historique();
                    h.setId(rs.getLong("id"));
                    h.setDescription(rs.getString("description"));
                    Timestamp ts = rs.getTimestamp("date");
                    if (ts != null) h.setDate(ts.toLocalDateTime());
                    h.setAccountId(rs.getLong("accountid"));
                    long auditorId = rs.getLong("auditor_id");
                    if (!rs.wasNull()) {
                        Auditor auditor = new Auditor();
                        auditor.setId(auditorId);
                        auditor.setFullname(rs.getString("auditor_name"));
                        h.setAuditor(auditor);
                    }
                    list.add(h);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération historique pour accountId=" + accountId + " : " + e.getMessage(), e);
        }
        return list;
    }

    /**
     * Récupère les N dernières entrées d'un compte
     */
    public List<Historique> findRecentByAccountId(long accountId, int limit) {
        String sql = "SELECT id, description, date, accountid, auditor_id FROM historique WHERE accountid = ? ORDER BY date DESC LIMIT ?";
        List<Historique> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Historique h = new Historique();
                    h.setId(rs.getLong("id"));
                    h.setDescription(rs.getString("description"));
                    Timestamp ts = rs.getTimestamp("date");
                    if (ts != null) h.setDate(ts.toLocalDateTime());
                    h.setAccountId(rs.getLong("accountid"));
                    list.add(h);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération historique récent pour accountId=" + accountId + " : " + e.getMessage(), e);
        }
        return list;
    }

    /**
     * Utilitaire rapide pour ajouter une entrée d'historique sans construire l'objet explicitement
     */
    public Historique addEntry(long accountId, String description, Long auditorId) {
        Historique h = new Historique();
        h.setAccountId(accountId);
        h.setDescription(description);
        h.setDate(LocalDateTime.now());
        if (auditorId != null) {
            Auditor auditor = new Auditor();
            auditor.setId(auditorId);
            h.setAuditor(auditor);
        }
        return save(h);
    }

}