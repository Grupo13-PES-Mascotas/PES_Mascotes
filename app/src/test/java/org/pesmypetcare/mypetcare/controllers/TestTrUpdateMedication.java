package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicationService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrUpdateMedication {
    private static final int MEDICATION_DURATION = 14;
    private static final int MEDICATION_QUANTITY = 150000;
    private static final String SHOULD_BE_THE_SAME_MEDICATION = "Should be the same medication";
    private User user;
    private Pet pet;
    private Medication medication;
    private TrUpdateMedication trUpdateMedication;
    private TrNewPetMedication trNewPetMedication;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        pet = new Pet("Linux");
        medication = new Medication("Filoproffin", 1, 1, MEDICATION_DURATION,
            DateTime.Builder.buildDateString("2020-01-12"));
        StubMedicationService stubMedicationService = new StubMedicationService();
        trNewPetMedication = new TrNewPetMedication(stubMedicationService);
        trUpdateMedication = new TrUpdateMedication(stubMedicationService);
    }

    @Test
    public void shouldUpdateMedicationBody() throws MedicationAlreadyExistingException {
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(pet);
        trNewPetMedication.setMedication(medication);
        trNewPetMedication.execute();
        medication.setMedicationQuantity(MEDICATION_QUANTITY);
        trUpdateMedication.setUser(user);
        trUpdateMedication.setPet(pet);
        trUpdateMedication.setMedication(medication);
        trUpdateMedication.execute();
        assertEquals(SHOULD_BE_THE_SAME_MEDICATION, medication, StubMedicationService.getCurrentMedication());
    }

    @Test
    public void shouldUpdateMedicationDate() throws MedicationAlreadyExistingException {
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(pet);
        trNewPetMedication.setMedication(medication);
        trNewPetMedication.execute();
        medication.setMedicationDate(DateTime.Builder.buildDateString("2077-11-11"));
        trUpdateMedication.setUser(user);
        trUpdateMedication.setPet(pet);
        trUpdateMedication.setMedication(medication);
        trUpdateMedication.execute();
        assertEquals(SHOULD_BE_THE_SAME_MEDICATION, medication, StubMedicationService.getCurrentMedication());
    }
}
