package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubVetVisitsManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrUpdateVetVisit {
    private static final String REASON = "Implosió";
    private static final String NEW_DATE = "1984-11-29";
    private TrUpdateVetVisit trUpdateVetVisit;
    private User user;
    private Pet pet;
    private VetVisit vetVisit;

    @Before
    public void setUp() {
        trUpdateVetVisit = new TrUpdateVetVisit(new StubVetVisitsManagerService());
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        vetVisit = new VetVisit(DateTime.Builder.buildDateString("2020-11-29"), "Carrer Major 15",
            "Combustió Espontànea");
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotUpdateVetVisitIfNotPetOwner() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trUpdateVetVisit.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trUpdateVetVisit.setPet(pet);
        trUpdateVetVisit.setVetVisit(vetVisit);
        trUpdateVetVisit.execute();
    }

    @Test
    public void shouldUpdateVetVisitBody() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trUpdateVetVisit.setUser(user);
        trUpdateVetVisit.setPet(pet);
        vetVisit.setReason(REASON);
        trUpdateVetVisit.setVetVisit(vetVisit);
        trUpdateVetVisit.execute();
        assertEquals("Should have the same reason", REASON, vetVisit.getReason());
    }

    @Test
    public void shouldUpdateVetVisitKey() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trUpdateVetVisit.setUser(user);
        trUpdateVetVisit.setPet(pet);
        trUpdateVetVisit.setVetVisit(vetVisit);
        trUpdateVetVisit.setNewDate(NEW_DATE);
        trUpdateVetVisit.execute();
    }
}
