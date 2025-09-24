package app.models;

import java.util.List;

public class Rapport {
    private long id;
    private String titre;
    private String contenu;
    private Auditor auditor; // relation Many-to-One
    private List<Account> accounts;// One-to-Many ou Many-to-Many : rapport peut concerner plusieurs comptes

    public Rapport() {}

    public Rapport(long id, String titre, String contenu, Auditor auditor, List<Account> accounts) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auditor = auditor;
        this.accounts = accounts;
    }

    public Auditor getAuditor() {
        return auditor;
    }

    public void setAuditor(Auditor auditor) {
        this.auditor = auditor;
    }
}
