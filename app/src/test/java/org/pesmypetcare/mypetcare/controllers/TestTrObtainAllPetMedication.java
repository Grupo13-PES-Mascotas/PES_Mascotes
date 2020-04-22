package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicationService;

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
    public void shouldReturnAllPetMedication() {
        trObtainAllPetMedications.setUser(user);
        trObtainAllPetMedications.setPet(pet);
        trObtainAllPetMedications.execute();
        assertEquals("Should have the same medications", pet.getMedicationEvents(),
            stubMedicationService.findMedicationsByPet(user, pet));

    }
}