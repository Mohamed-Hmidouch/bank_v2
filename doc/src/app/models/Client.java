package app.models;

import java.math.BigDecimal;
import java.util.List;

public class Client {
    private long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private BigDecimal salaire;
    private List<Account> accounts; // Relation One-to-Many : un client peut avoir plusieurs comptes
    
    public Client() {}
    
    public Client(long id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }
    
    public Client(long id, String nom, String prenom, String email, String telephone, BigDecimal salaire) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.salaire = salaire;
    }
    
    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public BigDecimal getSalaire() { return salaire; }
    public void setSalaire(BigDecimal salaire) { this.salaire = salaire; }
    
    public List<Account> getAccounts() { return accounts; }
    public void setAccounts(List<Account> accounts) { this.accounts = accounts; }
}
