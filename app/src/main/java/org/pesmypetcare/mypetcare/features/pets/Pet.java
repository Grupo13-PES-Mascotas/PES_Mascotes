package org.pesmypetcare.mypetcare.features.pets;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Pet {
    public static final String BUNDLE_NAME = "petName";
    public static final String BUNDLE_BREED = "petBreed";
    public static final String BUNDLE_BIRTH_DATE = "petBirthDate";
    public static final String BUNDLE_WEIGHT = "petFloat";
    public static final String BUNDLE_PATHOLOGIES = "petPathologies";
    public static final String BUNDLE_WASH = "petWash";
    public static final String BUNDLE_GENDER = "petGender";
    private static final int FACTOR_PES_1 = 30;
    private static final int FACTOR_PES_2 = 70;

    private String name;
    private GenderType gender;
    private String breed;
    private DateTime birthDate;
    private PetHealthInfo healthInfo;
    private User owner;
    private String previousName;
    private Bitmap profileImage;
    private ArrayList<Event> events;
    private ArrayList<PeriodEvent> periodEvents;

    public Pet() {
        this.events = new ArrayList<>();
        this.healthInfo = new PetHealthInfo();
        this.birthDate = DateTime.Builder.buildDateString("2020-01-1");
        this.periodEvents = new ArrayList<>();
    }

    public Pet(Bundle petInfo) {
        this.name = petInfo.getString(BUNDLE_NAME);
        this.breed = petInfo.getString(BUNDLE_BREED);
        this.birthDate = DateTime.Builder.buildDateString(Objects.requireNonNull(petInfo.getString(BUNDLE_BIRTH_DATE)));
        initializeHealthInfo(petInfo);
        this.events = new ArrayList<>();
        this.periodEvents = new ArrayList<>();

        if (isMale(petInfo)) {
            this.gender = GenderType.Male;
        } else if (isFemale(petInfo)) {
            this.gender = GenderType.Female;
        } else {
            this.gender = GenderType.Other;
        }
    }

    public Pet(Bundle petInfo, User user) {
        this.name = petInfo.getString(BUNDLE_NAME);
        this.breed = petInfo.getString(BUNDLE_BREED);
        this.birthDate = DateTime.Builder.buildDateString(Objects.requireNonNull(petInfo.getString(BUNDLE_BIRTH_DATE)));
        initializeHealthInfo(petInfo);
        this.events = new ArrayList<>();
        this.periodEvents = new ArrayList<>();

        if (isMale(petInfo)) {
            this.gender = GenderType.Male;
        } else if (isFemale(petInfo)){
            this.gender = GenderType.Female;
        } else {
            this.gender = GenderType.Other;
        }

        owner = user;
    }

    public Pet(String name) {
        this.name = name;
        this.events = new ArrayList<>();
        this.healthInfo = new PetHealthInfo();
        this.birthDate = DateTime.Builder.buildDateString("2020-01-1");
        this.periodEvents = new ArrayList<>();

    }

    /**
     * Method that initializes the health info of the pet.
     * @param petInfo A bundle containing all the information of the pet
     */
    public void initializeHealthInfo(Bundle petInfo) {
        DateTime dateTime = getActualDateTime();

        this.healthInfo = new PetHealthInfo();
        this.healthInfo.addWeightForDate(dateTime, petInfo.getFloat(BUNDLE_WEIGHT));
        this.healthInfo.setPathologies(petInfo.getString(BUNDLE_PATHOLOGIES));
        this.healthInfo.addRecommendedDailyKiloCaloriesForDate(dateTime, calculateRecommendedKiloCalories());
        this.healthInfo.addWashFrequencyForDate(dateTime, petInfo.getInt(BUNDLE_WASH));
    }

    /**
     * Calculate the recommended kilocalories for the pet.
     * @return The recommended kilocalories
     */
    private double calculateRecommendedKiloCalories() {
        return healthInfo.getLastWeight() * FACTOR_PES_1 + FACTOR_PES_2;
    }

    /**
     * Get the name of the pet.
     * @return The name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the pet.
     * @param name The name of the pet to set
     */
    public void setName(String name) throws PetRepeatException {
        if (owner == null || !owner.getPets().contains(new Pet(name))) {
            this.previousName = this.name;
            this.name = name;
        } else {
            throw new PetRepeatException();
        }
    }

    /**
     * Get the gender of the pet.
     * @return The gender of the pet
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * Set the gender of the pet.
     * @param gender The gender of the pet to set
     */
    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    /**
     * Get the breed of the pet.
     * @return The breed of the pet
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Set the breed of the pet.
     * @param breed The breed of the pet to set
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * Get the birth date of the pet.
     * @return The birth date of the pet
     */
    public String getBirthDate() {
        return birthDate.getYear() + "-" + birthDate.getMonth() + "-" + birthDate.getDay();
    }

    /**
     * Set the birth date of the pet.
     * @param birthDate The birth date to set
     */
    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Get the weight of the pet.
     * @return The weight of the pet
     */
    public double getWeight() {
        return healthInfo.getLastWeight();
    }

    /**
     * Set the weight of the pet.
     * @param weight The weight to set
     */
    public void setWeight(double weight) {
        DateTime dateTime = getActualDateTime();
        this.healthInfo.addWeightForDate(dateTime, weight);
    }

    /**
     * Get the actual date time.
     * @return The actual date time
     */
    private DateTime getActualDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        Date date = new Date();
        String strData = dateFormat.format(date);
        return DateTime.Builder.buildDateString(strData);
    }

    /**
     * Get the pathologies of the pet.
     * @return The pathologies of the pet
     */
    public String getPathologies() {
        return this.healthInfo.getPathologies();
    }

    /**
     * Set the pathologies of the pet.
     * @param pathologies The pathologies to set
     */
    public void setPathologies(String pathologies) {
        this.healthInfo.setPathologies(pathologies);
    }

    /**
     * Get the recommended daily kilo calories of the pet.
     * @return The recommended daily kilo calories of the pet
     */
    public double getRecommendedDailyKiloCalories() {
        return healthInfo.getLastRecommendedDailyKiloCalories();
    }

    /**
     * Set the recommended daily kilo calories of the pet.
     * @param recommendedDailyKiloCalories The recommended daily kilo calories of the pet to set
     */
    public void setRecommendedDailyKiloCalories(double recommendedDailyKiloCalories) {
        /*DateTime dateTime = getActualDateTime();
        healthInfo.addRecommendedDailyKiloCaloriesForDate(dateTime, recommendedDailyKiloCalories);*/
    }

    /**
     * Get the wash frequency of the pet.
     * @return The wash frequency of the pet
     */
    public int getWashFrequency() {
        return (int) healthInfo.getLastWashFrequency();
    }

    /**
     * Set the wash frequency of the pet.
     * @param washFrequency The wash frequency to set
     */
    public void setWashFrequency(int washFrequency) {
        DateTime dateTime = getActualDateTime();
        healthInfo.addWashFrequencyForDate(dateTime, washFrequency);
    }

    /**
     * Checks whether a pet is male or not.
     * @param petInfo Information about the pet
     * @return True if the pet is male
     */
    private boolean isMale(Bundle petInfo) {
        return "Male".equals(Objects.requireNonNull(petInfo.getString(BUNDLE_GENDER)));
    }

    /**
     * Checks whether a pet is female or not.
     * @param petInfo Information about the pet
     * @return True if the pet is female
     */
    private boolean isFemale(Bundle petInfo) {
        return "Female".equals(Objects.requireNonNull(petInfo.getString(BUNDLE_GENDER)));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(name, pet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Get the owner of the pet.
     * @return The owner of the pet
     */
    public User getOwner() {
        return owner;
    }

    /**
     * Set the owner of the pet.
     * @param owner  The owner of the pet
     */
    public void setOwner(User owner) {
        this.owner = owner;
    }

    /**
     * Get the previous name of the pet.
     * @return The previous name of the pet
     */
    public String getPreviousName() {
        return previousName;
    }

    /**
     * Set the previous name of the pet.
     * @param previousName The previous name of the pet
     */
    public void setPreviousName(String previousName) {
        this.previousName = previousName;
    }

    /**
     * Get the profile image of the pet.
     * @return The profile image of the pet
     */
    public Bitmap getProfileImage() {
        return profileImage;
    }

    /**
     * Set the profile image of the pet.
     * @param profileImage The profile image of the pet
     */
    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    /**
     * Get the health info.
     * @return The health info
     */
    public PetHealthInfo getHealthInfo() {
        return healthInfo;
    }

    /**
     * Add an event to the pet.
     * @param event The event of the pet to set
     */
    public void addEvent(Event event) {
        events.add(event);

        if (event instanceof Meals) {
            DateTime eventDate = event.getDateTime();
            double kcal = ((Meals) event).getKcal();

            healthInfo.addRecommendedDailyKiloCaloriesForDate(eventDate, kcal);
        }
    }

    /**
     * Delete an event.
     * @param event The event to delete
     */
    public void deleteEvent(Event event) {
        events.remove(event);
    }

    /**
     * Get the list of events on a date.
     * @param date The date of the events
     * @return The list of events on the given date
     */
    public List<Event> getEvents(String date) {
        ArrayList<Event> selectedEvents = new ArrayList<>();

        for (Event event : events) {
            String eventDate = DateConversion.getDate(event.getDateTime().toString());
            if (eventDate.equals(date)) {
                selectedEvents.add(event);
            }
        }

        return selectedEvents;
    }

    /**
     * Delete the weight for a date.
     * @param dateTime The date to delete the weight
     */
    public void deleteWeightForDate(DateTime dateTime) {
        healthInfo.deleteWeightForDate(dateTime);
    }

    /**
     * Delete the wash frequency for a date.
     * @param dateTime The date to delete the wash frequency
     */
    public void deleteWashFrequencyForDate(DateTime dateTime) {
        healthInfo.deleteWashFrequencyForDate(dateTime);
    }

    /**
     * Check whether the user is the owner.
     * @param user The user
     * @return True if the user is the owner
     */
    public boolean isOwner(User user) {
        return user.equals(owner);
    }

    /**
     * Set the weight for a date.
     * @param newWeight The weight to set
     * @param dateTime The date of the weight to set
     */
    public void setWeightForDate(double newWeight, DateTime dateTime) {
        healthInfo.addWeightForDate(dateTime, newWeight);
    }

    /**
     * Set the wash frequency for a date.
     * @param newWashFrequency The wash frequency to set
     * @param dateTime The date of the wash frequency to set
     */
    public void setWashFrequencyForDate(int newWashFrequency, DateTime dateTime) {
        healthInfo.addWashFrequencyForDate(dateTime, newWashFrequency);
    }

    /**
     * Get the list of meals of the pet.
     * @return The list of meals of the pet
     */
    @NonNull
    public List<Event> getMealEvents() {
        ArrayList<Event> mealEvents = new ArrayList<>();

        for (Event event : events) {
            if (event instanceof Meals) {
                mealEvents.add(event);
            }
        }
        return mealEvents;
    }

    /**
     * Method responsible for cleaning the event list of the pet.
     */
    public void deleteAllEvents() {
       events = new ArrayList<>();
    }

    /**
     * Get the list of meals of the pet for a given date.
     * @return The list of meals of the pet for a given date
     */
    public List<Event> getMealEventsForDate(String dateTime) {
        ArrayList<Event> mealEvents = new ArrayList<>();

        for (Event event : events) {
            String eventDate = DateConversion.getDate(event.getDateTime().toString());
            if (event instanceof Meals && eventDate.equals(dateTime)) {
                mealEvents.add(event);
            }
        }
        return mealEvents;
    }

    /**
     * Get the list of medication of the pet.
     * @return The list of medication of the pet
     */
    public List<Event> getMedicationEvents() {
        ArrayList<Event> medicationEvents = new ArrayList<>();

        for (Event event : events) {
            if (event instanceof Medication) {
                medicationEvents.add(event);
            }
        }
        return medicationEvents;
    }

    /**
     * Get the list of medications of the pet for a given date.
     * @return The list of medications of the pet for a given date
     */
    public List<Event> getMedicationEventsForDate(String dateTime) {
        ArrayList<Event> medicationEvents = new ArrayList<>();

        for (Event event : events) {
            String eventDate = DateConversion.getDate(event.getDateTime().toString());
            if (event instanceof Medication && eventDate.equals(dateTime)) {
                medicationEvents.add(event);
            }
        }
        return medicationEvents;
    }

    @Override
    public String toString() {
        return "Pet{"
            + "name='" + name + '\''
            + ", gender=" + gender
            + ", breed='" + breed + '\''
            + ", birthDate='" + birthDate + '\''
            + ", healthInfo=" + healthInfo
            + ", owner=" + owner
            + ", previousName='" + previousName + '\''
            + ", profileImage=" + profileImage +
            ", events=" + events
            + '}';
    }

    /**
     * Add a periodic notification to the pet.
     * @param event The event of the pet to set
     * @param period The period of the notification
     */
    public void addPeriodicNotification(Event event, int period) throws ParseException {
        PeriodEvent pe = new PeriodEvent(event.getDescription(), event.getDateTime(), period);
        periodEvents.add(pe);
    }

    /**
     * Get the list of periodic notification events on a date.
     * @param date The date of the events
     * @return The list of periodic notification events on the given date
     */
    public ArrayList<Event> getPeriodicEvents(String date) throws ParseException {
        Date dateActual = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        ArrayList<Event> selectedEvents = new ArrayList<>();
        for (PeriodEvent event : periodEvents) {
            String eventDate = (event.getDateTime()).toString().substring(0,
                    event.getDateTime().toString().indexOf('T'));
            Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(eventDate);
            int diff = (int) ((dateActual.getTime() - date1.getTime()) / 86400000);
            if(diff % event.getPeriod() == 0) {
                selectedEvents.add(new Event(event.getDescription(), event.getDateTime()));
            }
        }
        return selectedEvents;
    }

    /**
     * Delete a periodic notification event of the pet.
     * @param event The event to delete
     */
    public void deletePeriodicNotification(Event event) throws ParseException {
        org.pesmypetcare.usermanager.datacontainers.DateTime dateTime = event.getDateTime();
        String desc = event.getDescription();
        PeriodEvent pe = new PeriodEvent(desc, dateTime, 0);
        periodEvents.remove(pe);
    }

    /**
     * Get the events by the class.
     * @param eventClass The class of the event
     * @return The events of the specified class
     */
    public List<Event> getEventsByClass(Class eventClass) {
        ArrayList<Event> selectedEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getClass().equals(eventClass)) {
                selectedEvents.add(event);
            }
        }
        return selectedEvents;
    }

    /**
     * Check whether the pet contains the specified event.
     * @param dateTime The DateTime of the event
     * @param exercise The exercise class
     * @return True if the pet contains the event
     */
    public boolean containsEvent(DateTime dateTime, Class exercise) {
        for (Event event : events) {
            if (event.getClass().equals(exercise) && event.getDateTime().compareTo(dateTime) == 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * Delete the event by the class.
     * @param dateTime The date of the event
     * @param eventClass The event class
     */
    public void deleteEventByClass(DateTime dateTime, Class eventClass) {
        for (Event event : events) {
            if (event.getClass().equals(eventClass) && event.getDateTime().compareTo(dateTime) == 0) {
                events.remove(event);
                return;
            }
        }
    }

    /**
     * Add an exercise.
     * @param exercise The exercise to add
     */
    public void addExercise(Exercise exercise) {
        addEvent(exercise);
        int minutes = getMinutes(exercise.getDateTime(), exercise.getEndTime());
        String dateTime = exercise.getDateTime().toString();
        String strDate = dateTime.substring(0, dateTime.indexOf('T'));
        DateTime date = DateTime.Builder.buildDateString(strDate);

        if (healthInfo.getExerciseFrequency().containsKey(date)) {
            minutes += healthInfo.getExerciseFrequencyForDate(date);
        }

        healthInfo.addExerciseFrequencyForDate(date, minutes);
    }

    /**
     * Get the minutes duration.
     * @param startDateTime The start DateTime
     * @param endDateTime The end DateTime
     * @return The duration in minutes
     */
    private int getMinutes(DateTime startDateTime, DateTime endDateTime) {
        int startMinutes = startDateTime.getHour() * 60 + startDateTime.getMinutes();
        int endMinutes = endDateTime.getHour() * 60 + endDateTime.getMinutes();

        return endMinutes - startMinutes;
    }

    /**
     * Delete an exercise.
     * @param dateTime The DateTime of the event to delete
     */
    public void deleteExerciseForDate(DateTime dateTime) {
        List<Event> events = getEventsByClass(Exercise.class);
        Exercise exercise = null;

        for (Event event : events) {
            if (event.getDateTime().compareTo(dateTime) == 0) {
                exercise = (Exercise) event;
            }
        }

        String strDateTime = Objects.requireNonNull(exercise).getDateTime().toString();
        String strDate = strDateTime.substring(0, strDateTime.indexOf('T'));
        DateTime date = DateTime.Builder.buildDateString(strDate);
        int duration = getMinutes(exercise.getDateTime(), exercise.getEndTime());

        healthInfo.removeExerciseFrequency(date, duration);
        deleteEventByClass(dateTime, Exercise.class);
    }
}
