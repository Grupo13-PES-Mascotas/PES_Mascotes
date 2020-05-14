package org.pesmypetcare.mypetcare.controllers.infopet;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.exercise.TrDeleteExercise;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrDeleteExercise {
    private User user;
    private Pet pet;
    private TrDeleteExercise trDeleteExercise;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();
        trDeleteExercise = new TrDeleteExercise(new StubPetManagerService());

        pet.addExercise(new Exercise("Frisbee", "Playing at the beach",
            DateTime.Builder.buildFullString("2020-05-04T10:00:00"),
            DateTime.Builder.buildFullString("2020-05-04T11:00:00")));
        user.addPet(pet);
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotDeleteExerciseToNonOwnedPet() throws NotPetOwnerException, NotExistingExerciseException, ExecutionException, InterruptedException {
        trDeleteExercise.setUser(new User("johnSmith", "johnsmith@gmail.com", "5678"));
        trDeleteExercise.setPet(pet);
        trDeleteExercise.setExerciseDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trDeleteExercise.execute();
    }

    @Test(expected = NotExistingExerciseException.class)
    public void shouldNotDeleteNonExistingExercise() throws NotPetOwnerException, NotExistingExerciseException, ExecutionException, InterruptedException {
        trDeleteExercise.setUser(user);
        trDeleteExercise.setPet(pet);
        trDeleteExercise.setExerciseDateTime(DateTime.Builder.buildFullString("2020-05-03T10:00:00"));
        trDeleteExercise.execute();
    }

    @Test
    public void shouldDeleteExercise() throws NotPetOwnerException, NotExistingExerciseException, ExecutionException, InterruptedException {
        trDeleteExercise.setUser(user);
        trDeleteExercise.setPet(pet);
        trDeleteExercise.setExerciseDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trDeleteExercise.execute();

        assertEquals("Should delete exercise", "[]", pet.getEventsByClass(Exercise.class).toString());
    }

    @Test
    public void shouldDeleteExerciseTime() throws NotPetOwnerException, NotExistingExerciseException, ExecutionException, InterruptedException {
        trDeleteExercise.setUser(user);
        trDeleteExercise.setPet(pet);
        trDeleteExercise.setExerciseDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trDeleteExercise.execute();

        assertEquals("Should delete exercise time", "{}", pet.getHealthInfo().getExerciseFrequency().toString());
    }

    @Test
    public void shouldDecrementExerciseTime() throws NotPetOwnerException, NotExistingExerciseException, ExecutionException, InterruptedException {
        pet.addExercise(new Exercise("Running", "Running on the beach",
            DateTime.Builder.buildFullString("2020-05-04T08:00:00"),
            DateTime.Builder.buildFullString("2020-05-04T09:00:00")));
        trDeleteExercise.setUser(user);
        trDeleteExercise.setPet(pet);
        trDeleteExercise.setExerciseDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trDeleteExercise.execute();

        assertEquals("Should delete exercise time", "{2020-05-04T00:00:00=60}",
            pet.getHealthInfo().getExerciseFrequency().toString());
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        return pet;
    }
}
