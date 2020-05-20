package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.IllnessType;
import org.pesmypetcare.usermanager.datacontainers.pet.SeverityType;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Enric Hernando
 */
public class TestTrDeletePetIllness {
    private TrDeletePetIllness trDeletePetIllness;
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private User user;
    private Pet pet;
    private Illness illness;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trDeletePetIllness = new TrDeletePetIllness(stubMedicalProfileManagerService, new StubGoogleCalendarService());
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        illness = new Illness("Vacuna ebola", DateTime.Builder.buildDateString("2020-04-15"),
                DateTime.Builder.buildDateString("2020-04-15"), IllnessType.Allergy.toString(),
                SeverityType.Low.toString());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotUpdatePetIllnessIfNotPetOwner() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trDeletePetIllness.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trDeletePetIllness.setPet(pet);
        trDeletePetIllness.setIllness(illness);
        trDeletePetIllness.execute();
    }

    @Test
    public void shouldDeleteIllness() throws NotPetOwnerException, ExecutionException, InterruptedException {
        final int before = StubMedicalProfileManagerService.nIllnesses;
        trDeletePetIllness.setUser(user);
        trDeletePetIllness.setPet(pet);
        trDeletePetIllness.setIllness(illness);
        trDeletePetIllness.execute();
        assertEquals("Should have the same value", before - 1, StubMedicalProfileManagerService.nIllnesses);
    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
