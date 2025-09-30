package app.services;

import app.models.Enums.AccountType;
import app.repositories.*;
import app.models.*;
import app.models.Enums.CreditStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private final TrellerRepository trellerRepository;

    public TellerService() {
        this.clientRepository = new ClientRepository();
        this.accountRepository = new AccountRepository();
        this.transactionRepository = new TransactionRepository();
        this.creditRepository = new CreditRepository();
        this.trellerRepository = new TrellerRepository();
    }

    // ✅ Méthode privée réutilisable pour valider un compte
    private Account validateAccount(Long accountId, String context) {
        if (accountId == null || accountId <= 0) {
            throw new IllegalArgumentException("ID " + context + " invalide");
        }

        Account account = accountRepository.findById(accountId);
        if (account == null || account.getId() == 0L) {
            throw new IllegalArgumentException("Compte " + context + " introuvable : " + accountId);
        }

        if (!accountRepository.isAccountActive(accountId)) {
            throw new IllegalStateException("Compte " + context + " inactif");
        }

        return account;
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
        if (montant == null) {
            throw new IllegalArgumentException("Le montant est obligatoire.");
        }
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }

        try {
            // Réutilise la méthode centrale de validation
            Account account = validateAccount(accountId, "compte");

            // Calculer nouveau solde
            BigDecimal nouveauSolde = (account.getSolde() == null ? BigDecimal.ZERO : account.getSolde()).add(montant);

            // Mettre à jour le solde du compte
            accountRepository.updateSolde(accountId, nouveauSolde);

            // Créer la transaction et remplir les attributs
            Transaction tx = new Transaction();
            tx.setAccountId(accountId);
            tx.setDateTransaction(LocalDateTime.now());
            tx.setMontant(montant.setScale(2, java.math.RoundingMode.HALF_UP));
            transactionRepository.save(tx);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("erreur lors make deposit: " + e.getMessage(), e);
        }
    }

    /**
     * USE CASE 4 : Effectuer retrait
     * VALIDATION CRITIQUE : checkSoldeSuffisant() + updateSolde() +
     * Transaction.save(WITHDRAW)
     */
    public boolean makeWithdrawal(Long accountId, BigDecimal montant) {
        if (montant == null) {
            throw new IllegalArgumentException("Le montant est obligatoire.");
        }
        if (montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif.");
        }
        try {
            Account account = validateAccount(accountId, "compte");
            // Calculer nouveau solde
            BigDecimal nouveauSolde = (account.getSolde() == null ? BigDecimal.ZERO : account.getSolde()).subtract(montant);

            // Mettre à jour le solde du compte
            accountRepository.updateSolde(accountId, nouveauSolde);

            // Créer la transaction et remplir les attributs
            Transaction tx = new Transaction();
            tx.setAccountId(accountId);
            tx.setDateTransaction(LocalDateTime.now());
            tx.setMontant(montant.setScale(2, java.math.RoundingMode.HALF_UP));
            transactionRepository.save(tx);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("erreur lors make withdraw: " + e.getMessage(), e);
        }
    }


    /**
     * USE CASE 5 : Virement interne
     * LOGIQUE COMPLEXE : 2 comptes + validation + 2 updateSolde() + 2
     * Transaction.save()
     */
    public boolean makeInternalTransfer(Long compteSource, Long compteDestination, BigDecimal montant) {
        validateAccount(compteSource, "source");
        validateAccount(compteDestination, "destination");
        if (compteSource.equals(compteDestination)) {
            throw new IllegalArgumentException("Les comptes source et destination doivent être différents");
        }
        // Validation montant
        if (montant == null || montant.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        try {
            Account sourceAccount = accountRepository.findById(compteSource);
            Account destinationAccount = accountRepository.findById(compteDestination);

            BigDecimal SoldeSource = sourceAccount.getSolde().subtract(montant);
            BigDecimal SoldeDestination = destinationAccount.getSolde().add(montant);

            accountRepository.updateSolde(compteSource, SoldeSource);
            accountRepository.updateSolde(compteDestination, SoldeDestination);

            Transaction txDebit = new Transaction();
            txDebit.setAccountId(compteSource);
            txDebit.setDateTransaction(LocalDateTime.now());
            txDebit.setMontant(montant.negate());
            transactionRepository.save(txDebit);

            Transaction txCredit = new Transaction();
            txCredit.setAccountId(compteDestination);
            txCredit.setDateTransaction(LocalDateTime.now());
            txCredit.setMontant(montant.setScale(2, java.math.RoundingMode.HALF_UP));
            transactionRepository.save(txCredit);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("erreur lors make debit transfer: " + e.getMessage(), e);
        }
    }

    /**
     * USE CASE 6 : Demande de crédit (SANS TYPE)
     * WORKFLOW : Credit.save() avec STATUS=PENDING → Manager approval
     */
    public boolean requestCredit(Long accountId,Long creeBy, BigDecimal montant, BigDecimal taux, int dureeMois) {
        BigDecimal tauxMAx = new BigDecimal(20.00);
        if(accountId == null || accountId <= 0)
            throw new IllegalArgumentException("l'id est null");
        if(montant == null || montant.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Montant est null !!");
        if (taux == null || taux.compareTo(BigDecimal.ZERO) < 0 || taux.compareTo(tauxMAx) > 0)
            throw new IllegalArgumentException("Taux inccorect !!");
        if(dureeMois < 0 || dureeMois > 360)
            throw new IllegalArgumentException("Dures non accepté");

        try {
            validateAccount(accountId,"credit");
            if(!accountRepository.isAccountActive(accountId))
                throw new RuntimeException("this account is not active" + accountId);
            List<Credit> creditsClient = creditRepository.findByAccountId(accountId);
            long nCredit = creditsClient.stream().filter(c -> c.getStatus() == CreditStatus.ACTIVE).count();
            if(nCredit >= 2)
                throw new RuntimeException("vous avez plus q'un credit en cour !!" + nCredit);
            
            // ✅ Créer le crédit avec les relations ID testées
            Credit credit = new Credit();
            Account account = accountRepository.findById(accountId);
            credit.setAccount(account);  // Relation avec l'objet Account validé
            credit.setMontantInitial(montant);
            credit.setTaux(taux);
            credit.setDureMois(dureeMois);
            credit.setStatus(CreditStatus.PENDING_APPROVAL);
            Treller tellerCreateur = trellerRepository.findById(creeBy);
            credit.setCreeBy(tellerCreateur);
            // Sauvegarder le crédit
            creditRepository.save(credit);
            
            // ✅ Sauvegarder la transaction pour traçabilité
            Transaction tx = new Transaction();
            tx.setAccountId(accountId);  // Relation avec l'ID testé
            tx.setDateTransaction(LocalDateTime.now());
            tx.setMontant(montant.setScale(2, java.math.RoundingMode.HALF_UP));
            transactionRepository.save(tx);


            return true;
            
        } catch (Exception e) {
            // TODO: GESTION ERREUR
            throw new RuntimeException("Erreur lors de la demande de crédit : " + e.getMessage(), e);
        }
    }
}