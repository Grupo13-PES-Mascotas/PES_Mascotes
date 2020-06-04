package org.pesmypetcare.mypetcare.services.wash;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.wash.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.pesmypetcare.usermanager.datacontainers.pet.WashData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Enric Hernando
 */
public class WashManagerAdapter implements WashManagerService {

    private static final long TIME = 5;

    @Override
    public void createWash(User user, Pet pet, Wash wash) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        org.pesmypetcare.usermanager.datacontainers.pet.Wash libraryWash =
                new org.pesmypetcare.usermanager.datacontainers.pet.Wash(wash.getDateTime().toString(),
                        wash.getWashDescription(), wash.getDuration());
        createWashLibraryCall(accessToken, owner, petName, libraryWash);
    }

    /**
     * Method responsible for calling the library for creating a new wash.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param libraryWash The wash that has to be created
     */
    private void createWashLibraryCall(String accessToken, String owner, String petName,
                                       org.pesmypetcare.usermanager.datacontainers.pet.Wash libraryWash) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken,
                        petName, PetData.WASHES, libraryWash.getKey(), libraryWash.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deleteWash(User user, Pet pet, Wash wash) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        org.pesmypetcare.usermanager.datacontainers.pet.Wash libraryWash =
                new org.pesmypetcare.usermanager.datacontainers.pet.Wash(wash.getDateTime().toString(),
                        wash.getWashDescription(), wash.getDuration());
        deleteWashLibraryCall(accessToken, owner, petName, libraryWash);
    }

    /**
     * Method responsible for calling the library for deleting a wash.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param libraryWash The wash that has to be deleted
     */
    private void deleteWashLibraryCall(String accessToken, String owner, String petName,
                                       org.pesmypetcare.usermanager.datacontainers.pet.Wash libraryWash) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken,
                        petName, PetData.WASHES, libraryWash.getKey());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public List<Wash> findWashesByPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        return obtainAllWashes(accessToken, owner, petName);
    }

    @Override
    public void updateWashBody(User user, Pet pet, Wash wash) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        org.pesmypetcare.usermanager.datacontainers.pet.Wash libraryWash =
                new org.pesmypetcare.usermanager.datacontainers.pet.Wash(wash.getDateTime().toString(),
                        wash.getWashDescription(), wash.getDuration());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken,
                        petName, PetData.WASHES, libraryWash.getKey(), libraryWash.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateWashDate(User user, Pet pet, String newDate, String oldDate) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        updateWashDateLibraryCall(user, pet, newDate, oldDate, accessToken, owner, petName);
    }

    /**
     * Method responsible for calling the library for updating the date of a meal.
     * @param user The current user
     * @param pet The pet from which the meal has to be updated
     * @param newDate The new date of the meal
     * @param oldDate The old date of the meal
     * @param accessToken The accesss token of the user
     * @param owner The owner of the pet
     * @param petName The pet name
     */
    private void updateWashDateLibraryCall(User user, Pet pet, String newDate, String oldDate, String accessToken,
                                           String owner, String petName) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            WashData libraryWashData = null;
            try {
                libraryWashData = ServiceLocator.getInstance().getPetCollectionsManagerClient().getWash(
                        user.getToken(), pet.getName(), oldDate);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            org.pesmypetcare.usermanager.datacontainers.pet.Wash libraryUpdatedWash =
                    new org.pesmypetcare.usermanager.datacontainers.pet.Wash(newDate,
                            libraryWashData.getDescription(), libraryWashData.getDuration());
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken,
                        petName, PetData.WASHES, oldDate);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken,
                        petName, PetData.WASHES, libraryUpdatedWash.getKey(), libraryUpdatedWash.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deleteWashesFromPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollection(accessToken, petName,
                        PetData.WASHES);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    /**
     * Method responsible for accessing the service an obtaining all the washes for the indicated pet.
     * @param accessToken The accessToken of the owner
     * @param owner The owner of the pet
     * @param petName The name of the pet from which we want to obtain all the washes
     * @return The list with all the washes from the pet
     */
    private List<Wash> obtainAllWashes(String accessToken, String owner, String petName) {
        ArrayList<Wash> result = new ArrayList<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<org.pesmypetcare.usermanager.datacontainers.pet.Wash> washes = null;
            try {
                washes = ServiceLocator.getInstance()
                        .getPetCollectionsManagerClient().getAllWashes(accessToken, petName);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            for (org.pesmypetcare.usermanager.datacontainers.pet.Wash w : washes) {
                result.add(new Wash(DateTime.Builder.buildFullString(w.getKey()), w.getBody().getDuration(),
                        w.getBody().getDescription()));
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
}
