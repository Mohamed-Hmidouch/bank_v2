package app.models;

import java.math.BigDecimal;
import java.util.*;
import app.models.Enums.AccountType;

public class Account {
    private long id;
    private BigDecimal solde;
    private String status = "active";
    private AccountType type;
    private List<Transaction> transactions;
    private List<Historique> historiques;
    private List<Rapport> rapports;
    private long clientId;
    
    public Account() {
        // Pas de type par défaut - doit être spécifié explicitement
    }
        
    
    public Account(long id, BigDecimal solde, long clientId, AccountType type) {
        this.id = id;
        this.solde = solde;
        this.clientId = clientId;
        this.type = type;
    }
    
    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public AccountType getType() { return type; }
    public void setType(AccountType type) { this.type = type; }
    
    public long getClientId() { return clientId; }
    public void setClientId(long clientId) { this.clientId = clientId; }
    
    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
    
    public List<Historique> getHistoriques() { return historiques; }
    public void setHistoriques(List<Historique> historiques) { this.historiques = historiques; }
    
    public List<Rapport> getRapports() { return rapports; }
    public void setRapports(List<Rapport> rapports) { this.rapports = rapports; }
}
