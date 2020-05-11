package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.services.MedicalProfileManagerAdapter;

/**
 * @author Xavier Campos
 */
public class MedicalProfileControllersFactory {
    private MedicalProfileControllersFactory() {
        // Private constructor
    }

    /**
     * Method responsible for creating the transaction to add a new vaccination to a pet.
     * @return The transaction to add a new vaccination to a pet
     */
    public static TrAddNewVaccination createTrAddNewVaccination() {
        return new TrAddNewVaccination(new MedicalProfileManagerAdapter());
    }

    /**
     * Method responsible for creating the transaction to obtain all the vaccinations of a pet.
     * @return The transaction to obtain all the vaccinations of a pet
     */
    public static TrObtainAllPetVaccinations createTrObtainAllPetVaccinations() {
        return new TrObtainAllPetVaccinations(new MedicalProfileManagerAdapter());
    }
}
