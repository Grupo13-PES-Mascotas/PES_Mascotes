package org.pesmypetcare.mypetcare.controllers.medication;

import org.pesmypetcare.mypetcare.services.GoogleCalendarAdapter;
import org.pesmypetcare.mypetcare.services.MedicationManagerAdapter;

/**
 * @author Albert Pinto
 */
public class MedicationControllersFactory {
    private MedicationControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for adding a new pet medication.
     * @return The transaction for adding a new pet medication
     */
    public static TrNewPetMedication createTrNewPetMedication() {
        return new TrNewPetMedication(new MedicationManagerAdapter(), new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for getting all the pet medications.
     * @return The transaction for getting all the pet medications
     */
    public static TrObtainAllPetMedications createTrObtainAllPetMedications() {
        return new TrObtainAllPetMedications(new MedicationManagerAdapter());
    }

    /**
     * Create the transaction for deleting a pet medication.
     * @return The transaction for deleting a pet medication
     */
    public static TrDeleteMedication createTrDeleteMedication() {
        return new TrDeleteMedication(new MedicationManagerAdapter(), new GoogleCalendarAdapter());
    }

    /**
     * Create the transaction for updating a pet medication.
     * @return The transaction for updating a pet medication
     */
    public static TrUpdateMedication createTrUpdateMedication() {
        return new TrUpdateMedication(new MedicationManagerAdapter());
    }
}
