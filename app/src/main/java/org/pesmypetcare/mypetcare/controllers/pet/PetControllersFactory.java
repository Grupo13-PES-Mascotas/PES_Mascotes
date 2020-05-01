package org.pesmypetcare.mypetcare.controllers.pet;

import org.pesmypetcare.mypetcare.services.GoogleCalendarAdapter;
import org.pesmypetcare.mypetcare.services.MealManagerAdapter;
import org.pesmypetcare.mypetcare.services.MedicationManagerAdapter;
import org.pesmypetcare.mypetcare.services.PetManagerAdapter;

/**
 * @author Albert Pinto
 */
public class PetControllersFactory {
    private PetControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for registering a new pet.
     * @return The transaction for registering a new pet
     */
    public static TrRegisterNewPet createTrRegisterNewPet() {
        return new TrRegisterNewPet(new PetManagerAdapter(), new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for updating the pet image.
     * @return The transaction for updating the pet image
     */
    public static TrUpdatePetImage createTrUpdatePetImage() {
        return new TrUpdatePetImage(new PetManagerAdapter());
    }

    /**
     * Create the transaction for deleting a pet.
     * @return The transaction for deleting a pet
     */
    public static TrDeletePet createTrDeletePet() {
        return new TrDeletePet(new PetManagerAdapter(), new MealManagerAdapter(), new MedicationManagerAdapter());
    }

    /**
     * Create the transaction for updating a pet.
     * @return The transaction for updating a pet
     */
    public static TrUpdatePet createTrUpdatePet() {
        return new TrUpdatePet(new PetManagerAdapter());
    }

    /**
     * Create the transaction for obtaining all pet images.
     * @return The transaction for obtaining all pet images
     */
    public static TrObtainAllPetImages createTrObtainAllPetImages() {
        return new TrObtainAllPetImages(new PetManagerAdapter());
    }
}
