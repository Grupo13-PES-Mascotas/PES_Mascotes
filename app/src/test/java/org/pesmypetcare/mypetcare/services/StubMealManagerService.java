package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Meals;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StubMealManagerService implements MealManagerService {
    private static final String JOHN_DOE = "johnDoe";
    private static final String LINUX = "Linux";
    private static final String MEAL_NAME = "Meal";
    private static final int YEAR = 2020;
    private static final int MONTH = 2;
    private static final int DAY = 26;
    private static final int HOUR = 15;
    private static final int MINUTES = 23;
    private static final int SECONDS = 56;
    private static DateTime mealDate;
    private static double mealKcal = 75;
    public static Meals currentMeal;
    public static int nMeals = 0;
    private Map<String, ArrayList<Meals>> data;

    public StubMealManagerService() {
        this.data = new HashMap<>();
        try {
            mealDate = DateTime.Builder.build(YEAR, MONTH, DAY, HOUR, MINUTES, SECONDS);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }

        this.data.put(JOHN_DOE + " : " + LINUX, new ArrayList<>());
        for (int i = 0; i < 2; ++i) {
            Objects.requireNonNull(this.data.get(JOHN_DOE + " : " + LINUX))
                .add(new Meals(mealDate, mealKcal, MEAL_NAME + i));
            mealDate.increaseDay();
            mealKcal++;
        }
    }

    @Override
    public void createMeal(User user, Pet pet, Meals meal) {
        data.putIfAbsent(user.getUsername() + " : " + pet.getName(), new ArrayList<>());
        Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).add(meal);
        nMeals++;
    }

    @Override
    public void updateMealBody(User user, Pet pet, Meals meal) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            ArrayList<Meals> petMeals = data.get(user.getUsername() + " : " + pet.getName());
            assert petMeals != null;
            petMeals.remove(getTestMeal());
        }
        this.createMeal(user, pet, meal);
        StubMealManagerService.currentMeal = meal;
    }

    @Override
    public void updateMealDate(User user, Pet pet, String newDate, String oldDate) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            ArrayList<Meals> petMeals = data.get(user.getUsername() + " : " + pet.getName());
            assert petMeals != null;
            for (Meals m:petMeals) {
                if (m.getDateTime().equals(oldDate)) {
                    petMeals.remove(m);
                }
            }
        }

        this.createMeal(user, pet, new Meals(DateTime.Builder.buildFullString(newDate), mealKcal,
            MEAL_NAME + MINUTES));
    }

    @Override
    public void deleteMeal(User user, Pet pet, Meals meal) {
        ArrayList<Meals> petMeals = data.get(user.getUsername() + " : " + pet.getName());
        assert petMeals != null;
        petMeals.remove(meal);
        nMeals--;
    }

    @Override
    public List<Meals> findMealsByPet(User user, Pet pet) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            return data.get(user.getUsername() + " : " + pet.getName());
        }
        return null;
    }

    @Override
    public void deleteMealsFromPet(User user, Pet pet) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).clear();
        }
        nMeals = 0;
    }

    private Meals getTestMeal() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2020, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Meals(date, 52d, "Linux meal");
    }
}
