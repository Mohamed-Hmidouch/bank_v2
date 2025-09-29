package app.services;

import app.models.Enums.AccountType;
import app.repositories.ClientRepository;
import app.repositories.AccountRepository;
import app.repositories.TransactionRepository;
import app.repositories.CreditRepository;
import app.models.*;
import app.models.Enums.CreditStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.imageio.IIOException;

/**
 * MÉTHODE CRITIQUE : Service métier TELLER pour les 6 cas d'usage
 * ORCHESTRE tous les repositories + validation métier + logique complexe
 * 
 * ARCHITECTURE PATTERN : TellerService gère ses propres dépendances
 * - ClientRepository : USE CASES 1,2
 * - AccountRepository : USE CASES 1,2,3,4,5,6
 * - TransactionRepository : USE CASES 3,4,5 (traçabilité)
 * - CreditRepository : USE CASE 6
 */
public class TellerService {

    // Pattern imposé : Service gère ses dépendances
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CreditRepository creditRepository;

    public TellerService() {
        this.clientRepository = new ClientRepository();
        this.accountRepository = new AccountRepository();
        this.transactionRepository = new TransactionRepository();
        this.creditRepository = new CreditRepository();
    }

    /**
     * USE CASE 1 : Créer client avec premier compte
     * ORCHESTRATION : Client.save() → récupérer ID → Account.save() avec clientId
     */
    public boolean createClientWithFirstAccount(String nom, String prenom, String email,String telephone, String typeCompte, BigDecimal soldeInitial)
    {
        if(clientRepository.findByEmail(email) != null){
            throw new IllegalAccessError("Un client avec c'est email est deja exité");
        }
        if(soldeInitial.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Tu doit ajouté un monatnt infrérieur de 0 comme solde initial");
        }
        try{
        Client client = new Client();
        client.setNom(nom);
        client.setPrenom(prenom);
        client.setEmail(email);
        client.setTelephone(telephone);
        clientRepository.save(client);
        Account account = new Account();
        account.setSolde(soldeInitial);
        account.setType(AccountType.valueOf(typeCompte));
        account.setClientId(client.getId());
        accountRepository.save(account);
        return true;
        } catch (Exception e){
            throw new RuntimeException("erreur dans la creation du compte " + e.getMessage());
        }
    }

    /**
     * USE CASE 2 : Créer compte supplémentaire
     * VALIDATION : Client existe via findByClientId() + Account.save()
     */
    public boolean createAdditionalAccount(Long clientId, String typeCompte, BigDecimal soldeInitial) {
        if (clientId == null || clientId <= 0) {
            throw new IllegalArgumentException("ID client invalide.");
        }
        if (soldeInitial == null || soldeInitial.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Tu doit ajouté un monatnt infrérieur de 0 comme solde initial");
        }
        try {
            Account account = new Account();
            account.setSolde(soldeInitial);
            account.setType(AccountType.valueOf(typeCompte));
            account.setClientId(clientId);
            accountRepository.save(account);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("erreur dans la creation du compte supplémentaire: " + e.getMessage());
        }
    }

    /**
     * USE CASE 3 : Effectuer dépôt
     * LOGIQUE : Account.findById() + updateSolde() + Transaction.save(DEPOSIT)
     */
    public boolean makeDeposit(Long accountId, BigDecimal montant) {
        // TODO: Validation + mise à jour solde + traçabilité
        return false;
    }

    /**
     * USE CASE 4 : Effectuer retrait
     * VALIDATION CRITIQUE : checkSoldeSuffisant() + updateSolde() +
     * Transaction.save(WITHDRAW)
     */
    public boolean makeWithdrawal(Long accountId, BigDecimal montant) {
        // TODO: Validation solde + retrait + traçabilité
        return false;
    }

    /**
     * USE CASE 5 : Virement interne
     * LOGIQUE COMPLEXE : 2 comptes + validation + 2 updateSolde() + 2
     * Transaction.save()
     */
    public boolean makeInternalTransfer(Long compteSource, Long compteDestination, BigDecimal montant) {
        // TODO: Double validation + double mise à jour + double traçabilité
        return false;
    }

    /**
     * USE CASE 6 : Demande de crédit (SANS TYPE)
     * WORKFLOW : Credit.save() avec STATUS=PENDING → Manager approval
     */
    public boolean requestCredit(Long accountId, BigDecimal montant, BigDecimal taux, int dureeMois) {
        // TODO: Validation + Credit.save() STATUS=PENDING
        return false;
    }
}