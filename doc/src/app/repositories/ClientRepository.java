package app.repositories;

import java.sql.*;
import app.models.Client;
import app.repositories.interfaces.ClientInterface;
import app.utils.DatabaseConnection;

public class ClientRepository implements ClientInterface {
    private final Connection conn;

    public ClientRepository() {
        this.conn = DatabaseConnection.getConnection();
    }

    public void save(Client client) {
        String sql = "INSERT INTO client (id, nom, prenom, email, telephone) VALUES (?, ?, ?, ?, ?)";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, client.getId());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getPrenom());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getTelephone());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du client", e);
        }
    }

    public Client findByEmail(String email) {
        String sql = "SELECT * FROM client WHERE email = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getLong("id"));
                    client.setNom(rs.getString("nom"));
                    client.setPrenom(rs.getString("prenom"));
                    client.setEmail(rs.getString("email"));
                    client.setTelephone(rs.getString("telephone"));
                    return client;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche du client par email", e);
        }
        return null;
    }

    @Override
    public java.util.List<Client> findAll() {
        String sql = "SELECT * FROM client WHERE deleted_at IS NULL";
        java.util.List<Client> clients = new java.util.ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setEmail(rs.getString("email"));
                client.setTelephone(rs.getString("telephone"));
                clients.add(client);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération des clients", e);
        }
        return new java.util.ArrayList<>(clients);
    }

    @Override
    public void update(Long id, Client client) {
        String sql = "UPDATE client SET nom = ?, prenom = ?, email = ?, telephone = ? WHERE id = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(4, client.getTelephone());
            stmt.setLong(5, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour du client", e);
        }
    }

    public void delete(Long id){
        String sql = "UPDATE client SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression logique du client", e);
        }
    }
}