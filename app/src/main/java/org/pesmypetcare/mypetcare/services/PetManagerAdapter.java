package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.activities.threads.ThreadFactory;
import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Exercise;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.Walk;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.EventData;
import org.pesmypetcare.usermanagerlib.datacontainers.ExerciseData;
import org.pesmypetcare.usermanagerlib.datacontainers.FreqWash;
import org.pesmypetcare.usermanagerlib.datacontainers.FreqWashData;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PetManagerAdapter implements PetManagerService {
    private static final String A_REALLY_PRETTY_LOCATION = "A really pretty Location";
    private static final int EMAIL_REMINDER_MINUTES = 10;

    @Override
    public void updatePet(Pet pet) {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();
        String userToken = pet.getOwner().getToken();

        try {
            ServiceLocator.getInstance().getPetManagerClient().updateSimpleField(userToken, ownerUsername, name,
                PetManagerClient.GENDER, pet.getGender().toString());
            ServiceLocator.getInstance().getPetManagerClient().updateSimpleField(userToken, ownerUsername, name,
                PetManagerClient.BREED, pet.getBreed());
            updateHealth(pet);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        /*ServiceLocator.getInstance().getPetManagerClient().updateGender(userToken, ownerUsername, name,
            pet.getGender().toString());
        ServiceLocator.getInstance().getPetManagerClient().updateBirthday(pet.getOwner().getToken(), ownerUsername,
          name, pet.getBirthDate());
        ServiceLocator.getInstance().getPetManagerClient().updateBreed(userToken, ownerUsername,
            name, pet.getBreed());
        ServiceLocator.getInstance().getPetManagerClient().updatePathologies(pet.getOwner().getToken(),
            ownerUsername, name, pet.getPathologies());
        updateHealth(pet);*/
    }

    /**
     * Update the health data of the pet.
     * @param pet The pet to which its health data has to be updated
     */
    private void updateHealth(Pet pet) throws ExecutionException, InterruptedException {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();
        String userToken = pet.getOwner().getToken();

        /*ServiceLocator.getInstance().getPetManagerClient().updateField(userToken, ownerUsername, name,
            PetManagerClient.WEIGHT, pet.getWeight());*/
        /*ServiceLocator.getInstance().getPetManagerClient().updateWeight(pet.getOwner().getToken(), ownerUsername,
            name, pet.getWeight());
        ServiceLocator.getInstance().getPetManagerClient().updateRecKcal(pet.getOwner().getToken(), ownerUsername,
            name, pet.getRecommendedDailyKiloCalories());
        ServiceLocator.getInstance().getPetManagerClient().updateWashFreq(pet.getOwner().getToken(), ownerUsername,
            name, pet.getWashFrequency());*/
    }

    @Override
    public boolean registerNewPet(User user, Pet pet) {
        /*ServiceLocator.getInstance().getPetManagerClient().createPet(user.getToken(), user.getUsername(),
            pet.getName(), pet.getGender().toString(), pet.getBreed(), pet.getBirthDate(), pet.getWeight(),
            pet.getPathologies(), pet.getRecommendedDailyKiloCalories(), pet.getWashFrequency());*/
        org.pesmypetcare.usermanagerlib.datacontainers.Pet registerPet = getRegisterPet(pet);

        /*try {
            ServiceLocator.getInstance().getPetManagerClient()
                .createPet(user.getToken(), user.getUsername(), registerPet);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/

        return true;
    }

    /**
     * Get the register pet.
     * @param pet The pet from he application
     * @return The pet to be registered in the system
     */
    private org.pesmypetcare.usermanagerlib.datacontainers.Pet getRegisterPet(Pet pet) {
        org.pesmypetcare.usermanagerlib.datacontainers.Pet registerPet;
        registerPet = new org.pesmypetcare.usermanagerlib.datacontainers.Pet();
        PetData petData = new PetData();

        registerPet.setName(pet.getName());
        petData.setBirth("2020-05-22T00:00:00");
        petData.setBreed(pet.getBreed());
        petData.setGender(pet.getGender());
        petData.setPathologies(pet.getPathologies());
        petData.setRecommendedKcal(pet.getRecommendedDailyKiloCalories());
        //petData.setWashFreq(pet.getWashFrequency());
        //petData.setWeight(pet.getWeight());
      
        registerPet.setBody(petData);
        return registerPet;
    }

    @Override
    public void updatePetImage(User user, Pet pet, Bitmap newPetImage) {
        byte[] bytesImage = ImageManager.getDefaultBytesPetImage();

        if (pet.getProfileImage() != null) {
            bytesImage = ImageManager.getImageBytes(newPetImage);
            Thread writeImageThread = ThreadFactory.createWriteImageThread(ImageManager.PET_PROFILE_IMAGES_PATH,
                ImageManager.getPetImageName(user.getUsername(), pet.getName()), bytesImage);
            writeImageThread.start();
        }

        Thread savePetImageThread = createSavePetImageThread(user, pet, bytesImage);
        savePetImageThread.start();
    }

    /**
     * Create the save pet image thread.
     * @param user The user
     * @param pet The pet
     * @param finalBytesImage The image bytes
     * @return The thread for saving the pet image
     */
    private Thread createSavePetImageThread(User user, Pet pet, byte[] finalBytesImage) {
        return new Thread(() -> {
                try {
                    ServiceLocator.getInstance().getPetManagerClient().saveProfileImage(user.getToken(),
                        user.getUsername(), pet.getName(), finalBytesImage);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            });
    }

    @Override
    public void deletePet(Pet pet, User user) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().deletePet(user.getToken(), user.getUsername(),
                pet.getName());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        ImageManager.deleteImage(ImageManager.PET_PROFILE_IMAGES_PATH, user.getUsername() + '_' + pet.getName());
    }

    @Override
    public void deletePetsFromUser(User user) {
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            ImageManager.deleteImage(ImageManager.PET_PROFILE_IMAGES_PATH, user.getUsername() + "_" + pet.getName());
            try {
                ServiceLocator.getInstance().getPetManagerClient().deletePet(user.getToken(), user.getUsername(),
                    pet.getName());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Pet> findPetsByOwner(User user) throws PetRepeatException {
        List<org.pesmypetcare.usermanagerlib.datacontainers.Pet> userPets = null;
        try {
            userPets = ServiceLocator.getInstance().getPetManagerClient().getAllPets(user.getToken(),
                user.getUsername());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return getPets(userPets);
    }

    /**
     * Get the transformation of the pets from the server to pet instances.
     * @param userPets The pets from the server
     * @return The instances of the pets of our application
     * @throws PetRepeatException The pet is repeated
     */
    private List<Pet> getPets(List<org.pesmypetcare.usermanagerlib.datacontainers.Pet> userPets)
        throws PetRepeatException {
        List<Pet> pets = new ArrayList<>();

        for (org.pesmypetcare.usermanagerlib.datacontainers.Pet userPet : Objects.requireNonNull(userPets)) {
            if (userPet != null) {
                pets.add(decodePet(userPet));
            }
        }

        return pets;
    }

    @Override
    public Map<String, byte[]> getAllPetsImages(User user) {
        Map<String, byte[]> pets = null;
        try {
            pets = ServiceLocator.getInstance().getPetManagerClient().downloadAllProfileImages(user.getToken(),
                user.getUsername());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return pets;
    }

    public void registerNewEvent(Pet pet, Event event) throws ExecutionException, InterruptedException {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        EventData eventData = new EventData(id, pet.getName(), A_REALLY_PRETTY_LOCATION,
                event.getDescription(), EventData.BLUEBERRY, EMAIL_REMINDER_MINUTES, 0,
                event.getDateTime().toString(), event.getDateTime().toString());
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().createEvent(pet.getOwner()
                .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName(), eventData);
    }

    @Override
    public void deleteEvent(Pet pet, Event event) throws ExecutionException, InterruptedException {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        ServiceLocator.getInstance().getGoogleCalendarManagerClient().deleteEvent(pet.getOwner()
                .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName(), id);
    }

    @Override
    public void addWeight(User user, Pet pet, double newWeight, DateTime dateTime) throws ExecutionException, InterruptedException {
        org.pesmypetcare.usermanagerlib.datacontainers.Weight libraryWeight =
                new org.pesmypetcare.usermanagerlib.datacontainers.Weight(dateTime.toString(), (int)newWeight);
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                user.getUsername(), pet.getName(), PetData.WEIGHTS, libraryWeight.getKey(), libraryWeight.getBodyAsMap());

    }

    @Override
    public void deletePetWeight(User user, Pet pet, DateTime dateTime) throws ExecutionException, InterruptedException {
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(user.getToken(),
                user.getUsername(), pet.getName(), PetData.WEIGHTS, dateTime.toString());
    }

    @Override
    public void addWashFrequency(User user, Pet pet, int newWashFrequency, DateTime dateTime)
        throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String userName = user.getUsername();
        String petName = pet.getName();
        FreqWash freqWash = new FreqWash(new FreqWashData(newWashFrequency));
        ServiceLocator.getInstance().getFreqWashManagerClient().createFreqWash(accessToken, userName, petName,
            freqWash, dateTime);
    }

    @Override
    public void deletePetWashFrequency(User user, Pet pet, DateTime dateTime) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String userName = user.getUsername();
        String petName = pet.getName();
        ServiceLocator.getInstance().getFreqWashManagerClient().deleteByDate(accessToken, userName, petName, dateTime);
    }

    @Override
    public void addExercise(User user, Pet pet, Exercise exercise) throws ExecutionException, InterruptedException {
        ExerciseData libraryExerciseData = new ExerciseData(exercise.getName(), exercise.getDescription(),
                exercise.getEndTime().toString());
        org.pesmypetcare.usermanagerlib.datacontainers.Exercise libraryExercise =
                new org.pesmypetcare.usermanagerlib.datacontainers.Exercise(exercise.getDateTime().toString(), libraryExerciseData);
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                user.getUsername(), pet.getName(), PetData.EXERCISES, libraryExercise.getKey(), libraryExercise.getBodyAsMap());
    }

    @Override
    public void deleteExercise(User user, Pet pet, DateTime dateTime) throws ExecutionException, InterruptedException {
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(user.getToken(),
                user.getUsername(), pet.getName(), PetData.EXERCISES, dateTime.toString());
    }

    @Override
    public void updateExercise(User user, Pet pet, DateTime originalDateTime, Exercise exercise) throws ExecutionException, InterruptedException {
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(user.getToken(),
                user.getUsername(), pet.getName(), PetData.EXERCISES, originalDateTime.toString());
        ExerciseData libraryExerciseData = new ExerciseData(exercise.getName(), exercise.getDescription(),
                exercise.getEndTime().toString());
        org.pesmypetcare.usermanagerlib.datacontainers.Exercise libraryExercise =
                new org.pesmypetcare.usermanagerlib.datacontainers.Exercise(exercise.getDateTime().toString(), libraryExerciseData);
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                user.getUsername(), pet.getName(), PetData.EXERCISES, libraryExercise.getKey(), libraryExercise.getBodyAsMap());

    }

    @Override
    public void addWalking(User user, Pet pet, Walk walk) {
        // Not implemented yet
    }

    /**
     * Decodes the pet information from the server.
     * @param userPet The information from the server
     * @return The pet associated with that information
     * @throws PetRepeatException The pet is repeated.
     */
    private Pet decodePet(org.pesmypetcare.usermanagerlib.datacontainers.Pet userPet) throws PetRepeatException {
        PetData petData = userPet.getBody();
        Pet pet = new Pet();

        pet.setName(userPet.getName());
        pet.setGender(petData.getGender());
        pet.setBirthDate(DateTime.Builder.buildFullString(petData.getBirth()));
        /*pet.setWeight(petData.getWeight());
        pet.setWashFrequency(petData.getWashFreq());*/
        pet.setRecommendedDailyKiloCalories(petData.getRecommendedKcal());
        pet.setBreed(petData.getBreed());
        pet.setPathologies(petData.getPathologies());

        return pet;
    }
}
