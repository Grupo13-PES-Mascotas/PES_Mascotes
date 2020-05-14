package org.pesmypetcare.mypetcare.controllers.infopet;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.exercise.TrAddExercise;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubGoogleCalendarService;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrAddExercise {
    private User user;
    private Pet pet;
    private TrAddExercise trAddExercise;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();
        trAddExercise = new TrAddExercise(new StubPetManagerService(), new StubGoogleCalendarService());
        user.addPet(pet);
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddExerciseToNonOwnedPet() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException, InvalidFormatException {
        trAddExercise.setUser(new User("johnSmith", "johnsmith@gmail.com", "5678"));
        trAddExercise.setPet(pet);
        trAddExercise.setExerciseName("Frisbee");
        trAddExercise.setExerciseDescription("Playing at the beach");
        trAddExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trAddExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trAddExercise.execute();
    }

    @Test(expected = InvalidPeriodException.class)
    public void shouldTheStartDateTimeBeBeforeTheEndOne() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException, InvalidFormatException {
        trAddExercise.setUser(user);
        trAddExercise.setPet(pet);
        trAddExercise.setExerciseName("Frisbee");
        trAddExercise.setExerciseDescription("Playing at the beach");
        trAddExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trAddExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trAddExercise.execute();
    }

    @Test(expected = InvalidPeriodException.class)
    public void shouldStartAndEndTheExerciseInTheSameDate() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException, InvalidFormatException {
        trAddExercise.setUser(user);
        trAddExercise.setPet(pet);
        trAddExercise.setExerciseName("Frisbee");
        trAddExercise.setExerciseDescription("Playing at the beach");
        trAddExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trAddExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-05T10:00:00"));
        trAddExercise.execute();
    }

    @Test
    public void shouldAddExercise() throws NotPetOwnerException, InvalidPeriodException, ExecutionException,
        InterruptedException, InvalidFormatException {
        trAddExercise.setUser(user);
        trAddExercise.setPet(pet);
        trAddExercise.setExerciseName("Frisbee");
        trAddExercise.setExerciseDescription("Playing at the beach");
        trAddExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trAddExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trAddExercise.execute();

        assertEquals("Should add the exercise", "[{Frisbee, Playing at the beach, 2020-05-04T10:00:00, "
            + "2020-05-04T11:00:00}]", pet.getEventsByClass(Exercise.class).toString());
    }

    @Test
    public void shouldAddTheFirstExerciseTimeInDate() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException, InvalidFormatException {
        trAddExercise.setUser(user);
        trAddExercise.setPet(pet);
        trAddExercise.setExerciseName("Frisbee");
        trAddExercise.setExerciseDescription("Playing at the beach");
        trAddExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trAddExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trAddExercise.execute();

        assertEquals("Should add the exercise time", "{2020-05-04T00:00:00=60}",
            pet.getHealthInfo().getExerciseFrequency().toString());
    }

    @Test
    public void shouldAddSomeExerciseTimesInTheSameDate() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException, InvalidFormatException {
        trAddExercise.setUser(user);
        trAddExercise.setPet(pet);
        trAddExercise.setExerciseName("Frisbee");
        trAddExercise.setExerciseDescription("Playing at the beach");
        trAddExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T10:00:00"));
        trAddExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trAddExercise.execute();

        trAddExercise.setStartDateTime(DateTime.Builder.buildFullString("2020-05-04T11:00:00"));
        trAddExercise.setEndDateTime(DateTime.Builder.buildFullString("2020-05-04T13:00:00"));
        trAddExercise.execute();

        assertEquals("Should add the exercise time", "{2020-05-04T00:00:00=180}",
            pet.getHealthInfo().getExerciseFrequency().toString());
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
