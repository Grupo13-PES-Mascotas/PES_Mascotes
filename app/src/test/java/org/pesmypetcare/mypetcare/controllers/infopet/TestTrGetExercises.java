package org.pesmypetcare.mypetcare.controllers.infopet;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.exercise.TrGetExercises;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.pets.WalkPets;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Clemente
 */
public class TestTrGetExercises {
    private User user;
    private TrGetExercises trGetExercises;
    private LatLng latLng1;
    private LatLng latLng2;
    private WalkPets walkPet;
    private WalkPets walkPet2;
    private WalkPets walkPet3;

    @Before
    public void setUp() throws org.pesmypetcare.usermanager.exceptions.InvalidFormatException, PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        Pet pet = getDinkyPet();
        user.addPet(pet);
        trGetExercises = new TrGetExercises(new StubPetManagerService());

        DateTime dateTime1 = DateTime.Builder.build(2020, 2, 2, 16, 30, 0);
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
        walkPet3.addPet(pet);

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

    @Test
    public void shouldGetAllWalks() {
        trGetExercises.setUser(user);
        trGetExercises.execute();
        List<WalkPets> walkPets = trGetExercises.getResult();
        assertEquals("Should contain first walk", walkPets.get(0).toString(), walkPet.toString());
        assertEquals("Should contain second walk", walkPets.get(1).toString(), walkPet2.toString());
        assertEquals("Should contain third walk", walkPets.get(2).toString(), walkPet3.toString());
    }

}
