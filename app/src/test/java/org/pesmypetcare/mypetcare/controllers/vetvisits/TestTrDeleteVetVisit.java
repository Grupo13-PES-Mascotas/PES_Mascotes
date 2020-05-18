package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubVetVisitsManagerService;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrDeleteVetVisit {
    private TrDeleteVetVisit trDeleteVetVisit;
    private User user;
    private Pet pet;
    private VetVisit vetVisit;

    @Before
    public void setUp() {
        trDeleteVetVisit = new TrDeleteVetVisit(new StubVetVisitsManagerService(), new StubGoogleCalendarService());
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        vetVisit = new VetVisit(DateTime.Builder.buildDateString("2020-11-29"), "Carrer Major 15",
            "Combustió Espontànea");
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotDeleteVetVisitIfNotPetOwner() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trDeleteVetVisit.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trDeleteVetVisit.setPet(pet);
        trDeleteVetVisit.setVetVisit(vetVisit);
        trDeleteVetVisit.execute();
    }

    @Test
    public void shouldDeleteVetVisit() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trDeleteVetVisit.setUser(user);
        trDeleteVetVisit.setPet(pet);
        trDeleteVetVisit.setVetVisit(vetVisit);
        int nVisits = StubVetVisitsManagerService.nVetVisit;
        trDeleteVetVisit.execute();
        assertEquals("The number of vet visits should have decreased by 1",
            nVisits - 1, StubVetVisitsManagerService.nVetVisit);
    }

    @After
    public void restoreDefaultData() {
        new StubVetVisitsManagerService().addStubDefaultData();
    }
}
