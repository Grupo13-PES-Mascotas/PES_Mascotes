package org.pesmypetcare.mypetcare.controllers.medicalprofile;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubMedicalProfileManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.IllnessType;
import org.pesmypetcare.usermanager.datacontainers.pet.SeverityType;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrUpdatePetIllness {
    private static final String ILLNESS_DESCRIPTION = "Diarrea Explosiva";
    private StubMedicalProfileManagerService stubMedicalProfileManagerService;
    private TrUpdatePetIllness trUpdatePetIllness;
    private User user;
    private Pet pet;
    private Illness illness;

    @Before
    public void setUp() {
        stubMedicalProfileManagerService = new StubMedicalProfileManagerService();
        trUpdatePetIllness = new TrUpdatePetIllness(stubMedicalProfileManagerService);
        user = new User("Manolo Lama", "lamacope@gmail.com", "1234");
        pet = new Pet("Bichinho");
        pet.setOwner(user);
        illness = new Illness(
            "Vacuna ebola", DateTime.Builder.buildDateString("2020-04-17"),
            DateTime.Builder.buildDateString("2020-04-18"), SeverityType.Medium.toString(),
            IllnessType.Allergy.toString());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotUpdatePetIllnessIfNotPetOwner() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trUpdatePetIllness.setUser(user);
        pet.setOwner(new User("Tomas Roncero", "tomasAS@gmail.com", "1235"));
        trUpdatePetIllness.setPet(pet);
        trUpdatePetIllness.setIllness(illness);
        trUpdatePetIllness.execute();
    }

    @Test
    public void shouldUpdatePetIllnessBody() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trUpdatePetIllness.setUser(user);
        trUpdatePetIllness.setPet(pet);
        illness.setDescription(ILLNESS_DESCRIPTION);
        trUpdatePetIllness.setIllness(illness);
        trUpdatePetIllness.execute();
        assertEquals("Should have the same description", ILLNESS_DESCRIPTION,
            illness.getDescription());
    }

    @Test
    public void shouldUpdatePetIllnessKey() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trUpdatePetIllness.setUser(user);
        trUpdatePetIllness.setPet(pet);
        DateTime newDate = DateTime.Builder.buildDateString("2091-03-21");
        illness.setDateTime(newDate);
        trUpdatePetIllness.setIllness(illness);
        trUpdatePetIllness.execute();
        assertEquals("Should have the same date", newDate.toString(),
            illness.getDateTime().toString());
    }

    @After
    public void restoreDefaultData() {
        stubMedicalProfileManagerService.addStubDefaultData();
    }
}
