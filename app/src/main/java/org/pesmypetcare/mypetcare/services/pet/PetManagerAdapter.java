package org.pesmypetcare.mypetcare.services.pet;

import android.graphics.Bitmap;
import android.util.Pair;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.activities.threads.ThreadFactory;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.events.Event;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.Exercise;
import org.pesmypetcare.mypetcare.features.pets.events.exercise.walk.Walk;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanager.datacontainers.EventData;
import org.pesmypetcare.usermanager.datacontainers.pet.ExerciseData;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.pesmypetcare.usermanager.datacontainers.pet.Weight;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Albert Pinto
 */
public class PetManagerAdapter implements PetManagerService {
    private static final String A_REALLY_PRETTY_LOCATION = "A really pretty Location";
    private static final int EMAIL_REMINDER_MINUTES = 10;
    private static final int TIME = 20;
    private byte[] bytes;

    @Override
    public void updatePet(Pet pet) {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();
        String userToken = pet.getOwner().getToken();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().updateSimpleField(userToken, ownerUsername, name,
                    PetData.BREED, pet.getBreed());
                ServiceLocator.getInstance().getPetManagerClient().updateSimpleField(userToken, ownerUsername, name,
                    PetData.GENDER, pet.getGender().toString());
                ServiceLocator.getInstance().getPetManagerClient().updateSimpleField(userToken, ownerUsername, name,
                    PetData.PATHOLOGIES, pet.getPathologies());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Update the health data of the pet.
     * @param pet The pet to which its health data has to be updated
     */
    private void updateHealth(Pet pet) {
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
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            org.pesmypetcare.usermanager.datacontainers.pet.Pet libraryPet = getRegisterPet(pet);
            try {
                ServiceLocator.getInstance().getPetManagerClient().createPet(user.getToken(), user.getUsername(),
                    libraryPet);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }

            Pair<DateTime, Double> entry = pet.getLastWeightInfo();
            Weight libraryWeight = new Weight(entry.first.toString(), entry.second);
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.WEIGHTS, libraryWeight.getKey(),
                    libraryWeight.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGoogleCalendarManagerClient().createSecondaryCalendar(pet.getOwner()
                    .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();

        return true;
    }

    /**
     * Get the register pet.
     * @param pet The pet from he application
     * @return The pet to be registered in the system
     */
    private org.pesmypetcare.usermanager.datacontainers.pet.Pet getRegisterPet(Pet pet) {
        org.pesmypetcare.usermanager.datacontainers.pet.Pet registerPet;
        registerPet = new org.pesmypetcare.usermanager.datacontainers.pet.Pet();
        PetData petData = new PetData();

        registerPet.setName(pet.getName());
        petData.setBirth(pet.getBirthDate());
        petData.setBreed(pet.getBreed());
        petData.setGender(pet.getGender());
        petData.setPathologies(pet.getPathologies());
        petData.setRecommendedKcal(pet.getLastRecommendedDailyKiloCalories());
      
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
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deletePet(Pet pet, User user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deletePet(user.getToken(), user.getUsername(),
                        pet.getName());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        ImageManager.deleteImage(ImageManager.PET_PROFILE_IMAGES_PATH, user.getUsername()
                + '_' + pet.getName());
    }

    @Override
    public void deletePetsFromUser(User user) {
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            ImageManager.deleteImage(ImageManager.PET_PROFILE_IMAGES_PATH, user.getUsername()
                    + "_" + pet.getName());
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    ServiceLocator.getInstance().getPetManagerClient().deletePet(user.getToken(), user.getUsername(),
                            pet.getName());
                } catch (MyPetCareException e) {
                    e.printStackTrace();
                }
            });
            executorService.shutdown();
        }
    }

    @Override
    public List<Pet> findPetsByOwner(User user) throws PetRepeatException {
        AtomicReference<List<org.pesmypetcare.usermanager.datacontainers.pet.Pet>> userPets = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                userPets.set(ServiceLocator.getInstance().getPetManagerClient().getAllPets(user.getToken(),
                        user.getUsername()));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return getPets(userPets.get());
    }

    /**
     * Get the transformation of the pets from the server to pet instances.
     * @param userPets The pets from the server
     * @return The instances of the pets of our application
     * @throws PetRepeatException The pet is repeated
     */
    private List<Pet> getPets(List<org.pesmypetcare.usermanager.datacontainers.pet.Pet> userPets)
        throws PetRepeatException {
        List<Pet> pets = new ArrayList<>();
        for (org.pesmypetcare.usermanager.datacontainers.pet.Pet userPet : Objects.requireNonNull(userPets)) {
            if (userPet != null) {
                pets.add(decodePet(userPet));
            }
        }

        return pets;
    }

    @Override
    public Map<String, byte[]> getAllPetsImages(User user) {
        AtomicReference<Map<String, byte[]>> pets = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                pets.set(ServiceLocator.getInstance().getPetManagerClient().downloadAllProfileImages(user.getToken(),
                        user.getUsername()));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pets.get();
    }

    /**
     * Register a new event for the given pet.
     * @param pet The pet for which the event has to be added
     * @param event The event that has to be added
     */
    public void registerNewEvent(Pet pet, Event event) {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        registerNewEventLibraryCall(pet, event, id);
    }

    /**
     * Method responsible for calling the library to register a new event.
     * @param pet The pet for which the event has to be added
     * @param event The event that has to be added
     * @param id The id of the event
     */
    private void registerNewEventLibraryCall(Pet pet, Event event, String id) {
        EventData eventData = new EventData(id, pet.getName(), A_REALLY_PRETTY_LOCATION,
                event.getDescription(), EventData.BLUEBERRY, EMAIL_REMINDER_MINUTES, 0,
                event.getDateTime().toString(), event.getDateTime().toString());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGoogleCalendarManagerClient().createEvent(pet.getOwner()
                        .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName(), eventData);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deleteEvent(Pet pet, Event event) {
        String id = (pet.getName() + event.getDateTime().getDay() + event.getDateTime().getMonth()
                + event.getDateTime().getYear() + event.getDescription()).toLowerCase();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGoogleCalendarManagerClient().deleteEvent(pet.getOwner()
                        .getGoogleCalendarToken(), pet.getOwner().getUsername(), pet.getName(), id);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void addWeight(User user, Pet pet, double newWeight, DateTime dateTime) {
        Weight libraryWeight = new Weight(dateTime.toString(), newWeight);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.WEIGHTS, libraryWeight.getKey(),
                        libraryWeight.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deletePetWeight(User user, Pet pet, DateTime dateTime) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.WEIGHTS, dateTime.toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void addWashFrequency(User user, Pet pet, int newWashFrequency, DateTime dateTime) {
        /*String accessToken = user.getToken();
        String userName = user.getUsername();
        String petName = pet.getName();

        FreqWash freqWash = new FreqWash(new FreqWashData(newWashFrequency));
        ServiceLocator.getInstance().getFreqWashManagerClient().createFreqWash(accessToken, userName, petName,
            freqWash, dateTime);*/
    }

    @Override
    public void deletePetWashFrequency(User user, Pet pet, DateTime dateTime) {
        /*String accessToken = user.getToken();
        String userName = user.getUsername();
        String petName = pet.getName();
        ServiceLocator.getInstance().getFreqWashManagerClient().deleteByDate(accessToken, userName, petName, dateTime);
         */
    }

    @Override
    public void addExercise(User user, Pet pet, Exercise exercise) {
        ExerciseData libraryExerciseData = new ExerciseData(exercise.getName(), exercise.getDescription(),
                exercise.getEndTime().toString());
        org.pesmypetcare.usermanager.datacontainers.pet.Exercise libraryExercise =
                new org.pesmypetcare.usermanager.datacontainers.pet.Exercise(exercise.getDateTime().toString(),
                    libraryExerciseData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.EXERCISES, libraryExercise.getKey(),
                        libraryExercise.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deleteExercise(User user, Pet pet, DateTime dateTime) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.EXERCISES, dateTime.toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateExercise(User user, Pet pet, DateTime originalDateTime, Exercise exercise) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.EXERCISES, originalDateTime.toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            ExerciseData libraryExerciseData = new ExerciseData(exercise.getName(), exercise.getDescription(),
                    exercise.getEndTime().toString());

            if (exercise instanceof Walk) {
                libraryExerciseData.setCoordinates(((Walk) exercise).getCoordinates());
            }

            org.pesmypetcare.usermanager.datacontainers.pet.Exercise libraryExercise =
                    new org.pesmypetcare.usermanager.datacontainers.pet.Exercise(exercise.getDateTime().toString(),
                            libraryExerciseData);
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.EXERCISES, libraryExercise.getKey(),
                        libraryExercise.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void addWalking(User user, Pet pet, Walk walk) {
        ExerciseData libraryExerciseData = new ExerciseData(walk.getName(), walk.getDescription(),
            walk.getEndTime().toString(), walk.getCoordinates());
        org.pesmypetcare.usermanager.datacontainers.pet.Exercise libraryExercise =
            new org.pesmypetcare.usermanager.datacontainers.pet.Exercise(walk.getDateTime().toString(),
                libraryExerciseData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(user.getToken(),
                        user.getUsername(), pet.getName(), PetData.EXERCISES, libraryExercise.getKey(),
                        libraryExercise.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public List<Exercise> getAllExercises(User user, Pet pet) {
        SortedSet<Exercise> exercisesSet = new TreeSet<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<org.pesmypetcare.usermanager.datacontainers.pet.Exercise> exercises = null;
            try {
                exercises = ServiceLocator.getInstance()
                        .getPetCollectionsManagerClient().getAllExercises(user.getToken(), user.getUsername(),
                                pet.getName());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            for (org.pesmypetcare.usermanager.datacontainers.pet.Exercise exercise : exercises) {
                ExerciseData exerciseData = exercise.getBody();

                if (exerciseData.getCoordinates() == null) {
                    Exercise actualExercise = new Exercise(exerciseData.getName(), exerciseData.getDescription(),
                            DateTime.Builder.buildFullString(exercise.getKey()),
                            DateTime.Builder.buildFullString(exerciseData.getEndDateTime()));
                    exercisesSet.add(actualExercise);
                } else {
                    Walk actualExercise = new Walk(exerciseData.getName(), exerciseData.getDescription(),
                            DateTime.Builder.buildFullString(exercise.getKey()),
                            DateTime.Builder.buildFullString(exerciseData.getEndDateTime()),
                            exerciseData.getCoordinates());
                    exercisesSet.add(actualExercise);
                }
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>(exercisesSet);
    }

    @Override
    public List<Weight> getAllWeights(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        AtomicReference<List<Weight>> weights = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                Objects.requireNonNull(weights).set(ServiceLocator.getInstance().getPetCollectionsManagerClient()
                    .getAllWeights(accessToken, owner, petName));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return weights.get();
    }

    @Override
    public byte[] getPetImage(User user, Pet pet) {
        bytes = new byte[0];
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                bytes = ServiceLocator.getInstance().getPetManagerClient().downloadProfileImage(user.getToken(),
                    user.getUsername(), pet.getName());
            } catch (NullPointerException ignored) {

            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    /**
     * Decodes the pet information from the server.
     * @param userPet The information from the server
     * @return The pet associated with that information
     * @throws PetRepeatException The pet is repeated.
     */
    private Pet decodePet(org.pesmypetcare.usermanager.datacontainers.pet.Pet userPet) throws PetRepeatException {
        PetData petData = userPet.getBody();
        Pet pet = new Pet();

        pet.setName(userPet.getName());
        pet.setGender(petData.getGender());
        pet.setBirthDate(DateTime.Builder.buildFullString(petData.getBirth()));
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(petData.getRecommendedKcal());
        pet.setBreed(petData.getBreed());
        pet.setPathologies(petData.getPathologies());

        return pet;
    }
}
