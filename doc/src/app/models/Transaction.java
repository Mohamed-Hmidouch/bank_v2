package app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import app.models.Enums.TransactionType;

public class Transaction {
    private UUID id;
    private long accountId; // ✅ Cohérent avec Account.id (long)
    private LocalDateTime dateTransaction;
    private BigDecimal montant;
    private TransactionType type; // nullable for backward compatibility

    public Transaction() {}

    public Transaction(UUID id, long accountId, LocalDateTime dateTransaction, BigDecimal montant){
        this.id = id;
        this.accountId = accountId;
        this.dateTransaction = dateTransaction;
        this.montant = montant;
        this.type = null;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public void setAccountId(long accountId){
        this.accountId = accountId;
    }

    public void setDateTransaction(LocalDateTime dateTransaction){
        this.dateTransaction = dateTransaction;
    }

    public void setMontant(BigDecimal montant){
        this.montant = montant;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public UUID getId(){
        return id;
    }
    
    public long getAccountId() {
        return accountId;
    }

    public LocalDateTime getDateTransaction() {
        return dateTransaction;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public TransactionType getType() {
        return type;
    }
}
