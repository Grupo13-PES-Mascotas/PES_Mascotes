package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.mypetcare.utilities.DateTime;

public class Meals extends Event {
    private String mealName;
    private Double kcal;
    private DateTime mealDate;

    public Meals(DateTime dateTime, Double kcal, String mealName) {
        super("Meal " + mealName + " of the day " + dateTime.toString(), dateTime.toString());
        this.mealDate = dateTime;
        this.mealName = mealName;
        this.kcal = kcal;
    }

    /**
     * Getter of the mealName attribute.
     * @return The name of the meal
     */
    public String getMealName() {
        return mealName;
    }

    /**
     * Setter of the mealName attribute.
     * @param mealName The new name of the meal
     */
    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    /**
     * Getter of the kilocalories of the meal.
     * @return The kilocalories of the meal
     */
    public Double getKcal() {
        return kcal;
    }

    /**
     * Setter of the kilocalories of the meal.
     * @param kcal The new kilocalories of the meal
     */
    public void setKcal(Double kcal) {
        this.kcal = kcal;
    }

    /**
     * Getter of the date of the meal.
     * @return The date of the meal
     */
    public DateTime getMealDate() {
        return mealDate;
    }

    /**
     * Setter of the date of the meal.
     * @param mealDate The new date of the meal
     */
    public void setMealDate(DateTime mealDate) {
        this.mealDate = mealDate;
    }
}
