package org.pesmypetcare.mypetcare.controllers.exercise;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.controllers.exercise.TrGetAllExercises;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrGetAllExercises {
    private User user;
    private Pet pet;
    private Pet pet2;
    private TrGetAllExercises trGetAllExercises;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getPet("Dinky");
        pet2 = getPet("Linux");
        trGetAllExercises = new TrGetAllExercises(new StubPetManagerService());

        user.addPet(pet);
        user.addPet(pet2);
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotGetTheExercisesFromNonOwnedPet() throws NotPetOwnerException,
        ExecutionException, InterruptedException {
        trGetAllExercises.setUser(new User("johnSmith", "johnsmith@gmail.com", "5678"));
        trGetAllExercises.setPet(pet);
        trGetAllExercises.execute();
    }

    @Test
    public void shouldReturnAnEmptyListIfNoExercises() throws NotPetOwnerException, ExecutionException,
        InterruptedException {
        trGetAllExercises.setUser(user);
        trGetAllExercises.setPet(pet2);
        trGetAllExercises.execute();

        assertEquals("Should return an empty list if there are no exercises", "[]",
            trGetAllExercises.getResult().toString());
    }

    @Test
    public void shouldReturnAllSimpleExercises() throws NotPetOwnerException, ExecutionException, InterruptedException {
        trGetAllExercises.setUser(user);
        trGetAllExercises.setPet(pet);
        trGetAllExercises.execute();

        assertEquals("Should return all the exercises of the pet",
            "[{Frisbee, Playing at the beach, 2020-05-04T10:00:00, 2020-05-04T11:00:00}]",
            trGetAllExercises.getResult().toString());
    }

    private Pet getPet(String petName) throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName(petName);
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        return pet;
    }
}
