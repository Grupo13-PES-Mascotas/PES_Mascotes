package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.medication.TrNewPetMedication;
import org.pesmypetcare.mypetcare.features.pets.Medication;
import org.pesmypetcare.mypetcare.features.pets.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicationService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertTrue;

/**
 * @author Xavier Campos
 */
public class TestTrNewPetMedication {
    private static final int MEDICATION_DURATION = 14;
    private User user;
    private Pet pet;
    private TrNewPetMedication trNewPetMedication;

    @Before
    public void setUp() {
        this.user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
        this.pet = new Pet("Linux");
        StubMedicationService stubMedicationService = new StubMedicationService();
        trNewPetMedication = new TrNewPetMedication(stubMedicationService);
        List<Medication> medication = stubMedicationService.findMedicationsByPet(user, pet);
        for (Medication med : medication) {
            pet.addEvent(med);
        }
    }

    @Test(expected = MedicationAlreadyExistingException.class)
    public void shouldNotAddAlreadyExistingMedication() throws MedicationAlreadyExistingException, ExecutionException,
        InterruptedException {
        Medication medication = getTestMedication("2020-01-12");
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(pet);
        trNewPetMedication.setMedication(medication);
        trNewPetMedication.execute();
        trNewPetMedication.execute();
    }

    private Medication getTestMedication(String s) {
        return new Medication("Filoprofen", 1, 1, MEDICATION_DURATION,
            DateTime.Builder.buildDateString(s));
    }

    @Test
    public void shouldAddMedication() throws MedicationAlreadyExistingException, ExecutionException,
        InterruptedException {
        Medication medication = getTestMedication("2017-03-10");
        trNewPetMedication.setUser(user);
        trNewPetMedication.setPet(pet);
        trNewPetMedication.setMedication(medication);
        trNewPetMedication.execute();
        assertTrue("Should be true", trNewPetMedication.isResult());
    }
}
