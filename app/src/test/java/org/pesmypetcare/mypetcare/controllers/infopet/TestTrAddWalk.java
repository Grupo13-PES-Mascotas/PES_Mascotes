package org.pesmypetcare.mypetcare.controllers.infopet;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.exercise.TrAddWalk;
import org.pesmypetcare.mypetcare.features.pets.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrAddWalk {
    private static final int COORDINATES_NUMBER = 5;
    private User user;
    private List<Pet> pets;
    private List<LatLng> coordinates;
    private TrAddWalk trAddWalk;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pets = new ArrayList<>();
        coordinates = new ArrayList<>();

        Pet pet = getDinkyPet();
        user.addPet(pet);
        pets.add(pet);

        for (int actual = 0; actual < COORDINATES_NUMBER; ++actual) {
            coordinates.add(new LatLng(actual, actual));
        }

        trAddWalk = new TrAddWalk(new StubPetManagerService());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddWalkingToNonOwnerPet() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException {
        trAddWalk.setUser(new User("johnSmith", "johnsmith@gmail.com", "5678"));
        trAddWalk.setPets(pets);
        trAddWalk.setName("A walk for the neighbourhood");
        trAddWalk.setDescription("Walking on the near surroundings of our home");
        trAddWalk.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalk.setEndDateTime(DateTime.Builder.buildFullString("2020-05-10T11:00:00"));
        trAddWalk.setCoordinates(coordinates);
        trAddWalk.execute();
    }

    @Test(expected = InvalidPeriodException.class)
    public void shouldTheStartDateTimeBeBeforeTheEndOne() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException {
        trAddWalk.setUser(user);
        trAddWalk.setPets(pets);
        trAddWalk.setName("A walk for the neighbourhood");
        trAddWalk.setDescription("Walking on the near surroundings of our home");
        trAddWalk.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T11:00:00"));
        trAddWalk.setEndDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalk.setCoordinates(coordinates);
        trAddWalk.execute();
    }

    @Test(expected = InvalidPeriodException.class)
    public void shouldStartAndEndTheExerciseInTheSameDate() throws NotPetOwnerException, InvalidPeriodException,
        ExecutionException, InterruptedException {
        trAddWalk.setUser(user);
        trAddWalk.setPets(pets);
        trAddWalk.setName("A walk for the neighbourhood");
        trAddWalk.setDescription("Walking on the near surroundings of our home");
        trAddWalk.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalk.setEndDateTime(DateTime.Builder.buildFullString("2020-05-11T11:00:00"));
        trAddWalk.setCoordinates(coordinates);
        trAddWalk.execute();
    }

    @Test
    public void shouldAddWalking() throws NotPetOwnerException, InvalidPeriodException, ExecutionException,
        InterruptedException {
        trAddWalk.setUser(user);
        trAddWalk.setPets(pets);
        trAddWalk.setName("A walk for the neighbourhood");
        trAddWalk.setDescription("Walking on the near surroundings of our home");
        trAddWalk.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalk.setEndDateTime(DateTime.Builder.buildFullString("2020-05-10T11:00:00"));
        trAddWalk.setCoordinates(coordinates);
        trAddWalk.execute();

        for (Pet pet : pets) {
            assertEquals("Should add walking", "[{A walk for the neighbourhood, "
                + "Walking on the near surroundings of our home, 2020-05-10T10:00:00, 2020-05-10T11:00:00, "
                + "[lat/lng: (0.0,0.0), lat/lng: (1.0,1.0), lat/lng: (2.0,2.0), lat/lng: (3.0,3.0), "
                    + "lat/lng: (4.0,4.0)]}]",
                pet.getEventsByClass(Walk.class).toString());
        }
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
