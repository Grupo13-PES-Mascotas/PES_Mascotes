package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.VaccinationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrAddNewMedicalProfile {
    private TrAddNewVaccination trAddNewVaccination;
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private User user;
    private Pet pet;
    private Vaccination vaccination;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trAddNewVaccination = new TrAddNewVaccination(stubMedicalProfileManagerService);
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        vaccination = new Vaccination("Vacuna ebola", DateTime.Builder.buildDateString("2020-04-15"));
    }

    @Test(expected = VaccinationAlreadyExistingException.class)
    public void shouldNotAddVaccinationIfAlreadyExisting() throws VaccinationAlreadyExistingException,
        NotPetOwnerException, ExecutionException, InterruptedException {
        trAddNewVaccination.setUser(user);
        trAddNewVaccination.setPet(pet);
        trAddNewVaccination.setVaccination(vaccination);
        trAddNewVaccination.execute();
        trAddNewVaccination.execute();
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddVaccinationIfNotPetOwner() throws VaccinationAlreadyExistingException,
        NotPetOwnerException, ExecutionException, InterruptedException {
        trAddNewVaccination.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAs@gmail.com", "1235"));
        trAddNewVaccination.setPet(pet);
        trAddNewVaccination.setVaccination(vaccination);
        trAddNewVaccination.execute();
    }

    @Test
    public void shouldAddVaccination() throws VaccinationAlreadyExistingException, NotPetOwnerException,
        ExecutionException, InterruptedException {
        trAddNewVaccination.setUser(user);
        trAddNewVaccination.setPet(pet);
        trAddNewVaccination.setVaccination(vaccination);
        int nVisits = StubMedicalProfileManagerService.nVaccinations;
        trAddNewVaccination.execute();
        assertEquals("The number of vaccinations should have increased by 1",
            nVisits + 1, StubMedicalProfileManagerService.nVaccinations);
    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
