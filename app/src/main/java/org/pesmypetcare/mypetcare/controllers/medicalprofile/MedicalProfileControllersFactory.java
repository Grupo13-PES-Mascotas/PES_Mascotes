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
    public static TrAddNewPetVaccination createTrAddNewVaccination() {
        return new TrAddNewPetVaccination(new MedicalProfileManagerAdapter());
    }

    /**
     * Method responsible for creating the transaction to obtain all the vaccinations of a pet.
     * @return The transaction to obtain all the vaccinations of a pet
     */
    public static TrObtainAllPetVaccinations createTrObtainAllPetVaccinations() {
        return new TrObtainAllPetVaccinations(new MedicalProfileManagerAdapter());
    }

    /**
     * Method responsible for creating the transaction to delete a vaccination from a pet.
     * @return The transaction to delete a vaccination from a pet
     */
    public static TrDeletePetVaccination createTrDeletePetVaccinations() {
        return new TrDeletePetVaccination(new MedicalProfileManagerAdapter());
    }
}
