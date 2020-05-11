package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.medication.TrDeleteMedication;
import org.pesmypetcare.mypetcare.controllers.medication.TrNewPetMedication;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicationService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrDeleteMedication {
    private static final int MEDICATION_DURATION = 25;
    private User user;
    private Pet linux;
    private Medication originalMedication;
    private TrDeleteMedication trDeleteMedication;
    private TrNewPetMedication trNewPetMedication;
    private StubMedicationService stubMedicationService;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        linux = new Pet("Linux");
        originalMedication = new Medication("Filoproffin", 2, 2, MEDICATION_DURATION,
            DateTime.Builder.buildDateString("2020-04-15"));
        stubMedicationService = new StubMedicationService();
        trDeleteMedication = new TrDeleteMedication(stubMedicationService);
        trNewPetMedication = new TrNewPetMedication(stubMedicationService);
    }

    @Test
    public void shouldDeleteMedication() throws MedicationAlreadyExistingException, ExecutionException,
        InterruptedException {
        final int before = stubMedicationService.nMedications();
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(linux);
        trNewPetMedication.setMedication(originalMedication);
        trNewPetMedication.execute();
        trDeleteMedication.setUser(user);
        trDeleteMedication.setPet(linux);
        trDeleteMedication.setMedication(originalMedication);
        trDeleteMedication.execute();
        assertEquals("Should have the same value", before, stubMedicationService.nMedications());
    }

    @Test
    public void shouldDeleteAllMeals() {
        stubMedicationService.deleteMedicationsFromPet(user, linux);
        assertEquals("Should be equal", 0, stubMedicationService.nMedications());
    }
}
