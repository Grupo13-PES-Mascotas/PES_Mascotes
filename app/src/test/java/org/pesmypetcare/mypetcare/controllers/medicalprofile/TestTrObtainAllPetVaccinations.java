package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrObtainAllPetVaccinations {
    private TrObtainAllPetVaccinations trObtainAllPetVaccinations;
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private User user;
    private Pet pet;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trObtainAllPetVaccinations = new TrObtainAllPetVaccinations(stubMedicalProfileManagerService);
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
    }

    @Test
    public void shouldReturnAllPetVaccinations() throws ExecutionException, InterruptedException {
        trObtainAllPetVaccinations.setUser(user);
        trObtainAllPetVaccinations.setPet(pet);
        trObtainAllPetVaccinations.execute();
        assertEquals("Should have the same medications", pet.getVaccinationEvents(),
            stubMedicalProfileManagerService.findVaccinationsByPet(user, pet));

    }

    @After
    public void restoreDefaulData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
