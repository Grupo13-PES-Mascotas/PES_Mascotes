package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Illness;
import org.pesmypetcare.mypetcare.features.pets.IllnessAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrAddNewPetIllness {
    private TrAddNewPetIllness trAddNewPetIllness;
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private User user;
    private Pet pet;
    private Illness illness;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trAddNewPetIllness = new TrAddNewPetIllness(stubMedicalProfileManagerService);
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        illness = new Illness("Malatia xunga",
            DateTime.Builder.buildDateString("2020-12-21"), DateTime.Builder.buildDateString("2021-01-15"),
            "Terminal", "Greu");
    }

    @Test(expected = IllnessAlreadyExistingException.class)
    public void shouldNotAddVaccinationIfAlreadyExisting() throws IllnessAlreadyExistingException,
        NotPetOwnerException {
        trAddNewPetIllness.setUser(user);
        trAddNewPetIllness.setPet(pet);
        trAddNewPetIllness.setIllness(illness);
        trAddNewPetIllness.execute();
        trAddNewPetIllness.execute();
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddVaccinationIfNotPetOwner() throws NotPetOwnerException, IllnessAlreadyExistingException {
        trAddNewPetIllness.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAs@gmail.com", "1235"));
        trAddNewPetIllness.setPet(pet);
        trAddNewPetIllness.setIllness(illness);
        trAddNewPetIllness.execute();
    }

    @Test
    public void shouldAddVaccination() throws NotPetOwnerException, IllnessAlreadyExistingException {
        trAddNewPetIllness.setUser(user);
        trAddNewPetIllness.setPet(pet);
        trAddNewPetIllness.setIllness(illness);
        int nIllness = StubMedicalProfileManagerService.nIllness;
        trAddNewPetIllness.execute();
        assertEquals("The number of illnesses should have increased by 1",
            nIllness + 1, StubMedicalProfileManagerService.nIllness);
    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
