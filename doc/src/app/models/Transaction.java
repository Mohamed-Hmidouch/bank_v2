package app.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private long accountId; // ✅ Cohérent avec Account.id (long)
    private LocalDateTime dateTransaction;
    private BigDecimal montant;

    public Transaction() {}

    public Transaction(UUID id, long accountId, LocalDateTime dateTransaction, BigDecimal montant){
        this.id = id;
        this.accountId = accountId;
        this.dateTransaction = dateTransaction;
        this.montant = montant;
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
}
