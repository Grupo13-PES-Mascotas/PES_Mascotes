package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.pesmypetcare.mypetcare.services.MedicalProfileManagerAdapter;

/**
 * @author Xavier Campos
 */
public class MedicalProfileControllersFactory {
    private MedicalProfileControllersFactory() {
        // Private constructor
    }

    public static TrAddNewVaccination createTrAddNewVaccination() {
        return new TrAddNewVaccination(new MedicalProfileManagerAdapter());
    }
}
