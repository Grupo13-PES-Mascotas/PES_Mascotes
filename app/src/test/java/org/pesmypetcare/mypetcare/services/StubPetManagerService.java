package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class StubPetManagerService implements PetManagerService {
    private static final String JOHN_DOE = "johnDoe";
    private static final String JOHN_DOE_2 = "johnDoe2";
    private static final String DINKY = "Dinky";
    private static final String LINUX = "Linux";
    private Map<String, ArrayList<Pet>> data;

    public StubPetManagerService() {
        this.data = new HashMap<>();
        this.data.put(JOHN_DOE, new ArrayList<>());
        Pet pet = new Pet(DINKY);
        pet.setWeight(10.0);
        pet.addExercise(new Exercise("Frisbee", "Playing at the beach",
            DateTime.Builder.buildFullString("2020-05-04T10:00:00"),
            DateTime.Builder.buildFullString("2020-05-04T11:00:00")));
        Objects.requireNonNull(this.data.get(JOHN_DOE)).add(pet);

        pet = new Pet(LINUX);
        pet.setWeight(10.0);
        Objects.requireNonNull(this.data.get(JOHN_DOE)).add(pet);

        this.data.put(JOHN_DOE_2, new ArrayList<>());
        for (int index = 0; index < 2; ++index) {
            pet = new Pet("pet" + index);
            Objects.requireNonNull(this.data.get(JOHN_DOE_2)).add(pet);
        }
    }

    @Override
    public void updatePet(Pet pet) {
        if (pet.getPreviousName() != null) {
            Objects.requireNonNull(data.get(pet.getOwner().getUsername())).remove(new Pet(pet.getPreviousName()));
        } else if (Objects.requireNonNull(data.containsKey(pet.getOwner().getUsername()))) {
            Objects.requireNonNull(data.get(pet.getOwner().getUsername())).remove(pet);
        }
        this.registerNewPet(pet.getOwner(), pet);
    }

    @Override
    public boolean registerNewPet(User user, Pet pet) {
        data.putIfAbsent(user.getUsername(), new ArrayList<>());
        Objects.requireNonNull(data.get(user.getUsername())).add(pet);

        return true;
    }

    @Override
    public void updatePetImage(User user, Pet pet, Bitmap newPetImage) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).setProfileImage(newPetImage);
    }
    @Override
    public void deletePet(Pet pet, User user) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        assert pets != null;
        pets.remove(pet);
    }

    @Override
    public void deletePetsFromUser(User user) {
        data.remove(user.getUsername());
    }

    @Override
    public List<Pet> findPetsByOwner(User user) {
        return data.get(user.getUsername());
    }

    @Override
    public Map<String, byte[]> getAllPetsImages(User user) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        Map<String, byte[]> result = new HashMap<>();

        Objects.requireNonNull(pets).forEach(p -> result.put(p.getName(),
            new byte[] {(byte) 0x0000FF, (byte) 0x0000FF}));

        return result;
    }

    @Override
    public void registerNewEvent(Pet pet, Event event) {
        ArrayList<Pet> pets = data.get(pet.getOwner().getUsername());
        assert pets != null;
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).addEvent(event);
    }

    @Override
    public void deleteEvent(Pet pet, Event event) {
        ArrayList<Pet> pets = data.get(pet.getOwner().getUsername());
        assert pets != null;
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).deleteEvent(event);
    }

    @Override
    public void addWeight(User user, Pet pet, double newWeight, DateTime dateTime) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        int petIndex = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(petIndex).setWeightForDate(newWeight, dateTime);
    }

    @Override
    public void deletePetWeight(User user, Pet pet, DateTime dateTime) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        int petIndex = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(petIndex).deleteWeightForDate(dateTime);
    }

    @Override
    public void addWashFrequency(User user, Pet pet, int newWashFrequency, DateTime dateTime) throws ExecutionException,
        InterruptedException {
        ArrayList<Pet> pets = data.get(user.getUsername());
        int petIndex = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(petIndex).setWashFrequencyForDate(newWashFrequency, dateTime);
    }

    @Override
    public void deletePetWashFrequency(User user, Pet pet, DateTime dateTime) {
        ArrayList<Pet> pets = data.get(user.getUsername());
        int petIndex = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(petIndex).deleteWashFrequencyForDate(dateTime);
    }

    @Override
    public void addExercise(User user, Pet pet, Exercise exercise) {
        List<Pet> pets = data.get(user.getUsername());
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).addExercise(exercise);
    }

    @Override
    public void deleteExercise(User user, Pet pet, DateTime dateTime) {
        List<Pet> pets = data.get(user.getUsername());
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).deleteExerciseForDate(dateTime);
    }

    @Override
    public void updateExercise(User user, Pet pet, DateTime originalDateTime, Exercise exercise) {
        List<Pet> pets = data.get(user.getUsername());
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).deleteExerciseForDate(originalDateTime);
        pets.get(index).addExercise(exercise);
    }

    @Override
    public void addWalking(User user, Pet pet, Walk walk) {
        List<Pet> pets = data.get(user.getUsername());
        int index = Objects.requireNonNull(pets).indexOf(pet);
        pets.get(index).addExercise(walk);
    }

    @Override
    public List<Exercise> getAllExercises(User user, Pet pet) {
        List<Pet> pets = data.get(user.getUsername());
        int index = Objects.requireNonNull(pets).indexOf(pet);

        List<Event> events = pets.get(index).getEventsByClass(Exercise.class);
        events.addAll(pets.get(index).getEventsByClass(Walk.class));
        List<Exercise> exercises = new ArrayList<>();

        for (Event event : events) {
            exercises.add((Exercise) event);
        }

        return exercises;
    }
}
