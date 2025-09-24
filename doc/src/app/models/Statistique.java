package app.models;

import java.time.LocalDateTime;

public class Statistique {
    private long id;
    private String type;
    private LocalDateTime dateCalcul; // Quand la statistique a été calculée
    private long auditorId; // Many-to-One : auditeur qui génère la statistique

    public Statistique() {}

    public Statistique(long id, String type, LocalDateTime dateCalcul, long auditorId) {
        this.id = id;
        this.type = type;
        this.dateCalcul = dateCalcul;
        this.auditorId = auditorId;
    }

    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getDateCalcul() { return dateCalcul; }
    public void setDateCalcul(LocalDateTime dateCalcul) { this.dateCalcul = dateCalcul; }

    public long getAuditorId() { return auditorId; }
    public void setAuditorId(long auditorId) { this.auditorId = auditorId; }
}
