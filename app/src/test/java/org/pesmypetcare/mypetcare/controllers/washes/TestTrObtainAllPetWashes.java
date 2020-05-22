package org.pesmypetcare.mypetcare.controllers.washes;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.washes.TrObtainAllPetWashes;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubWashManagerService;

import static org.junit.Assert.assertEquals;

/**
 * @author Enric Hernando
 */
public class TestTrObtainAllPetWashes {
    private TrObtainAllPetWashes trObtainAllPetWashes;
    private StubWashManagerService stubWashManagerService;
    private Pet pet;
    private User user;

    @Before
    public void setUp() {
        stubWashManagerService = new StubWashManagerService();
        trObtainAllPetWashes = new TrObtainAllPetWashes(stubWashManagerService);
        pet = new Pet("Linux");
        user = new User("johnDoe", "johndoe@gmail.com", "PASSWORD");
    }

    @Test
    public void shouldReturnAllPetMedication() {
        trObtainAllPetWashes.setUser(user);
        trObtainAllPetWashes.setPet(pet);
        trObtainAllPetWashes.execute();
        assertEquals("Should have the same wash", pet.getWashEvents(),
                stubWashManagerService.findWashesByPet(user, pet));

    }
}
