package app.Controllers;

import app.services.TellerService;
import java.math.BigDecimal;

public class TellerController {
//    private final TellerService tellerService;

//    // ✅ Le contrôleur connaît SEULEMENT le service
//    public TellerController() {
//        this.tellerService = new TellerService();
//    }
//
//    /**
//     * Créer un nouveau client avec son premier compte bancaire (indissociables)
//     */
//    public boolean createClientWithFirstAccount(String nom, String prenom, String email,
//                                              String adresse, String telephone,
//                                              String typeCompte, BigDecimal soldeInitial) {
//        try {
//            return tellerService.createClientWithFirstAccount(
//                nom, prenom, email, adresse, telephone, typeCompte, soldeInitial);
//        } catch (Exception e) {
//            System.err.println("Erreur dans le contrôleur TELLER: " + e.getMessage());
//            return false;
//        }
//    }
}