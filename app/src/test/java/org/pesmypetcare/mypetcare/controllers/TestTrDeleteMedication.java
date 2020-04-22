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
public class TestTrDeleteMedication {
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
        originalMedication = new Medication("Filoproffin", 3, 2, 25,
            DateTime.Builder.buildDateString("2020-04-15"));
        stubMedicationService = new StubMedicationService();
        trDeleteMedication = new TrDeleteMedication(stubMedicationService);
        trNewPetMedication = new TrNewPetMedication(stubMedicationService);
    }

    @Test
    public void shouldDeleteMedication() throws MedicationAlreadyExistingException {
        int before = stubMedicationService.nMedications();
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(linux);
        trNewPetMedication.setMedication(originalMedication);
        trNewPetMedication.execute();
        trDeleteMedication.setUser(user);
        trDeleteMedication.setPet(linux);
        trDeleteMedication.setMedication(originalMedication);
        trDeleteMedication.execute();
        assertEquals("Should be equal", before , stubMedicationService.nMedications());
    }

    @Test
    public void shouldDeleteAllMeals() {
        stubMedicationService.deleteMedicationsFromPet(user, linux);
        assertEquals("Should be equal", 0, stubMedicationService.nMedications());
    }
}
