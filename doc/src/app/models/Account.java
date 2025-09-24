package app.models;

import java.math.BigDecimal;
import java.util.*;
public class Account {
    private long id;
    private BigDecimal solde;
    private String status = "active";
    private List<Transaction> transactions;
    private List<Historique> historiques; // Relation One-to-Many : un compte peut avoir plusieurs historiques
    private List<Rapport> rapports;
    private long clientId; // Relation Many-to-One : un compte appartient à un seul client
    
    public Account() {}
        
    public Account(long id, BigDecimal solde, long clientId) {
        this.id = id;
        this.solde = solde;
        this.clientId = clientId;
    }
    
    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public BigDecimal getSolde() { return solde; }
    public void setSolde(BigDecimal solde) { this.solde = solde; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public long getClientId() { return clientId; }
    public void setClientId(long clientId) { this.clientId = clientId; }
    
    public List<Transaction> getTransactions() { return transactions; }
    public void setTransactions(List<Transaction> transactions) { this.transactions = transactions; }
    
    public List<Historique> getHistoriques() { return historiques; }
    public void setHistoriques(List<Historique> historiques) { this.historiques = historiques; }
    
    public List<Rapport> getRapports() { return rapports; }
    public void setRapports(List<Rapport> rapports) { this.rapports = rapports; }
}
