package app.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import app.models.Enums.CreditType;

public class Echeance {
    private long id;
    private Credit credit; // Relation Many-to-One : une échéance appartient à un seul crédit
    private int numero; // Numéro d'ordre de l'échéance (1, 2, 3... mois)
    private LocalDate datePrevue; // Date à laquelle le paiement est attendu
    private BigDecimal montantPrincipal; // Part du capital à rembourser
    private BigDecimal interet; // Intérêts dus pour cette échéance
    private BigDecimal penalite; // Montant de la pénalité si retard (default 0)
    private boolean payee; // Indique si l'échéance a été réglée
    private LocalDate datePaiement; // Date réelle du paiement (si payé)

    public Echeance() {}

    public Echeance(long id, Credit credit, int numero, LocalDate datePrevue, 
                   BigDecimal montantPrincipal, BigDecimal interet) {
        this.id = id;
        this.credit = credit;
        this.numero = numero;
        this.datePrevue = datePrevue;
        this.montantPrincipal = montantPrincipal;
        this.interet = interet;
        this.penalite = BigDecimal.ZERO; // Pas de pénalité par défaut
        this.payee = false; // Non payée par défaut
        this.datePaiement = null; // Pas encore payée
    }

    // Getters et Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getCreditIdCompat() { return credit != null ? credit.getId() : 0; }
    public void setCreditIdCompat(long creditId) { 
        // Cette méthode est conservée pour compatibilité, mais préférer setCredit()
        // L'implémentation complète nécessiterait un service pour récupérer Credit par ID
    }

    public Credit getCredit() { return credit; }
    public void setCredit(Credit credit) { this.credit = credit; }

    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }

    public LocalDate getDatePrevue() { return datePrevue; }
    public void setDatePrevue(LocalDate datePrevue) { this.datePrevue = datePrevue; }

    public BigDecimal getMontantPrincipal() { return montantPrincipal; }
    public void setMontantPrincipal(BigDecimal montantPrincipal) { this.montantPrincipal = montantPrincipal; }

    public BigDecimal getInteret() { return interet; }
    public void setInteret(BigDecimal interet) { this.interet = interet; }

    public BigDecimal getPenalite() { return penalite; }
    public void setPenalite(BigDecimal penalite) { this.penalite = penalite; }

    public boolean isPayee() { return payee; }
    public void setPayee(boolean payee) { this.payee = payee; }

    public LocalDate getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDate datePaiement) { this.datePaiement = datePaiement; }

    // Méthodes utilitaires
    
    /**
     * Calcule le montant total de l'échéance (capital + intérêts + pénalités)
     */
    public BigDecimal getMontantTotal() {
        return montantPrincipal.add(interet).add(penalite);
    }

    /**
     * Vérifie si l'échéance est en retard (non payée et date prévue dépassée)
     */
    public boolean isEnRetard() {
        return !payee && datePrevue.isBefore(LocalDate.now());
    }

    /**
     * Marque l'échéance comme payée à la date actuelle
     */
    public void marquerCommePaye() {
        this.payee = true;
        this.datePaiement = LocalDate.now();
    }

    /**
     * Marque l'échéance comme payée à une date spécifique
     */
    public void marquerCommePaye(LocalDate datePaiement) {
        this.payee = true;
        this.datePaiement = datePaiement;
    }

    // Méthodes utilitaires enrichies grâce à la relation avec Credit

    /**
     * Retourne le compte associé à cette échéance via le crédit
     */
    public Account getAccount() {
        return credit != null ? credit.getAccount() : null;
    }

    /**
     * Retourne l'ID du client propriétaire du compte via la relation Credit → Account
     */
    public long getClientId() {
        Account account = getAccount();
        return account != null ? account.getClientId() : 0;
    }

    /**
     * Retourne le gestionnaire qui a validé le crédit
     */
    public Manger getCreditManager() {
        return credit != null ? credit.getValidateBy() : null;
    }

    /**
     * Retourne l'employé qui a créé le crédit
     */
    public Treller getCreditCreator() {
        return credit != null ? credit.getCreeBy() : null;
    }

    /**
     * Retourne le type de crédit (CONSTANT, DEGRESSIF)
     */
    public CreditType getCreditType() {
        return credit != null ? credit.getType() : null;
    }

    /**
     * Retourne le montant initial du crédit
     */
    public BigDecimal getMontantInitialCredit() {
        return credit != null ? credit.getMontantInitial() : BigDecimal.ZERO;
    }
}