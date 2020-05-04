package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.pesmypetcare.mypetcare.services.VetVisitsAdapter;

/**
 * @author Xavier Campos
 */
public class VetVisitsControllersFactory {
    private VetVisitsControllersFactory() {
        // Private constructor
    }

    /**
     * Creates the transaction responsible for obtaining all the vet visits of a pet.
     * @return The transaction responsible for obtaining all the vet visits of a pet
     */
    public static TrObtainAllVetVisits createTrObtainAllVetVisits() {
        return new TrObtainAllVetVisits(new VetVisitsAdapter());
    }

    /**
     * Creates the transaction responsible for creating and adding a new vet visit to a pet.
     * @return The transaction responsible for creating and adding a new vet visit to a pet
     */
    public static TrNewVetVisit createTrNewVetVisit() {
        return new TrNewVetVisit(new VetVisitsAdapter());
    }
}
