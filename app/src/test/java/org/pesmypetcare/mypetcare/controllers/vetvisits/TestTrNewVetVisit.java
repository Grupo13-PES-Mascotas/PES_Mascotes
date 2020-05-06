package org.pesmypetcare.mypetcare.controllers.vetvisits;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.pets.VetVisitAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubVetVisitsManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrNewVetVisit {
    private TrNewVetVisit trNewVetVisit;
    private User user;
    private Pet pet;
    private VetVisit vetVisit;

    @Before
    public void setUp() {
        trNewVetVisit = new TrNewVetVisit(new StubVetVisitsManagerService());
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        vetVisit = new VetVisit(DateTime.Builder.buildDateString("2021-11-29"), "Carrer Casablanca 71",
            "Implosió");
    }

    @Test(expected = VetVisitAlreadyExistingException.class)
    public void shouldNotAddVetVisitIfAlreadyExisting() throws VetVisitAlreadyExistingException, NotPetOwnerException {
        trNewVetVisit.setUser(user);
        trNewVetVisit.setPet(pet);
        trNewVetVisit.setVetVisit(vetVisit);
        trNewVetVisit.execute();
        trNewVetVisit.execute();
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddVetVisitIfNotPetOwner() throws VetVisitAlreadyExistingException, NotPetOwnerException {
        trNewVetVisit.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAs@gmail.com", "1235"));
        trNewVetVisit.setPet(pet);
        trNewVetVisit.setVetVisit(vetVisit);
        trNewVetVisit.execute();
    }

    @Test
    public void shouldAddVetVisit() throws VetVisitAlreadyExistingException, NotPetOwnerException {
        trNewVetVisit.setUser(user);
        trNewVetVisit.setPet(pet);
        trNewVetVisit.setVetVisit(vetVisit);
        int nVisits = StubVetVisitsManagerService.nVetVisit;
        trNewVetVisit.execute();
        assertEquals("The number of vet visits should have increased by 1",
            nVisits + 1, StubVetVisitsManagerService.nVetVisit);
    }

    @After
    public void restoreDefaultData() {
        new StubVetVisitsManagerService().addStubDefaultData();
    }
}
