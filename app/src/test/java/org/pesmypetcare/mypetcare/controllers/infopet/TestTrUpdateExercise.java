package org.pesmypetcare.mypetcare.controllers.infopet;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

/**
 * @author Albert Pinto
 */
public class TestTrUpdateExercise {
    private User user;
    private Pet pet;
    private TrUpdateExercise trUpdateExercise;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();
        trUpdateExercise = new TrUpdateExercise(new StubPetManagerService());

        pet.addExercise(new Exercise("Frisbee", "Playing at the beach",
            DateTime.Builder.buildFullString("2020-05-04T10:00:00"),
            DateTime.Builder.buildFullString("2020-05-04T11:00:00")));
        user.addPet(pet);
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddExerciseToNonOwnedPet() throws NotPetOwnerException {
        trUpdateExercise.setUser(new User("johnSmith", "johnsmith@gmail.com", "5678"));
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.execute();
    }

    @Test(expected = NotExistingExerciseException.class)
    public void shouldNotUpdateNonExistingExercise() throws NotPetOwnerException {
        trUpdateExercise.setUser(user);
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.execute();
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}
