package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.VaccinationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrAddNewPetVaccination {
    private TrAddNewPetVaccination trAddNewPetVaccination;
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private User user;
    private Pet pet;
    private Vaccination vaccination;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trAddNewPetVaccination = new TrAddNewPetVaccination(stubMedicalProfileManagerService,
            new StubGoogleCalendarService());
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        vaccination = new Vaccination("Vacuna ebola", DateTime.Builder.buildDateString("2020-04-15"));
    }

    @Test(expected = VaccinationAlreadyExistingException.class)
    public void shouldNotAddVaccinationIfAlreadyExisting() throws VaccinationAlreadyExistingException,
        NotPetOwnerException, ExecutionException, InterruptedException, InvalidFormatException {
        trAddNewPetVaccination.setUser(user);
        trAddNewPetVaccination.setPet(pet);
        trAddNewPetVaccination.setVaccination(vaccination);
        trAddNewPetVaccination.execute();
        trAddNewPetVaccination.execute();
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddVaccinationIfNotPetOwner() throws VaccinationAlreadyExistingException,
        NotPetOwnerException, ExecutionException, InterruptedException, InvalidFormatException {
        trAddNewPetVaccination.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAs@gmail.com", "1235"));
        trAddNewPetVaccination.setPet(pet);
        trAddNewPetVaccination.setVaccination(vaccination);
        trAddNewPetVaccination.execute();
    }

    @Test
    public void shouldAddVaccination() throws VaccinationAlreadyExistingException, NotPetOwnerException,
        ExecutionException, InterruptedException, InvalidFormatException {
        trAddNewPetVaccination.setUser(user);
        trAddNewPetVaccination.setPet(pet);
        trAddNewPetVaccination.setVaccination(new Vaccination("Vacuna ebola2",
                DateTime.Builder.buildDateString("2020-04-18")));
        int nVisits = StubMedicalProfileManagerService.nVaccinations;
        trAddNewPetVaccination.execute();
        assertEquals("The number of vaccinations should have increased by 1",
            nVisits + 1, StubMedicalProfileManagerService.nVaccinations);
    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
