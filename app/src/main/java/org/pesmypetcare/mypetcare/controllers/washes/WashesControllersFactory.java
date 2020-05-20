package org.pesmypetcare.mypetcare.controllers.washes;

import org.pesmypetcare.mypetcare.services.googlecalendar.GoogleCalendarAdapter;
import org.pesmypetcare.mypetcare.services.wash.WashManagerAdapter;

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
    public static TrNewPetWash createTrNewPetWash() {
        return new TrNewPetWash(new WashManagerAdapter(), new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for obtaining all pet washes.
     * @return The transaction for obtaining all pet Washes
     */
    public static TrObtainAllPetWashes createTrObtainAllPetWashes() {
        return new TrObtainAllPetWashes(new WashManagerAdapter());
    }

    /**
     * Create the transaction for deleting a Wash.
     * @return The transaction for deleting a Wash
     */
    public static TrDeleteWash createTrDeleteWash() {
        return new TrDeleteWash(new WashManagerAdapter(), new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for updating a Wash.
     * @return The transaction for updating a Wash
     */
    public static TrUpdateWash createTrUpdateWash() {
        return new TrUpdateWash(new WashManagerAdapter());
    }
}
