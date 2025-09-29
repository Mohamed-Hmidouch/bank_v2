package app.repositories;

import app.repositories.interfaces.CreditInterface;
import app.models.Credit;
import app.models.Manger;
import app.models.Treller;
import app.models.Account;
import app.models.Enums.CreditStatus;
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
    
    /**
     * MÉTHODE CRITIQUE : Recherche tous les crédits d'un compte
     * UTILISÉE POUR : Suivi des demandes de crédit par account
     * - Manager : Voir les crédits en attente d'approbation  
     * - Teller : Consulter historique des crédits d'un client
     * - Audit : Traçabilité complète des crédits
     */
    @Override
    public List<Credit> findByAccountId(long accountId) {
        String sql = "SELECT * FROM credit WHERE account_id = ? AND deleted_at IS NULL ORDER BY id DESC";
        List<Credit> credits = new ArrayList<>();
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, accountId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Credit credit = new Credit();
                    Manger validateBy = new Manger();
                    Treller createBy = new Treller();
                    credit.setId(rs.getLong("id"));
                    
                    // Créer un Account minimal avec juste l'ID
                    Account account = new Account();
                    account.setId(rs.getLong("account_id"));
                    credit.setAccount(account);
                    // Mapper les données directement depuis la DB
                    credit.setMontantInitial(rs.getBigDecimal("montantinitial"));
                    credit.setTaux(rs.getBigDecimal("taux"));
                    credit.setDureMois(rs.getInt("duremois"));
                    String statusStr = rs.getString("status");
                    credit.setStatus(CreditStatus.valueOf(statusStr));
                    validateBy.setId(rs.getLong("validateby_id"));
                    createBy.setId(rs.getLong("creeby_id"));
                    credit.setCreeBy(createBy);
                    credit.setValidateBy(validateBy);
                    credits.add(credit);
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erreur recherche crédits par accountId : " + e.getMessage(), e);
        }
        
        return credits;
    }
}