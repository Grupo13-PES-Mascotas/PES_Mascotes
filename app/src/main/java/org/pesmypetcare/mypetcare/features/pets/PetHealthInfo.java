package org.pesmypetcare.mypetcare.features.pets;

import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class PetHealthInfo {
    private Map<DateTime, Double> weight;
    private Map<DateTime, Double> dailyKiloCalories;
    private Map<DateTime, Integer> exerciseFrequency;
    private Map<DateTime, Integer> weeklyExercise;
    private Map<DateTime, Double> weeklyKiloCaloriesAverage;
    private Map<DateTime, Integer> washFrequency;
    private Map<DateTime, Integer> weeklyMeals;
    private String pathologies;
    private List<String> petNeeds;

    public PetHealthInfo() {
        this.weight = new TreeMap<>(new TreeComparator());
        this.dailyKiloCalories = new TreeMap<>(new TreeComparator());
        this.exerciseFrequency = new TreeMap<>(new TreeComparator());
        this.weeklyExercise = new TreeMap<>(new TreeComparator());
        this.weeklyKiloCaloriesAverage = new TreeMap<>(new TreeComparator());
        this.washFrequency = new TreeMap<>(new TreeComparator());
        this.petNeeds = new ArrayList<>();
        this.weeklyMeals = new TreeMap<>(new TreeComparator());
    }

    /**
     * Getter of the weight attribute.
     * @return The weight attribute
     */
    public Map<DateTime, Double> getWeight() {
        return weight;
    }

    /**
     * Method that gets the last stored weight of the pet.
     * @return The last stored weight of the pet or -1 if the pet does not have any weight stored
     */
    public double getLastWeight() {
        if (weight.isEmpty()) {
            return -1;
        }
        return ((TreeMap<DateTime, Double>) weight).lastEntry().getValue();
    }
    /**
     * Method that gets the weight for a given date.
     * @param date The date for which we went to obtain to weight of the pet
     * @return The weight of the pet for the given date
     */
    public double getWeightForDate(DateTime date) {
        if (weight.containsKey(date)) {
            return weight.get(date);
        }
        return -1;
    }

    /**
     * Method that adds, or replaces if date is already present, a new weight.
     * @param date The date for which we want to add the weight of the pet
     * @param weight The weight of the pet in that date
     */
    public void addWeightForDate(DateTime date, double weight) {
        selectDate(date);
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
    public Map<DateTime, Double> getDailyKiloCalories() {
        return dailyKiloCalories;
    }

    /**
     * Method that gets the last stored recommended daily kilocalories of the pet.
     * @return The last stored recommended daily kilocalories of the pet
     * or -1 if the pet does not have any recommended daily kilocalories stored
     */
    public double getLastRecommendedDailyKiloCalories() {
        if (dailyKiloCalories.isEmpty()) {
            return -1;
        }
        return ((TreeMap<DateTime, Double>) dailyKiloCalories).lastEntry().getValue();
    }

    /**
     * Getter of the recommendedDailyKiloCalories for an specific date.
     * @param date The date for which we want to get the recommendedDailyKiloCalories of the pet
     * @return The recommendedDailyKiloCalories of the pet for the specified date or -1 if the date was not found
     */
    public double getRecommendedDailyKiloCaloriesForDate(DateTime date) {
        if (dailyKiloCalories.containsKey(date)) {
            return dailyKiloCalories.get(date);
        }
        return -1;
    }

    /**
     * Method that adds, or replaces if date is already present, a new recommendedDailyKiloCalories.
     * @param date The date for which we want to add the recommendedDailyKiloCalories of the pet
     * @param kCal The recommendedDailyKiloCalories of the pet in that date
     */
    public void addRecommendedDailyKiloCaloriesForDate(DateTime date, double kCal) {
        /*date.setHour(0);
        date.setMinutes(0);
        date.setSeconds(0);
        if (dailyKiloCalories.containsKey(date)) {
            double storedKcal = dailyKiloCalories.get(date);
            dailyKiloCalories.put(date, storedKcal + kCal);
        } else {
            this.dailyKiloCalories.put(date, kCal);
        }
        this.addWeeklyKiloCalAverageForDate(date, kCal);*/
    }

    /**
     * Method that removes a recommendedDailyKiloCalories for a date.
     * @param date The date for which we want to remove the recommendedDailyKiloCalories of the pet
     */
    public void deleteRecommendedDailyKiloCaloriesForDate(DateTime date) {
        this.dailyKiloCalories.remove(date);
    }

    /**
     * Getter of the exerciseFrequency attribute.
     * @return The exerciseFrequency attribute
     */
    public Map<DateTime, Integer> getExerciseFrequency() {
        return exerciseFrequency;
    }

    /**
     * Method that gets the last stored exercise frequency of the pet.
     * @return The last stored exercise frequency of the pet
     * or -1 if the pet does not have any exercise frequency stored
     */
    public int getLastExerciseFrequency() {
        if (exerciseFrequency.isEmpty()) {
            return -1;
        }
        return ((TreeMap<DateTime, Integer>) exerciseFrequency).lastEntry().getValue();
    }

    /**
     * Getter of the exerciseFrequency attribute for a given date.
     * @param date The date for which we want to obtain de exerciseFrequency
     * @return The exerciseFrequency for a given date.
     */
    public int getExerciseFrequencyForDate(DateTime date) {
        if (exerciseFrequency.containsKey(date)) {
            return exerciseFrequency.get(date);
        }
        return -1;
    }

    /**
     * Method that adds, or replaces if date already present, a new exerciseFrequency.
     * @param date The date for which we want to add the exerciseFrequency of the pet
     * @param exerciseFreq The exerciseFreq of the pet for that given date
     */
    public void addExerciseFrequencyForDate(DateTime date, int exerciseFreq) {
        this.exerciseFrequency.put(date, exerciseFreq);
    }

    /**
     * Method that removes the exerciseFrequency for a given date.
     * @param date The date for which we want to remove the exerciseFrequency of the pet
     */
    public void removeExerciseFrequencyForDate(DateTime date) {
        this.exerciseFrequency.remove(date);
    }

    /**
     * Getter of the weeklyExercise attribute.
     * @return The weeklyExercise attribute
     */
    public Map<DateTime, Integer> getWeeklyExercise() {
        return weeklyExercise;
    }

    /**
     * Method that gets the last stored weekly exercise of the pet.
     * @return The last stored weekly exercise of the pet or null if the pet does not have any weekly exercise stored
     */
    public Integer getLastWeeklyExercise() {
        if (weeklyExercise.isEmpty()) {
            return null;
        }
        return Objects.requireNonNull(((TreeMap<DateTime, Integer>) weeklyExercise).lastEntry()).getValue();
    }

    /**
     * Getter of the weeklyExercise for a given date.
     * @param date The date for which we want to obtain de weeklyExercise
     * @return The weeklyExercise for the given date, or null if not present
     */
    public Integer getWeeklyExerciseForDate(DateTime date) {
        if (weeklyExercise.containsKey(date)) {
            return weeklyExercise.get(date);
        }
        return null;
    }

    /**
     * Method that adds, or replaces if present, the event for a given date.
     * @param date The date for which we want to add the event
     * @param exercise The exercise that we want to add for that given date
     */
    public void addWeeklyExerciseForDate(DateTime date, Integer exercise) {
        this.weeklyExercise.put(date, exercise);
    }

    /**
     * Method that removes the exercise for a given date.
     * @param date The given date for which we want to remove the event
     */
    public void removeWeeklyExerciseForDate(DateTime date) {
        this.weeklyExercise.remove(date);
    }

    /**
     * Getter of the weeklyKiloCaloriesAverage attribute.
     * @return The weeklyKiloCaloriesAverage attribute
     */
    public Map<DateTime, Double> getWeeklyKiloCaloriesAverage() {
        return weeklyKiloCaloriesAverage;
    }

    /**
     * Method that gets the last stored weekly kilocalories average of the pet.
     * @return The last stored weekly kilocalories average of the pet
     * or -1 if the pet does not have any weekly kilocalories average stored
     */
    public double getLastWeeklyKilocaloriesAverage() {
        if (weeklyKiloCaloriesAverage.isEmpty()) {
            return -1;
        }
        return ((TreeMap<DateTime, Double>) weeklyKiloCaloriesAverage).lastEntry().getValue();
    }

    /**
     * Getter of the weeklyKiloCalAverage for a given date.
     * @param date The given date for which we want to obtain the event.
     * @return The weeklyKiloCalAverage for the given date, or -1 if the date is not present
     */
    public double getWeeklyKiloCalAverageForDate(DateTime date) {
        if (weeklyKiloCaloriesAverage.containsKey(date)) {
            return weeklyKiloCaloriesAverage.get(date);
        }
        return -1;
    }

    /**
     * Method that adds, or replaces if present, the weeklyKiloCalAvg for a given date.
     * @param date The date for which we want to add the weeklyKiloCalAvg
     * @param kcal The kcal to add for a given date
     */
    public void addWeeklyKiloCalAverageForDate(DateTime date, double kcal) {
        /*obtainDateMonday(date);
        if (weeklyKiloCaloriesAverage.containsKey(date)) {
            //int n = weeklyMeals.get(date);
            double storedKcal = weeklyKiloCaloriesAverage.get(date);
            weeklyKiloCaloriesAverage.put(date, storedKcal + kcal);
        } else {
            this.weeklyKiloCaloriesAverage.put(date, kcal);
            this.weeklyMeals.put(date, 1);
        }*/
    }

    /**
     * Obtains the monday of the indicated day.
     * @param date The date for which we want to find the monday
     */
    private void obtainDateMonday(DateTime date) {
        Calendar c = Calendar.getInstance();
        c.set(date.getYear(), date.getMonth(), date.getDay());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        while (dayOfWeek != Calendar.MONDAY) {
            date.decreaseDay();
        }
        date.setHour(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }

    /**
     * Method that removes the weeklyKiloCalAvg for a given date.
     * @param date The date for which we want to remove the weeklyKiloCalAvg
     */
    public void removeWeeklyKiloCalAverageForDate(DateTime date) {
        this.weeklyKiloCaloriesAverage.remove(date);
    }

    /**
     * Getter of the washFrequency attribute.
     * @return The washFrequency attribute
     */
    public Map<DateTime, Integer> getWashFrequency() {
        return washFrequency;
    }

    /**
     * Method that gets the last stored wash frequency of the pet.
     * @return The last stored wash frequency of the pet
     * or -1 if the pet does not have any wash frequency average stored
     */
    public int getLastWashFrequency() {
        if (washFrequency.isEmpty()) {
            return -1;
        }
        return ((TreeMap<DateTime, Integer>) washFrequency).lastEntry().getValue();
    }

    /**
     * Getter of the washFrequency for a given date.
     * @param date The date for which we want to obtain de washFrequency
     * @return The washFrequency of the pet for the given date or -1 if not present
     */
    public int getWashFrequencyForDate(DateTime date) {
        if (washFrequency.containsKey(date)) {
            return washFrequency.get(date);
        }
        return -1;
    }

    /**
     * Method that adds, or replaces if date is present, the given washFreq.
     * @param date The date for which we want to add the washFrequency
     * @param washFreq The washFrequency that we want to add
     */
    public void addWashFrequencyForDate(DateTime date, int washFreq) {
        selectDate(date);
        washFrequency.put(date, washFreq);
    }

    /**
     * Method that removes the washFrequency for the given date.
     * @param date The date for which we want to remove the washFrequency
     */
    public void deleteWashFrequencyForDate(DateTime date) {
        washFrequency.remove(date);
    }

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
    public List<String> getPetNeeds() {
        return petNeeds;
    }

    /**
     * Setter of the petNeeds attribute.
     * @param petNeeds The petNeeds to set
     */
    public void setPetNeeds(List<String> petNeeds) {
        this.petNeeds = petNeeds;
    }

    /**
     * Select the date.
     * @param date The dateTime to select the date from
     */
    private void selectDate(DateTime date) {
        date.setHour(0);
        date.setMinutes(0);
        date.setSeconds(0);
    }

    @Override
    public String toString() {
        return "PetHealthInfo{"
            + "weight=" + weight
            + ", recommendedDailyKiloCalories=" + dailyKiloCalories
            + ", exerciseFrequency=" + exerciseFrequency
            + ", weeklyExercise=" + weeklyExercise
            + ", weeklyKiloCaloriesAverage=" + weeklyKiloCaloriesAverage
            + ", washFrequency=" + washFrequency
            + ", pathologies='" + pathologies + '\''
            + ", petNeeds=" + petNeeds
            + '}';
    }

    private static class TreeComparator implements Comparator<DateTime> {

        @Override
        public int compare(DateTime date1, DateTime date2) {
            String strDate1 = date1.toString();
            String strDate2 = date2.toString();
            return strDate1.compareTo(strDate2);
        }
    }
}
