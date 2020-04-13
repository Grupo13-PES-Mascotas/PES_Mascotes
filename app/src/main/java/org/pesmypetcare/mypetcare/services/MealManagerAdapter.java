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
        //System.out.println("Meal data pa la libreria " + meal.getDateTime());
        Meal libMeal = new Meal(meal.getDateTime(), body);
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
        DateTime mealDate = new DateTime(meal.getDateTime());
        String field = MealManagerClient.MEALNAME;
        Object value = meal.getMealName();
        try {
            ServiceLocator.getInstance().getMealManagerClient().updateMealField(accessToken,
                owner, petName, mealDate, field, value);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        field = MealManagerClient.KCAL;
        value = meal.getKcal();
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
        updateMeal(user, pet, newDate, oldDate, accessToken, owner, petName);
    }

    /**
     * Method responsible for obtaining all data an updating the meal.
     * @param user The owner of the pet
     * @param pet The pet from which we want to update the meal
     * @param newDate The new date of the meal
     * @param oldDate The current date of the meal
     * @param accessToken The access token of the user
     * @param owner The username of the owner of the pet
     * @param petName The name of the pet
     */
    private void updateMeal(User user, Pet pet, String newDate, String oldDate, String accessToken, String owner, String petName) {
        MealData mealData = null;
        try {
            mealData = ServiceLocator.getInstance().getMealManagerClient().getMealData(accessToken,
                owner, petName, new DateTime(oldDate));
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Meals currentMeal = new Meals(new Meal(oldDate, mealData));
        Meals newMeal = new Meals(new Meal(newDate, mealData));
        this.deleteMeal(user, pet, currentMeal);
        this.createMeal(user, pet, newMeal);
    }

    @Override
    public void deleteMeal(User user, Pet pet, Meals meal) {
        /*String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        DateTime dateTime = new DateTime(meal.getDateTime());
        try {
            ServiceLocator.getInstance().getMealManagerClient().deleteByDate(accessToken, owner, petName, dateTime);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("Nº apats before delete " + pet.getMealEvents().size());
        pet.deleteEvent(meal);
        System.out.println("Nº apats after delete " + pet.getMealEvents().size());
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
