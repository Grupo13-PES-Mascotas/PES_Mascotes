package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Enric Hernando
 */
public class TestTrObtainAllPetIllness {
    private TrObtainAllPetIllness trObtainAllPetIllness;
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private User user;
    private Pet pet;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trObtainAllPetIllness = new TrObtainAllPetIllness(stubMedicalProfileManagerService);
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
    }


    @Test(expected = NotPetOwnerException.class)
    public void shouldNotReturnAllPetIllnessIfNotPetOwner() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trObtainAllPetIllness.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trObtainAllPetIllness.setPet(pet);
        trObtainAllPetIllness.execute();
    }
    @Test
    public void shouldReturnAllPetIllness() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trObtainAllPetIllness.setUser(user);
        trObtainAllPetIllness.setPet(pet);
        trObtainAllPetIllness.execute();
        assertEquals("Should have the same illnesses", pet.getIllnessEvents(),
                stubMedicalProfileManagerService.findIllnessesByPet(user, pet));

    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
