package app.repositories;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;

import app.models.Transaction;
import app.repositories.interfaces.TransactionInterface;
import app.utils.DatabaseConnection;

public class TransactionRepository implements TransactionInterface {

    /**
     * MÉTHODE CRITIQUE : Enregistre toutes les transactions TELLER
     * 
     * MAPPING EXACT DB :
     * - PostgreSQL: id (uuid), accountId (bigint), datetransaction (timestamp), montant (numeric)
     * - Java Model: UUID getId(), long getAccountId(), LocalDateTime getDateTransaction(), BigDecimal getMontant()
     * 
     * UTILISÉE POUR 4 USE CASES TELLER :
     * - USE CASE 3: Dépôt client
     * - USE CASE 4: Retrait client  
     * - USE CASE 5: Virement interne (2 transactions)
     * - USE CASE 6: Crédit accordé
     */
    @Override
    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transaction (id, accountId, datetransaction, montant) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Générer UUID si absent
            if (transaction.getId() == null) {
                transaction.setId(UUID.randomUUID());
            }
            
            // Mapping exact colonnes DB
            stmt.setObject(1, transaction.getId());                    // uuid
            stmt.setLong(2, transaction.getAccountId());               // bigint  
            stmt.setTimestamp(3, Timestamp.valueOf(transaction.getDateTransaction())); // timestamp
            stmt.setBigDecimal(4, transaction.getMontant());           // numeric(18,2)
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new RuntimeException("Échec sauvegarde transaction");
            }
            
            return transaction;
            
        } catch (SQLException e) {
            throw new RuntimeException("Erreur enregistrement transaction : " + e.getMessage(), e);
        }
    }

    @Override
    public List<Transaction> findByAccountId(long accountId) {
        String sql = "SELECT id, accountId, datetransaction, montant FROM transaction WHERE accountId = ? ORDER BY datetransaction DESC";
        List<Transaction> transactions = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, accountId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction tx = new Transaction();
                    tx.setId((java.util.UUID) rs.getObject("id"));
                    tx.setAccountId(rs.getLong("accountId"));
                    Timestamp ts = rs.getTimestamp("datetransaction");
                    if (ts != null) tx.setDateTransaction(ts.toLocalDateTime());
                    tx.setMontant(rs.getBigDecimal("montant").setScale(2, java.math.RoundingMode.HALF_UP));
                    transactions.add(tx);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur récupération transactions pour accountId=" + accountId + " : " + e.getMessage(), e);
        }
        return transactions;
    }
}