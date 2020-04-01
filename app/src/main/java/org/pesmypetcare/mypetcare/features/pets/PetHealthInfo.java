package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.mypetcare.utilities.DateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class PetHealthInfo {
    private Map<DateTime, Double> weight;
    private Map<DateTime, Double> recommendedDailyKiloCalories;
    private Map<DateTime, Integer> exerciseFrequency;
    private Map<DateTime, Event> weeklyExercise;
    private Map<DateTime, Double> weeklyKiloCaloriesAverage;
    private Map<DateTime, Integer> washFrequency;
    private String pathologies;
    private ArrayList<String> petNeeds;

    public PetHealthInfo() {
        this.weight = new TreeMap<DateTime, Double>(new TreeComparator());
        this.recommendedDailyKiloCalories = new TreeMap<DateTime, Double>(new TreeComparator());
        this.exerciseFrequency = new TreeMap<DateTime, Integer>(new TreeComparator());
        this.weeklyExercise = new TreeMap<DateTime, Event>(new TreeComparator());
        this.weeklyKiloCaloriesAverage = new TreeMap<DateTime, Double>(new TreeComparator());
        this.petNeeds = new ArrayList<>();
    }

    /**
     * Getter of the weight attribute.
     * @return The weight attribute
     */
    public Map<DateTime, Double> getWeight() {
        return weight;
    }

    /**
     * Method that adds, or replaces if date is already present, a new weight.
     * @param date The date for which we want to add the weight of the pet
     * @param weight The weight of the pet in that date
     */
    public void addWeightForDate(DateTime date, double weight) {
        this.weight.put(date, weight);
    }

    /**
     * Method that removes a weight for a date.
     * @param date The date for which we want to remove the weight of the pet
     */
    public void deleteWeightForDate(DateTime date) {
        this.weight.remove(date);
    }

    /**
     * Getter of the recommendedDailyKiloCalories attribute.
     * @return The recommendedDailyKiloCalories attribute
     */
    public Map<DateTime, Double> getRecommendedDailyKiloCalories() {
        return recommendedDailyKiloCalories;
    }

    /**
     * Getter of the recommendedDailyKiloCalories for an specific date.
     * @param date The date for which we want to get the recommendedDailyKiloCalories of the pet
     * @return The recommendedDailyKiloCalories of the pet for the specified date or -1 if the date was not found
     */
    public double getRecommendedDailyKiloCaloriesForDate(DateTime date) {
        if(recommendedDailyKiloCalories.containsKey(date)) {
            return recommendedDailyKiloCalories.get(date);
        }
        return -1;
    }

    /**
     * Method that adds, or replaces if date is already present, a new recommendedDailyKiloCalories.
     * @param date The date for which we want to add the recommendedDailyKiloCalories of the pet
     * @param kCal The recommendedDailyKiloCalories of the pet in that date
     */
    public void addRecommendedDailyKiloCaloriesForDate(DateTime date, double kCal) {
        this.recommendedDailyKiloCalories.put(date, kCal);
    }

    /**
     * Method that removes a recommendedDailyKiloCalories for a date.
     * @param date The date for which we want to remove the recommendedDailyKiloCalories of the pet
     */
    public void deleteRecommendedDailyKiloCaloriesForDate(DateTime date) {
        this.recommendedDailyKiloCalories.remove(date);
    }
    /**
     * Getter of the exerciseFrequency attribute.
     * @return The exerciseFrequency attribute
     */
    public Map<DateTime, Integer> getExerciseFrequency() {
        return exerciseFrequency;
    }

    /**
     * Getter of the exerciseFreq for an specific date.
     * @param date The date for which we want to get the weight of the pet
     * @return The weight of the pet for the specified date or -1 if the date was not found
     */
    public double getWeightForDate(DateTime date) {
        if (weight.containsKey(date)) {
            return weight.get(date);
        }
        return -1;
    }

    /**
     * Getter of the weeklyExercise attribute.
     * @return The weeklyExercise attribute
     */
    public Map<DateTime, Event> getWeeklyExercise() {
        return weeklyExercise;
    }

    /**
     * Getter of the weeklyKiloCaloriesAverage attribute.
     * @return The weeklyKiloCaloriesAverage attribute
     */
    public Map<DateTime, Double> getWeeklyKiloCaloriesAverage() {
        return weeklyKiloCaloriesAverage;
    }

    /**
     * Getter of the washFrequency attribute.
     * @return The washFrequency attribute
     */
    /*public int getWashFrequency() {
        return washFrequency;
    }*/

    /**
     * Setter of the wash frequency attribute.
     * @param washFrequency The wash frequency to set
     */
    /*public void setWashFrequency(int washFrequency) {
        this.washFrequency = washFrequency;
    }*/

    /**
     * Getter of the pathologies attribute.
     * @return The pathologies attribute
     */
    public String getPathologies() {
        return pathologies;
    }

    /**
     * Setter of the pathologies attribute.
     * @param pathologies The pathologies to set
     */
    public void setPathologies(String pathologies) {
        this.pathologies = pathologies;
    }

    /**
     * Getter of the petNeeds attribute.
     * @return The petNeeds
     */
    public ArrayList<String> getPetNeeds() {
        return petNeeds;
    }

    /**
     * Setter of the petNeeds attribute.
     * @param petNeeds The petNeeds to set
     */
    public void setPetNeeds(ArrayList<String> petNeeds) {
        this.petNeeds = petNeeds;
    }

    private static class TreeComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            String date1 = o1.toString();
            String date2 = o2.toString();
            return date1.compareTo(date2);
        }
    }
}
