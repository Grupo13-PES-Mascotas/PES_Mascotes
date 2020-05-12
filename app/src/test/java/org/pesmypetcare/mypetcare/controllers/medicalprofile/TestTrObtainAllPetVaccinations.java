package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;

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
        pet.setOwner(user);
    }


    @Test(expected = NotPetOwnerException.class)
    public void shouldNotReturnAllPetVaccinationsIfNotPetOwner() throws NotPetOwnerException {
        trObtainAllPetVaccinations.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trObtainAllPetVaccinations.setPet(pet);
        trObtainAllPetVaccinations.execute();
    }
    @Test
    public void shouldReturnAllPetVaccinations() throws NotPetOwnerException {
        trObtainAllPetVaccinations.setUser(user);
        trObtainAllPetVaccinations.setPet(pet);
        trObtainAllPetVaccinations.execute();
        assertEquals("Should have the same medications", pet.getVaccinationEvents(),
            stubMedicalProfileManagerService.findVaccinationsByPet(user, pet));

    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
