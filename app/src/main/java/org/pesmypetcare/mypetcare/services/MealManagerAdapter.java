package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.clients.MealManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.Meal;
import org.pesmypetcare.usermanagerlib.datacontainers.MealData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MealManagerAdapter implements MealManagerService {

    @Override
    public void createMeal(User user, Pet pet, Meals meal) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MealData body = new MealData(meal.getMealName(), meal.getKcal());
        Meal libMeal = new Meal(meal.getDateTime().toString(), body);
        try {
            ServiceLocator.getInstance().getMealManagerClient().createMeal(accessToken, owner, petName, libMeal);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMealBody(User user, Pet pet, Meals meal) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
      
        DateTime mealDate = meal.getDateTime();
        updateMealName(meal, accessToken, owner, petName, mealDate);
        updateMealKCal(meal, accessToken, owner, petName, mealDate);
    }

    /**
     * Method responsible for accessing the service to update the meal kilocalories.
     * @param meal The meal from which we want to update the kcal.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet to which belong the meal
     * @param mealDate The date of the meal
     */
    private void updateMealKCal(Meals meal, String accessToken, String owner, String petName, DateTime mealDate) {
        String field = MealManagerClient.KCAL;
        Object value = meal.getKcal();
        try {
            ServiceLocator.getInstance().getMealManagerClient().updateMealField(accessToken,
                owner, petName, mealDate, field, value);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method responsible for accessing the service to update the meal name.
     * @param meal The meal from which we want to update the name
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet to which belong the meal
     * @param mealDate The date of the meal
     */
    private void updateMealName(Meals meal, String accessToken, String owner, String petName, DateTime mealDate) {
        String field = MealManagerClient.MEALNAME;
        Object value = meal.getMealName();
        try {
            ServiceLocator.getInstance().getMealManagerClient().updateMealField(accessToken,
                owner, petName, mealDate, field, value);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateMealDate(User user, Pet pet, String newDate, String oldDate) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MealData mealData = obtainMealData(oldDate, accessToken, owner, petName);
        Meals currentMeal = new Meals(new Meal(oldDate, mealData));
        Meals newMeal = new Meals(new Meal(newDate, mealData));
        this.deleteMeal(user, pet, currentMeal);
        this.createMeal(user, pet, newMeal);
    }

    /**
     * Method responsible for obtaining the meal data.
     * @param oldDate The date of the meal before the update
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @return The data of the meal
     */
    private MealData obtainMealData(String oldDate, String accessToken, String owner, String petName) {
        MealData mealData = null;
        try {
            mealData = ServiceLocator.getInstance().getMealManagerClient().getMealData(accessToken,
                owner, petName, DateTime.Builder.buildFullString(oldDate));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return mealData;
    }

    @Override
    public void deleteMeal(User user, Pet pet, Meals meal) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
      
        DateTime dateTime = meal.getDateTime();
        try {
            ServiceLocator.getInstance().getMealManagerClient().deleteByDate(accessToken, owner, petName, dateTime);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Meals> findMealsByPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        return obtainAllMeals(accessToken, owner, petName);
    }

    /**
     * Method responsible for accessing the service an obtaining all the meals for the indicated pet.
     * @param accessToken The accessToken of the owner
     * @param owner The owner of the pet
     * @param petName The name of the pet from which we want to obtain all the meals
     * @return The list with all the meals from the pet
     */
    private List<Meals> obtainAllMeals(String accessToken, String owner, String petName) {
        List<Meal> mealList = null;
        try {
            mealList = ServiceLocator.getInstance().getMealManagerClient().getAllMealData(accessToken,
                owner, petName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        List<Meals> result = new ArrayList<>();
        for (Meal m:mealList) {
            result.add(new Meals(m));
        }
        return result;
    }

    @Override
    public void deleteMealsFromPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        try {
            ServiceLocator.getInstance().getMealManagerClient().deleteAllMeals(accessToken, owner, petName);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
