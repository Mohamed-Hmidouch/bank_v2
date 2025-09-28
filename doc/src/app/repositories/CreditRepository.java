package app.repositories;

import app.repositories.interfaces.CreditInterface;
import app.models.Credit;
import app.utils.DatabaseConnection;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

/**
 * Implémentation pour les opérations sur Credit
 * CRITIQUE pour USE CASE 6 TELLER : Demande de crédit
 */
public class CreditRepository implements CreditInterface {
    
    /**
     * MÉTHODE CRITIQUE : Enregistre demande de crédit TELLER (SANS TYPE)
     * 
     * MAPPING EXACT DB SIMPLIFIÉ :
     * - account_id (bigint) ← credit.getAccount().getId()
     * - validateby_id (bigint) ← credit.getValidateBy().getId() (Manager)
     * - creeby_id (bigint) ← credit.getCreeBy().getId() (Teller)  
     * - status (credit_status) ← PENDING (enum PostgreSQL)
     * - montantinitial (numeric) ← credit.getMontantInitial()
     * - taux (numeric) ← credit.getTaux()
     * - duremois (integer) ← credit.getDureMois()
     * 
     * USE CASE 6 : requestCredit() → STATUS = PENDING → Attente approbation Manager
     */
    @Override
    public Credit save(Credit credit) {
        String sql = "INSERT INTO credit (account_id, validateby_id, creeby_id, status, montantinitial, taux, duremois) " +
                    "VALUES (?, ?, ?, ?::credit_status, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Mapping simplifié SANS colonne type
            stmt.setLong(1, credit.getAccount().getId());           // account_id (FK vers account)
            stmt.setLong(2, credit.getValidateBy().getId());        // validateby_id (Manager qui validera)
            stmt.setLong(3, credit.getCreeBy().getId());            // creeby_id (Teller qui crée)
            stmt.setString(4, credit.getStatus().toString());       // status::credit_status (PENDING)
            stmt.setBigDecimal(5, credit.getMontantInitial());      // montantinitial (numeric 18,2)
            stmt.setBigDecimal(6, credit.getTaux());                // taux (numeric 8,4)
            stmt.setInt(7, credit.getDureMois());                   // duremois (integer)
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Échec création demande crédit");
            }
            
            // Récupérer l'ID auto-généré
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    credit.setId(generatedKeys.getLong(1));
                }
            }
            
            return credit;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erreur enregistrement crédit : " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Credit> findByAccountId(long accountId) {
        // TODO: Implémenter pour suivi crédits compte
        return new ArrayList<>();
    }
}