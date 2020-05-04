package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubVetVisitsManagerService;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrObtainAllVetVisits {
    private TrObtainAllVetVisits trObtainAllVetVisits;
    private User user;
    private Pet pet;

    @Before
    public void setUp() {
        trObtainAllVetVisits = new TrObtainAllVetVisits(new StubVetVisitsManagerService());
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
    }

    @Test
    public void shouldObtainAllPetVetVisits() {
        trObtainAllVetVisits.setUser(user);
        trObtainAllVetVisits.setPet(pet);
        trObtainAllVetVisits.execute();
        assertEquals("Should have the same vet visits", trObtainAllVetVisits.getResult(), pet.getVetVisitEvents());
    }
}
