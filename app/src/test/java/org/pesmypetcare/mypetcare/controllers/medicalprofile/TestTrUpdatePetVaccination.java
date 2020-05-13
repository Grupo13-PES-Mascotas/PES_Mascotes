package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrUpdatePetVaccination {
    private static final String VACCINATION_DESCRIPTION = "Vacuna anti-explosions";
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private TrUpdatePetVaccination trUpdatePetVaccination;
    private User user;
    private Pet pet;
    private Vaccination vaccination;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trUpdatePetVaccination = new TrUpdatePetVaccination(stubMedicalProfileManagerService);
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        vaccination = new Vaccination("Vacuna ebola", DateTime.Builder.buildDateString("2020-04-15"));
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotUpdatePetVaccinationIfNotPetOwner() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trUpdatePetVaccination.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trUpdatePetVaccination.setPet(pet);
        trUpdatePetVaccination.setVaccination(vaccination);
        trUpdatePetVaccination.execute();
    }

    @Test
    public void shouldUpdatePetVaccinationBody() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trUpdatePetVaccination.setUser(user);
        trUpdatePetVaccination.setPet(pet);
        vaccination.setDescription(VACCINATION_DESCRIPTION);
        trUpdatePetVaccination.setVaccination(vaccination);
        trUpdatePetVaccination.execute();
        assertEquals("Should have the same description", VACCINATION_DESCRIPTION,
            vaccination.getDescription());
    }

    @Test
    public void shouldUpdatePetVaccinationKey() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trUpdatePetVaccination.setUser(user);
        trUpdatePetVaccination.setPet(pet);
        DateTime newDate = DateTime.Builder.buildDateString("2091-03-21");
        vaccination.setVaccinationDate(newDate);
        trUpdatePetVaccination.setVaccination(vaccination);
        trUpdatePetVaccination.execute();
        assertEquals("Should have the same date", newDate.toString(),
            vaccination.getVaccinationDate().toString());
    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
