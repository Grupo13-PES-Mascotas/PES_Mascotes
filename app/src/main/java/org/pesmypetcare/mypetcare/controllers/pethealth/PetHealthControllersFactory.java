package org.pesmypetcare.mypetcare.controllers.pethealth;

import org.pesmypetcare.mypetcare.services.pet.PetManagerAdapter;

/**
 * @author Albert Pinto
 */
public class PetHealthControllersFactory {
    private PetHealthControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for adding a new weight.
     * @return The transaction for adding a new weight
     */
    public static TrAddNewWeight createTrAddNewWeight() {
        return new TrAddNewWeight(new PetManagerAdapter());
    }

    /**
     * Create the transaction for deleting a weight.
     * @return The transaction for deleting a weight
     */
    public static TrDeleteWeight createTrDeleteWeight() {
        return new TrDeleteWeight(new PetManagerAdapter());
    }

    /**
     * Create the transaction for adding a new wash frequency.
     * @return The transaction for adding a new wash frequency
     */
    public static TrAddNewWashFrequency createTrAddNewWashFrequency() {
        return new TrAddNewWashFrequency(new PetManagerAdapter());
    }

    /**
     * Create the transaction for deleting a wash frequency.
     * @return The transaction for deleting a wash frequency
     */
    public static TrDeleteWashFrequency createTrDeleteWashFrequency() {
        return new TrDeleteWashFrequency(new PetManagerAdapter());
    }

    /**
     * Create the transaction for getting all the pets.
     * @return The transaction for getting all the pets
     */
    public static TrGetAllWeights createTrGetAllWeights() {
        return new TrGetAllWeights(new PetManagerAdapter());
    }
}
