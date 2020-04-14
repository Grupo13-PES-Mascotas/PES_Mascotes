package org.pesmypetcare.mypetcare.features.pets;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.text.DateFormat;
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

    public Pet() {
        this.events = new ArrayList<>();
        this.healthInfo = new PetHealthInfo();
        this.birthDate = DateTime.Builder.buildDateString("2020-01-1");
    }

    public Pet(Bundle petInfo) {
        this.name = petInfo.getString(BUNDLE_NAME);
        this.breed = petInfo.getString(BUNDLE_BREED);
        this.birthDate = DateTime.Builder.buildDateString(Objects.requireNonNull(petInfo.getString(BUNDLE_BIRTH_DATE)));
        initializeHealthInfo(petInfo);
        this.events = new ArrayList<>();

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
        DateTime dateTime = getActualDateTime();

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
        this.birthDate = DateTime.Builder.buildDateString(Objects.requireNonNull(petInfo.getString(BUNDLE_BIRTH_DATE)));
        initializeHealthInfo(petInfo);
        this.events = new ArrayList<>();

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
        DateTime dateTime = getActualDateTime();
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

    public PetHealthInfo getHealthInfo() {
        return healthInfo;
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

    public void deleteWeightForDate(DateTime dateTime) {
        healthInfo.deleteWeightForDate(dateTime);
    }

    public void deleteWashFrequencyForDate(DateTime dateTime) {
        healthInfo.deleteWashFrequencyForDate(dateTime);
    }

    public boolean isOwner(User user) {
        return user.equals(owner);
    }

    public void setWeightForDate(double newWeight, DateTime dateTime) {
        healthInfo.addWeightForDate(dateTime, newWeight);
    }

    public void setWashFrequencyForDate(int newWashFrequency, DateTime dateTime) {
        healthInfo.addWashFrequencyForDate(dateTime, newWashFrequency);
    }

    @NonNull
    @Override
    public String toString() {
        return "Pet{" +
            "name='" + name + '\'' +
            ", gender=" + gender +
            ", breed='" + breed + '\'' +
            ", birthDate='" + birthDate + '\'' +
            ", healthInfo=" + healthInfo +
            ", owner=" + owner +
            ", previousName='" + previousName + '\'' +
            ", profileImage=" + profileImage +
            ", events=" + events +
            '}';
    }
}
