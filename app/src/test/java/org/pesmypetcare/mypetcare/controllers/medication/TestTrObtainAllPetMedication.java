package org.pesmypetcare.mypetcare.controllers.medication;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.medication.TrObtainAllPetMedications;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicationService;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrObtainAllPetMedication {
    private TrObtainAllPetMedications trObtainAllPetMedications;
    private StubMedicationService stubMedicationService;
    private Pet pet;
    private User user;

    @Before
    public void setUp() {
        stubMedicationService = new StubMedicationService();
        trObtainAllPetMedications = new TrObtainAllPetMedications(stubMedicationService);
        pet = new Pet("Linux");
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
    }

    @Test
    public void shouldReturnAllPetMedication() throws ExecutionException, InterruptedException {
        trObtainAllPetMedications.setUser(user);
        trObtainAllPetMedications.setPet(pet);
        trObtainAllPetMedications.execute();
        assertEquals("Should have the same medications", pet.getMedicationEvents(),
            stubMedicationService.findMedicationsByPet(user, pet));

    }
}
