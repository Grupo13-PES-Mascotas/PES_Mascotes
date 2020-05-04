package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.mypetcare.services.WashManagerAdapter;

/**
 * @author Enric Hernando
 */
public class WashesControllersFactory {
    private WashesControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for adding a new pet wash.
     * @return The transaction for adding a new pet wash
     */
    public static TrNewPetWash createTrNewPetMeal() {
        return new TrNewPetWash(new WashManagerAdapter());
    }

}
