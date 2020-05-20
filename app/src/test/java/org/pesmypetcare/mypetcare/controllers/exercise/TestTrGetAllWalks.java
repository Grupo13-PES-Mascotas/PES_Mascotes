package org.pesmypetcare.mypetcare.controllers.exercise;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.controllers.exercise.TrGetAllWalks;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.pets.WalkPets;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Clemente
 */
public class TestTrGetAllWalks {
    private User user;
    private Walk walk;
    private Walk walk2;
    private Pet pet;
    private Pet pet2;
    private TrGetAllWalks trGetAllWalks;

    @Before
    public void setUp() throws PetRepeatException, InvalidFormatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet("Dinky");
        pet2 = getDinkyPet("Dinky2");
        user.addPet(pet);
        user.addPet(pet2);
        trGetAllWalks = new TrGetAllWalks();

        List<LatLng> coordinates = Arrays.asList(new LatLng(0.0, 0.0), new LatLng(1.0, 1.0));
        walk = new Walk("walk", "nice", DateTime.Builder.build(2020, 2, 2, 16, 30, 0),
            DateTime.Builder.build(2020, 2, 2, 17, 30, 0), coordinates);
        walk2 = new Walk("walk2", "nice2", DateTime.Builder.build(2020, 2, 3, 16, 30, 0),
            DateTime.Builder.build(2020, 2, 3, 17, 30, 0), coordinates);

        /*DateTime dateTime1 = DateTime.Builder.build(2020, 2, 2, 16, 30, 0);
        DateTime dateTime2 = DateTime.Builder.build(2020, 2, 2, 17, 30, 0);
        DateTime dateTime3 = DateTime.Builder.build(2020, 2, 3, 16, 30, 0);
        DateTime dateTime4 = DateTime.Builder.build(2020, 2, 4, 16, 30, 0);

        List<LatLng> coordinates = new ArrayList<>();
        coordinates.add(latLng1);
        coordinates.add(latLng2);
        Walk walk1 = new Walk("walk1", "paseito", dateTime1, dateTime2, coordinates);
        Walk walk2 = new Walk("walk2", "paseo corto", dateTime2, dateTime3, coordinates);
        Walk walk3 = new Walk("walk3", "buen paseo", dateTime3, dateTime4, coordinates);

        pet.addExercise(walk1);
        pet.addExercise(walk2);
        pet.addExercise(walk3);

        walkPet = new WalkPets(walk1);
        walkPet2 = new WalkPets(walk2);
        walkPet3 = new WalkPets(walk3);
        walkPet.addPet(pet);
        walkPet2.addPet(pet);
        walkPet3.addPet(pet);*/

    }

    @Test
    public void shouldGetWalkWithOnePet() {
        pet.addExercise(walk);
        trGetAllWalks.setUser(user);
        trGetAllWalks.execute();
        List<WalkPets> walkPetsResult = trGetAllWalks.getResult();

        WalkPets walkPets = new WalkPets(walk);
        walkPets.addPet(pet);

        assertEquals("Should get a walk with one pet", "[{walk, {Dinky}}]", walkPetsResult.toString());
    }

    @Test
    public void shouldGetWalkWithMoreThanOnePet() {
        pet.addExercise(walk);
        pet2.addExercise(walk);
        trGetAllWalks.setUser(user);
        trGetAllWalks.execute();
        List<WalkPets> walkPetsResult = trGetAllWalks.getResult();

        WalkPets walkPets = new WalkPets(walk);
        walkPets.addPet(pet);

        assertEquals("Should get a walk with more than one pet", "[{walk, {Dinky, Dinky2}}]",
            walkPetsResult.toString());
    }

    @Test
    public void shouldGetSomeWalks() {
        pet.addExercise(walk);
        pet2.addExercise(walk);
        pet2.addExercise(walk2);
        trGetAllWalks.setUser(user);
        trGetAllWalks.execute();
        List<WalkPets> walkPetsResult = trGetAllWalks.getResult();

        WalkPets walkPets = new WalkPets(walk);
        walkPets.addPet(pet);

        assertEquals("Should get a walk with more than one pet", "[{walk, {Dinky, Dinky2}}, {walk2, {Dinky2}}]",
            walkPetsResult.toString());
    }

    private Pet getDinkyPet(String name) throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName(name);
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        return pet;
    }
}
