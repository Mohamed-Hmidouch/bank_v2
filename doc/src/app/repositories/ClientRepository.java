package app.repositories;

import java.sql.Connection;

import app.models.Client;
import app.repositories.interfaces.ClientInterface;
import app.utils.DatabaseConnection;

public class ClientRepository implements ClientInterface {
    private final Connection conn;

    public ClientRepository() {
        this.conn = DatabaseConnection.getConnection();
    }

    public void save(Client client) {
        String sql = "INSERT INTO client (id, nom, prenom, email, adresse, telephone) VALUES (?, ?, ?, ?, ?, ?)";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, client.getId());
            stmt.setString(2, client.getNom());
            stmt.setString(3, client.getPrenom());
            stmt.setString(4, client.getEmail());
            stmt.setString(5, client.getAdresse());
            stmt.setString(6, client.getTelephone());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la sauvegarde du client", e);
        }
    }

    public Client findByEmail(String email) {
        String sql = "SELECT * FROM client WHERE email = ?";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId((java.util.UUID) rs.getObject("id"));
                    client.setNom(rs.getString("nom"));
                    client.setPrenom(rs.getString("prenom"));
                    client.setEmail(rs.getString("email"));
                    client.setAdresse(rs.getString("adresse"));
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
        String sql = "SELECT * FROM client";
        java.util.List<Client> clients = new java.util.ArrayList<>();
        try (var stmt = conn.prepareStatement(sql);
             var rs = stmt.executeQuery()) {
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
    public void update(Client client) {
        String sql = "UPDATE client SET nom = ?, prenom = ?, email = ?, adresse = ?, telephone = ? WHERE id = ?";
        try (var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getEmail());
            stmt.setString(5, client.getTelephone());
            stmt.setObject(6, client.getId());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la mise à jour du client", e);
        }
    }
}