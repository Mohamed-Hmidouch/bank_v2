package app.models;

import java.time.LocalDateTime;

public class Historique {
    private long id;
    private String description;
    private LocalDateTime date;
    private long accountId; // Relation Many-to-One : un historique appartient à un compte
    private Auditor auditor; // auditor li ttfara3 lhistorique

    public Historique() {}

    public Historique(long id, String description, LocalDateTime date, long accountId, Auditor auditor) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.accountId = accountId;
        this.auditor = auditor;
    }

    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public long getAccountId() { return accountId; }
    public void setAccountId(long accountId) { this.accountId = accountId; }

    public Auditor getAuditor() {
        return auditor;
    }

    public void setAuditor(Auditor auditor) {
        this.auditor = auditor;
    }
}
