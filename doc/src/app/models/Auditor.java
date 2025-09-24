package app.models;

import java.util.List;

public class Auditor extends User {
    private List<Rapport> rapports;
    private List<Historique> historiques;
    private List<Statistique> statistiques;
    
    public Auditor() {}
    
    public Auditor(long id, String fullName, Role role, String email, String password) {
        super(id, fullName, role, email, password);
    }

    public boolean isAuditor() {
        return getRole() == Role.Auditor;
    }
    public List<Rapport> getRapports() {
        return rapports;
    }
    public List<Historique> getHistoriques() {
        return historiques;
    }
    public List<Statistique> getStatistiques() {
        return statistiques;
    }
}
