package org.pesmypetcare.mypetcare.features.pets;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Pair;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.mypetcare.utilities.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

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
    private String birthDate;
    private PetHealthInfo healthInfo;
    private User owner;
    private String previousName;
    private Bitmap profileImage;
    private ArrayList<Event> events;
    private HashMap<Integer, Integer> periodsWeek;
    private HashMap<Integer,Integer> periodsMonth;
    private boolean dailyNotification;
    private ArrayList<Event> eventPeriodWeek;
    private ArrayList<Event> eventPeriodMonth;
    private ArrayList<Pair<String, String>> dailyEvents;


    public Pet() {
        this.events = new ArrayList<>();
        this.healthInfo = new PetHealthInfo();
        this.periodsWeek = new HashMap<Integer, Integer>();
        this.periodsMonth = new HashMap<Integer, Integer>();
        this.dailyNotification = false;
        this.eventPeriodWeek = new ArrayList<>(7);
        this.eventPeriodMonth = new ArrayList<>(31);
        this.dailyEvents = new ArrayList<Pair<String, String>>();
        initializeEventsPeriod();
    }

    public Pet(Bundle petInfo) {
        this.name = petInfo.getString(BUNDLE_NAME);
        this.breed = petInfo.getString(BUNDLE_BREED);
        this.birthDate = petInfo.getString(BUNDLE_BIRTH_DATE);
        initializeHealthInfo(petInfo);
        this.events = new ArrayList<>();
        this.periodsWeek = new HashMap<Integer, Integer>();
        this.periodsMonth = new HashMap<Integer, Integer>();
        this.dailyNotification = false;
        this.eventPeriodWeek = new ArrayList<>(7);
        this.eventPeriodMonth = new ArrayList<>(31);
        initializeEventsPeriod();

        if (isMale(petInfo)) {
            this.gender = GenderType.Male;
        } else if (isFemale(petInfo)) {
            this.gender = GenderType.Female;
        } else {
            this.gender = GenderType.Other;
        }
    }

    /**
     * Method that initializes the health info of the pet.
     * @param petInfo A bundle containing all the information of the pet
     */
    public void initializeHealthInfo(Bundle petInfo) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);
        DateTime dateTime = new DateTime(strData);
        this.healthInfo = new PetHealthInfo();
        this.healthInfo.addWeightForDate(dateTime, petInfo.getFloat(BUNDLE_WEIGHT));
        this.healthInfo.setPathologies(petInfo.getString(BUNDLE_PATHOLOGIES));
        this.healthInfo.addRecommendedDailyKiloCaloriesForDate(dateTime, calculateRecommendedKiloCalories());
        this.healthInfo.addWashFrequencyForDate(dateTime, petInfo.getInt(BUNDLE_WASH));
    }

    /**
     * Calculate the recommended kilocalories for the pet
     * @return The recommended kilocalories
     */
    private double calculateRecommendedKiloCalories() {
        return healthInfo.getLastWeight() * FACTOR_PES_1 + FACTOR_PES_2;
    }

    public Pet(Bundle petInfo, User user) {
        this.name = petInfo.getString(BUNDLE_NAME);
        this.breed = petInfo.getString(BUNDLE_BREED);
        this.birthDate = petInfo.getString(BUNDLE_BIRTH_DATE);
        initializeHealthInfo(petInfo);
        this.events = new ArrayList<>();
        this.periodsWeek = new HashMap<Integer, Integer>();
        this.periodsMonth = new HashMap<Integer, Integer>();
        this.dailyNotification = false;
        this.eventPeriodWeek = new ArrayList<>(7);
        this.eventPeriodMonth = new ArrayList<>(31);
        initializeEventsPeriod();


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
        this.periodsWeek = new HashMap<Integer, Integer>();
        this.periodsMonth = new HashMap<Integer, Integer>();
        this.dailyNotification = false;
        this.eventPeriodWeek = new ArrayList<>(7);
        this.eventPeriodMonth = new ArrayList<>(31);
        initializeEventsPeriod();

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
        return birthDate;
    }

    /**
     * Set the birth date of the pet.
     * @param birthDate The birth date to set
     */
    public void setBirthDate(String birthDate) {
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);
        DateTime dateTime = new DateTime(strData);
        this.healthInfo.addWeightForDate(dateTime, weight);
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);
        DateTime dateTime = new DateTime(strData);
        healthInfo.addRecommendedDailyKiloCaloriesForDate(dateTime, recommendedDailyKiloCalories);
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = new Date();
        String strData = dateFormat.format(date);
        DateTime dateTime = new DateTime(strData);
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
     * Add an event to the pet.
     * @param event The event of the pet to set
     */
    public void addEvent(Event event) {
        events.add(event);
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
            String eventDate = DateConversion.getDate(event.getDateTime());
            if (eventDate.equals(date)) {
                selectedEvents.add(event);
            }
        }

        return selectedEvents;
    }

    @Override
    public String toString() {
        return "{" + name + ", " + (profileImage == null ? "NULL" : "NO_NULL") + "}";
    }

    public void addPeriodicNotification(Event event, int period, int day) throws ParseException {
        if (period == 7 || period == 14) {
            putInPeriodsWeek(period, day);
            eventPeriodWeek.add(day-1, event);
        }
        else if (period == -1 || period == -3) {
            putInPeriodsMonth(period, day);
            eventPeriodMonth.add(day-1, event);
        }
        else if(period == 0) {
            dailyNotification = true;
            String date = event.getDateTime();
            String[] separate = date.split("'T'");
            String time = separate[1];
            dailyEvents.add(new  Pair<String, String>(time, event.getDescription()));
        }
    }

    private void putInPeriodsMonth(int period, int day) {
        if (period == -1) {
            periodsMonth.put(day, 0);
        }
        else {
            periodsMonth.put(day, 2);
        }
    }

    private void putInPeriodsWeek(int period, int day) {
        if (period == 7) {
            periodsWeek.put(day, 0);
        }
        else {
            periodsWeek.put(day, 1);
        }
    }

    public Event getPeriodicNotificationDay(String dateText) throws ParseException {
        int dayOfWeek = getDayOfWeek(dateText);
        int dayOfMonth = getDayOfMonth(dateText);
        if (dailyNotification) {
            getDailyNotifications();
        }
        if (periodsWeek.containsKey(dayOfWeek)) {
            if (periodsWeek.get(dayOfWeek) == 0) {
                return eventPeriodWeek.get(dayOfWeek - 1);
            }
            else if (periodsWeek.get(dayOfWeek) == 1) {
                if (itsthedayWeek(eventPeriodWeek.get(dayOfWeek - 1), dateText)) {
                    return eventPeriodWeek.get(dayOfWeek - 1);
                }
            }
        }
        if (periodsMonth.containsKey(dayOfMonth)) {
            if (periodsMonth.get(dayOfMonth) == 0) {
                return eventPeriodMonth.get(dayOfMonth - 1);
            }
            else if (periodsMonth.get(dayOfMonth) == 2) {
                if (itsthedayMonth(eventPeriodMonth.get(dayOfMonth - 1), dateText)) {
                    return eventPeriodMonth.get(dayOfMonth - 1);
                }
            }
        }
        return null;
    }

    private void getDailyNotifications() {
    }


    private boolean itsthedayMonth(Event event, String actualDate) throws ParseException {
        String dateTime = event.getDateTime();
        Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTime);
        Date dateActual = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(actualDate);
        int diff =(int) ((dateActual.getTime()-date1.getTime())/86400000);
        return (diff % 90) < 15;
    }

    private boolean itsthedayWeek(Event event, String actualDate) throws ParseException {
        String dateTime = event.getDateTime();
        Date date1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateTime);
        Date dateActual = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(actualDate);
        int diff =(int) ((dateActual.getTime()-date1.getTime())/86400000);
        return (diff % 14) == 0;
    }

    private void initializeEventsPeriod() {
        Event e = new Event("", "");
        for (int i = 0; i < 6; ++i) eventPeriodWeek.add(i, e);
        for (int i = 0; i < 30; ++i) eventPeriodMonth.add(i, e);
    }

    private int getDayOfWeek(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date date = formatter.parse(dateString.replaceAll("Z$", "+0000"));
        DateFormat dfWeek = new SimpleDateFormat("u", Locale.ENGLISH);
        return Integer.parseInt(dfWeek.format(date));
    }

    private int getDayOfMonth(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        Date date = formatter.parse(dateString.replaceAll("Z$", "+0000"));
        DateFormat dfMonth = new SimpleDateFormat("dd", Locale.ENGLISH);
        return Integer.parseInt(dfMonth.format(date));
    }

    public void deletePeriodicNotification(String date, int period, String desc) throws ParseException {
        int day;
        Event e = getPeriodicNotificationDay(date);
        if (eventPeriodWeek.contains(e)) {
            day = getDayOfWeek(date);
            eventPeriodWeek.remove(day);
            periodsWeek.remove(day);
        }
        if (eventPeriodMonth.contains(e)) {
            day = getDayOfMonth(date);
            eventPeriodMonth.remove(day);
            periodsMonth.remove(day);
        }
        if (period == 0) {
            String[] separate = date.split("'T'");
            String time = separate[1];
            dailyEvents.remove(new Pair<String, String> (time, desc));
        }
    }
}
