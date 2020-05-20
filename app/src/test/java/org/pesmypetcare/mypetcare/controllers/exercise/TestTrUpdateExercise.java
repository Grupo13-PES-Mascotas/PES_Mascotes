package org.pesmypetcare.mypetcare.controllers.exercise;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.events.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.NotExistingExerciseException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

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
    public void shouldNotAddExerciseToNonOwnedPet() throws NotPetOwnerException, NotExistingExerciseException,
            InvalidPeriodException, ExecutionException, InterruptedException {
        trUpdateExercise.setUser(new User("johnSmith", "johnsmith@gmail.com", "5678"));
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setOriginalStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.execute();
    }

    @Test(expected = InvalidPeriodException.class)
    public void shouldStartAndEndTheExerciseInTheSameDate() throws NotPetOwnerException, InvalidPeriodException,
            NotExistingExerciseException, ExecutionException, InterruptedException {
        trUpdateExercise.setUser(user);
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setOriginalStartDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-05T10:00:00"));
        trUpdateExercise.execute();
    }

    @Test(expected = NotExistingExerciseException.class)
    public void shouldNotUpdateNonExistingExercise() throws NotPetOwnerException, NotExistingExerciseException,
            InvalidPeriodException, ExecutionException, InterruptedException {
        trUpdateExercise.setUser(user);
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee2");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setOriginalStartDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T12:00:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T13:00:00"));
        trUpdateExercise.execute();
    }

    @Test
    public void shouldUpdateTheExercise() throws NotPetOwnerException, NotExistingExerciseException,
            InvalidPeriodException, ExecutionException, InterruptedException {
        trUpdateExercise.setUser(user);
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee time");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setOriginalStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.execute();

        assertEquals("Should update the exercise", "[{Frisbee time, Playing at the beach, 2020-05-04T10:00:00, "
            + "2020-05-04T11:00:00}]", pet.getEventsByClass(Exercise.class).toString());
    }

    @Test
    public void shouldUpdateTheExerciseTime() throws NotPetOwnerException, NotExistingExerciseException,
            InvalidPeriodException, ExecutionException, InterruptedException {
        trUpdateExercise.setUser(user);
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee time");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setOriginalStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T12:00:00"));
        trUpdateExercise.execute();

        assertEquals("Should add the exercise time", "{2020-05-04T00:00:00=120}",
            pet.getHealthInfo().getExerciseFrequency().toString());
    }

    @Test
    public void shouldUpdateTheStartDate() throws NotPetOwnerException, NotExistingExerciseException,
            InvalidPeriodException, ExecutionException, InterruptedException {
        trUpdateExercise.setUser(user);
        trUpdateExercise.setPet(pet);
        trUpdateExercise.setExerciseName("Frisbee time");
        trUpdateExercise.setExerciseDescription("Playing at the beach");
        trUpdateExercise.setOriginalStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trUpdateExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:30:00"));
        trUpdateExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trUpdateExercise.execute();

        assertEquals("Should update the exercise", "[{Frisbee time, Playing at the beach, 2020-05-04T10:30:00, "
            + "2020-05-04T11:00:00}]", pet.getEventsByClass(Exercise.class).toString());
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
