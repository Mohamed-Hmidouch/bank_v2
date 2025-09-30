package app.models;

import java.math.BigDecimal;
import java.util.List;

import app.models.Enums.CreditStatus;
import app.models.Enums.CreditType;

public class Credit {
    private long id;
    private Account account;
    private Manger validateBy;
    private Treller CreeBy;
    private CreditStatus status;
    private CreditType type;
    private BigDecimal montantInitial;
    private BigDecimal taux;
    private int dureMois;
    private List<Echeance> echeances;

    public Credit() {}

    public Credit(long id, Account account, Manger validateBy, Treller CreeBy, CreditStatus status, CreditType type,
                  BigDecimal montantInitial, BigDecimal taux, int dureMois) {
        this.id = id;
        this.account = account;
        this.validateBy = validateBy;
        this.CreeBy = CreeBy;
        this.status = status;
        this.type = type;
        this.montantInitial = montantInitial;
        this.taux = taux;
        this.dureMois = dureMois;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Manger getValidateBy() {
        return validateBy;
    }

    public void setValidateBy(Manger validateBy) {
        this.validateBy = validateBy;
    }

    public Treller getCreeBy() {
        return CreeBy;
    }

    public void setCreeBy(Treller CreeBy) {
        this.CreeBy = CreeBy;
    }

    public CreditStatus getStatus() {
        return status;
    }

    public void setStatus(CreditStatus status) {
        this.status = status;
    }

    public CreditType getType() {
        return type;
    }

    public void setType(CreditType type) {
        this.type = type;
    }

    public BigDecimal getMontantInitial() {
        return montantInitial;
    }

    public void setMontantInitial(BigDecimal montantInitial) {
        this.montantInitial = montantInitial;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public int getDureMois() {
        return dureMois;
    }

    public void setDureMois(int dureMois) {
        this.dureMois = dureMois;
    }

    public List<Echeance> getEcheances() { return echeances; }
    public void setEcheances(List<Echeance> echeances) { this.echeances = echeances; }
}
