package org.pesmypetcare.mypetcare.features.pets;

import android.graphics.Bitmap;
import android.os.Bundle;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.Objects;

public class Pet {
    public static final String BUNDLE_NAME = "petName";
    public static final String BUNDLE_BREED = "petBreed";
    public static final String BUNDLE_BIRTH_DATE = "petBirthDate";
    public static final String BUNDLE_WEIGHT = "petFloat";
    public static final String BUNDLE_PATHOLOGIES = "petPathologies";
    public static final String BUNDLE_CALORIES = "petCalories";
    public static final String BUNDLE_WASH = "petWash";
    public static final String BUNDLE_GENDER = "petGender";
    private String name;
    private GenderType gender;
    private String breed;
    private String birthDate;
    private double weight;
    private String pathologies;
    private double recommendedDailyKiloCalories;
    private int washFrequency;
    private User owner;
    private String previousName;
    private Bitmap profileImage;

    public Pet() {
    }

    public Pet(Bundle petInfo) {
        this.name = petInfo.getString(BUNDLE_NAME);
        this.breed = petInfo.getString(BUNDLE_BREED);
        this.birthDate = petInfo.getString(BUNDLE_BIRTH_DATE);
        this.weight = petInfo.getFloat(BUNDLE_WEIGHT);
        this.pathologies = petInfo.getString(BUNDLE_PATHOLOGIES);
        this.recommendedDailyKiloCalories = petInfo.getFloat(BUNDLE_CALORIES);
        this.washFrequency = petInfo.getInt(BUNDLE_WASH);

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
        this.birthDate = petInfo.getString(BUNDLE_BIRTH_DATE);
        this.weight = petInfo.getFloat(BUNDLE_WEIGHT);
        this.pathologies = petInfo.getString(BUNDLE_PATHOLOGIES);
        this.recommendedDailyKiloCalories = petInfo.getFloat(BUNDLE_CALORIES);
        this.washFrequency = petInfo.getInt(BUNDLE_WASH);

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
        return weight;
    }

    /**
     * Set the weight of the pet.
     * @param weight The weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Get the pathologies of the pet.
     * @return The pathologies of the pet
     */
    public String getPathologies() {
        return pathologies;
    }

    /**
     * Set the pathologies of the pet.
     * @param pathologies The pathologies to set
     */
    public void setPathologies(String pathologies) {
        this.pathologies = pathologies;
    }

    /**
     * Get the recommended daily kilo calories of the pet.
     * @return The recommended daily kilo calories of the pet
     */
    public double getRecommendedDailyKiloCalories() {
        return recommendedDailyKiloCalories;
    }

    /**
     * Set the recommended daily kilo calories of the pet.
     * @param recommendedDailyKiloCalories The recommended daily kilo calories of the pet to set
     */
    public void setRecommendedDailyKiloCalories(double recommendedDailyKiloCalories) {
        this.recommendedDailyKiloCalories = recommendedDailyKiloCalories;
    }

    /**
     * Get the wash frequency of the pet.
     * @return The wash frequency of the pet
     */
    public int getWashFrequency() {
        return washFrequency;
    }

    /**
     * Set the wash frequency of the pet.
     * @param washFrequency The wash frequency to set
     */
    public void setWashFrequency(int washFrequency) {
        this.washFrequency = washFrequency;
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
}
