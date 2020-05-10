package org.pesmypetcare.mypetcare.controllers.infopet;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.exercise.TrAddWalking;
import org.pesmypetcare.mypetcare.features.pets.InvalidPeriodException;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.Walking;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrAddWalking {
    private static final int COORDINATES_NUMBER = 5;
    private User user;
    private List<Pet> pets;
    private List<LatLng> coordinates;
    private TrAddWalking trAddWalking;

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

        trAddWalking = new TrAddWalking(new StubPetManagerService());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotAddWalkingToNonOwnerPet() throws NotPetOwnerException, InvalidPeriodException {
        trAddWalking.setUser(new User("johnSmith", "johnsmith@gmail.com", "5678"));
        trAddWalking.setPets(pets);
        trAddWalking.setName("A walk for the neighbourhood");
        trAddWalking.setDescription("Walking on the near surroundings of our home");
        trAddWalking.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalking.setEndDateTime(DateTime.Builder.buildFullString("2020-05-10T11:00:00"));
        trAddWalking.setCoordinates(coordinates);
        trAddWalking.execute();
    }

    @Test(expected = InvalidPeriodException.class)
    public void shouldTheStartDateTimeBeBeforeTheEndOne() throws NotPetOwnerException, InvalidPeriodException {
        trAddWalking.setUser(user);
        trAddWalking.setPets(pets);
        trAddWalking.setName("A walk for the neighbourhood");
        trAddWalking.setDescription("Walking on the near surroundings of our home");
        trAddWalking.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T11:00:00"));
        trAddWalking.setEndDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalking.setCoordinates(coordinates);
        trAddWalking.execute();
    }

    @Test(expected = InvalidPeriodException.class)
    public void shouldStartAndEndTheExerciseInTheSameDate() throws NotPetOwnerException, InvalidPeriodException {
        trAddWalking.setUser(user);
        trAddWalking.setPets(pets);
        trAddWalking.setName("A walk for the neighbourhood");
        trAddWalking.setDescription("Walking on the near surroundings of our home");
        trAddWalking.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalking.setEndDateTime(DateTime.Builder.buildFullString("2020-05-11T11:00:00"));
        trAddWalking.setCoordinates(coordinates);
        trAddWalking.execute();
    }

    @Test
    public void shouldAddWalking() throws NotPetOwnerException, InvalidPeriodException {
        trAddWalking.setUser(user);
        trAddWalking.setPets(pets);
        trAddWalking.setName("A walk for the neighbourhood");
        trAddWalking.setDescription("Walking on the near surroundings of our home");
        trAddWalking.setStartDateTime(DateTime.Builder.buildFullString("2020-05-10T10:00:00"));
        trAddWalking.setEndDateTime(DateTime.Builder.buildFullString("2020-05-10T11:00:00"));
        trAddWalking.setCoordinates(coordinates);
        trAddWalking.execute();

        for (Pet pet : pets) {
            assertEquals("Should add walking", "[{A walk for the neighbourhood, "
                + "Walking on the near surroundings of our home, 2020-05-10T10:00:00, 2020-05-10T11:00:00, "
                + "[lat/lng: (0.0,0.0), lat/lng: (1.0,1.0), lat/lng: (2.0,2.0), lat/lng: (3.0,3.0), "
                    + "lat/lng: (4.0,4.0)]}]",
                pet.getEventsByClass(Walking.class).toString());
        }
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
