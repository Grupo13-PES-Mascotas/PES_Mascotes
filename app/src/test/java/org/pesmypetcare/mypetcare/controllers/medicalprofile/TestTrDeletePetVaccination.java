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

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrDeletePetVaccination {
    private TrDeletePetVaccination trDeletePetVaccination;
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private User user;
    private Pet pet;
    private Vaccination vaccination;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trDeletePetVaccination = new TrDeletePetVaccination(stubMedicalProfileManagerService);
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        vaccination = new Vaccination("Vacuna ebola", DateTime.Builder.buildDateString("2020-04-15"));
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotUpdatePetVaccinationIfNotPetOwner() throws NotPetOwnerException {
        trDeletePetVaccination.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trDeletePetVaccination.setPet(pet);
        trDeletePetVaccination.setVaccination(vaccination);
        trDeletePetVaccination.execute();
    }

    @Test
    public void shouldDeleteIllness() throws NotPetOwnerException {
        final int before = StubMedicalProfileManagerService.nVaccinations;
        trDeletePetVaccination.setUser(user);
        trDeletePetVaccination.setPet(pet);
        trDeletePetVaccination.setVaccination(vaccination);
        trDeletePetVaccination.execute();
        assertEquals("Should have the same value", before - 1, StubMedicalProfileManagerService.nVaccinations);
    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
