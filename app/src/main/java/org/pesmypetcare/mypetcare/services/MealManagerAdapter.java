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
    public void updateMealBody(User user, Pet pet, Meals meal, Boolean updateName) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        DateTime mealDate = new DateTime(meal.getDateTime());
        String field;
        Object value;
        if (updateName) {
            field = MealManagerClient.MEALNAME;
            value = meal.getMealName();
        } else {
            field = MealManagerClient.KCAL;
            value = meal.getKcal();
        }
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
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        DateTime dateTime = new DateTime(meal.getDateTime());
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
