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
    private User user;
    private Pet pet;
    private Medication medication;
    private TrUpdateMedication trUpdateMedication;
    private TrNewPetMedication trNewPetMedication;
    private StubMedicationService stubMedicationService;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        pet = new Pet("Linux");
        medication = new Medication("Filoproffin", 1, 1, 14,
            DateTime.Builder.buildDateString("2020-01-12"));
        stubMedicationService = new StubMedicationService();
        trNewPetMedication = new TrNewPetMedication(stubMedicationService);
        trUpdateMedication = new TrUpdateMedication(stubMedicationService);
    }

    @Test
    public void shouldUpdateMedicationBody() throws MedicationAlreadyExistingException {
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(pet);
        trNewPetMedication.setMedication(medication);
        trNewPetMedication.execute();
        medication.setMedicationQuantity(150000);
        trUpdateMedication.setUser(user);
        trUpdateMedication.setPet(pet);
        trUpdateMedication.setMedication(medication);
        trUpdateMedication.execute();
        assertEquals("Should be the same medication", medication, StubMedicationService.currentMedication);
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
        assertEquals("Should be the same medication", medication, StubMedicationService.currentMedication);
    }
}
