package org.pesmypetcare.mypetcare.services.meal;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.meals.Meals;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.pet.Meal;
import org.pesmypetcare.usermanager.datacontainers.pet.MealData;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Xavier Campos
 */
public class MealManagerAdapter implements MealManagerService {
    private static final long TIME = 2;

    @Override
    public void createMeal(User user, Pet pet, Meals meal) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        MealData mealData = new MealData(meal.getMealName(), meal.getKcal());
        Meal libraryMeal = new Meal(meal.getMealDate().toString(), mealData);
        createMealLibraryCall(accessToken, owner, petName, libraryMeal);
    }

    /**
     * Method responsible for calling the library to create a new meal.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param libraryMeal The meal that has to be created
     */
    private void createMealLibraryCall(String accessToken, String owner, String petName, Meal libraryMeal) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner,
                        petName, PetData.MEALS, libraryMeal.getKey(), libraryMeal.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateMealBody(User user, Pet pet, Meals meal) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MealData mealData = new MealData(meal.getMealName(), meal.getKcal());
        Meal libraryMeal = new Meal(meal.getMealDate().toString(), mealData);
        updateMealBodyLibraryCall(accessToken, owner, petName, libraryMeal);
    }

    /**
     * Method responsible for calling the library for updating the body of a meal.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param libraryMeal The updated meal
     */
    private void updateMealBodyLibraryCall(String accessToken, String owner, String petName, Meal libraryMeal) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner,
                        petName, PetData.MEALS, libraryMeal.getKey(), libraryMeal.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateMealKey(User user, Pet pet, String newDate, String oldDate) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            MealData mealData = getMealData(oldDate, accessToken, owner, petName);
            Meal oldLibraryMeal = new Meal(oldDate, mealData);
            Meal newLibraryMeal = new Meal(newDate, mealData);
            deleteOldMeal(accessToken, owner, petName, oldLibraryMeal);
            createNewMeal(accessToken, owner, petName, newLibraryMeal);
        });
        executorService.shutdown();
    }

    /**
     * Creates the new meal with the updated date.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param newLibraryMeal The meal that has to be created
     */
    private void createNewMeal(String accessToken, String owner, String petName, Meal newLibraryMeal) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
                    PetData.MEALS, newLibraryMeal.getKey(), newLibraryMeal.getBodyAsMap());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the old meal with the old date.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param oldLibraryMeal The meal that has to be deleted
     */
    private void deleteOldMeal(String accessToken, String owner, String petName, Meal oldLibraryMeal) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner,
                    petName, PetData.MEALS, oldLibraryMeal.getKey());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtains the meal data from the server.
     * @param oldDate The oldDate of the meal
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @return
     */
    private MealData getMealData(String oldDate, String accessToken, String owner, String petName) {
        MealData result = null;
        try {
            result = ServiceLocator.getInstance().getPetCollectionsManagerClient().getMeal(
                    accessToken, owner, petName, oldDate);
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deleteMeal(User user, Pet pet, Meals meal) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MealData mealData = new MealData(meal.getMealName(), meal.getKcal());
        Meal libraryMeal = new Meal(meal.getMealDate().toString(), mealData);
        deleteMealLibraryCall(accessToken, owner, petName, libraryMeal);
    }

    /**
     * Method responsible for calling the library for deleting a meal.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param libraryMeal The meal that has to be deleted
     */
    private void deleteMealLibraryCall(String accessToken, String owner, String petName, Meal libraryMeal) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner,
                        petName, PetData.MEALS, libraryMeal.getKey());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public List<Meals> findMealsByPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ArrayList<Meals> appMeals = new ArrayList<>();
        findMealsByPetLibraryCall(accessToken, owner, petName, appMeals);
        return appMeals;
    }

    /**
     * Method responsible for calling the library to obtain all the meals of the given pet.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param appMeals The list of the meals of the pet
     */
    private void findMealsByPetLibraryCall(String accessToken, String owner, String petName,
                                           List<Meals> appMeals) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<Meal> libraryMeals = null;
            try {
                libraryMeals = ServiceLocator.getInstance().getPetCollectionsManagerClient().getAllMeals(
                        accessToken, owner, petName);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            for (Meal libMeal : libraryMeals) {
                appMeals.add(new Meals(DateTime.Builder.buildFullString(libMeal.getKey()),
                        libMeal.getBody().getKcal(), libMeal.getBody().getMealName()));
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMealsFromPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollection(accessToken, owner, petName,
                        PetData.MEALS);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
