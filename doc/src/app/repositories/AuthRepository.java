package app.repositories;

import app.models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import app.utils.DatabaseConnection;

public class AuthRepository implements AuthInterface {

    private final Connection connection;

    public AuthRepository() {
        this.connection = DatabaseConnection.getConnection();
    }

    @Override
    public User findById(long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement prepareStmt = connection.prepareStatement(sql)) {
            prepareStmt.setLong(1, id);
            try (ResultSet result = prepareStmt.executeQuery()) {

                User user = new User();

                while (result.next()) {
                    user.setId(result.getLong("id"));
                    user.setFullName(result.getString("fullName"));
                    user.setRole(User.Role.valueOf(result.getString("role")));
                    user.setEmail(result.getString("email"));
                    user.setPassword(result.getString("password"));
                    user.setLoggedIn(result.getBoolean("logged_in"));
                }

                return user;
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche utilisateur par ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public User findByEmailAndPassword(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try(PreparedStatement pStatement = connection.prepareStatement(sql)){
            pStatement.setString(1, email);
            pStatement.setString(2, password);
            try(ResultSet rs = pStatement.executeQuery()){
                User user = new User();
                while (rs.next()) {
                    user.setId(rs.getLong("id"));
                    user.setFullName(rs.getString("fullName"));
                    user.setRole(User.Role.valueOf(rs.getString("role")));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setLoggedIn(rs.getBoolean("logged_in"));
                }
                return user;
            }
        } catch(Exception e){
            System.err.println("Erreur lors de l'authentification pour email " + email + ": " + e.getMessage());
            return null;
        }
    }

    @Override
    public void updateLoggedInStatus(long userId, boolean loggedIn) {
        String sql = "UPDATE users SET logged_in = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, loggedIn);
            stmt.setLong(2, userId);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                System.err.println("Aucun utilisateur trouvé avec l'ID: " + userId);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du statut de connexion pour l'utilisateur " + userId + ": " + e.getMessage());
        }
    }

    @Override
    public boolean isUserLoggedIn(long userId) {
        String sql = "SELECT logged_in FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("logged_in");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification du statut de connexion pour l'utilisateur " + userId + ": " + e.getMessage());
        }
        return false;
    }
}
