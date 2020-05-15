package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.pet.Meal;
import org.pesmypetcare.usermanager.datacontainers.pet.MealData;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MealManagerAdapter implements MealManagerService {

    @Override
    public void createMeal(User user, Pet pet, Meals meal) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        MealData mealData = new MealData(meal.getMealName(), meal.getKcal());
        Meal libraryMeal = new Meal(meal.getMealDate().toString(), mealData);
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
                PetData.MEALS, libraryMeal.getKey(), libraryMeal.getBodyAsMap());
    }

    @Override
    public void updateMealBody(User user, Pet pet, Meals meal) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MealData mealData = new MealData(meal.getMealName(), meal.getKcal());
        Meal libraryMeal = new Meal(meal.getMealDate().toString(), mealData);
        ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner, petName,
                PetData.MEALS, libraryMeal.getKey(), libraryMeal.getBodyAsMap());
    }

    @Override
    public void updateMealKey(User user, Pet pet, String newDate, String oldDate) throws ExecutionException,
            InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MealData mealData = ServiceLocator.getInstance().getPetCollectionsManagerClient().getMeal(
                accessToken, owner, petName, oldDate);
        Meal oldLibraryMeal = new Meal(oldDate, mealData);
        Meal newLibraryMeal = new Meal(newDate, mealData);
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
                PetData.MEALS, oldLibraryMeal.getKey());
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
                PetData.MEALS, newLibraryMeal.getKey(), newLibraryMeal.getBodyAsMap());
    }

    @Override
    public void deleteMeal(User user, Pet pet, Meals meal) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MealData mealData = new MealData(meal.getMealName(), meal.getKcal());
        Meal libraryMeal = new Meal(meal.getMealDate().toString(), mealData);
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
                PetData.MEALS, libraryMeal.getKey());
    }

    @Override
    public List<Meals> findMealsByPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ArrayList<Meals> appMeals = new ArrayList<>();
        List<Meal> libraryMeals = ServiceLocator.getInstance().getPetCollectionsManagerClient().getAllMeals(
                accessToken, owner, petName);
        for (Meal libMeal : libraryMeals) {
            appMeals.add(new Meals(DateTime.Builder.buildFullString(libMeal.getKey()),
                    libMeal.getBody().getKcal(), libMeal.getBody().getMealName()));
        }
        return appMeals;
    }

    @Override
    public void deleteMealsFromPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollection(accessToken, owner, petName,
                PetData.MEALS);
    }
}
