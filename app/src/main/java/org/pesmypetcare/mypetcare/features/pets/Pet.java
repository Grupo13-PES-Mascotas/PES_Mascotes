package org.pesmypetcare.mypetcare.features.pets;

public class Pet {
    private String name;
    private Gender gender;
    private String breed;
    private String birthDate;
    private float weight;
    private String pathologies;
    private float recommendedDailyKiloCalories;
    private int washFrequency;

    public Pet(String name, Gender gender, String breed, String birthDate, float weight, String pathologies,
               float recommendedDailyKiloCalories, int washFrequency) {
        this.name = name;
        this.gender = gender;
        this.breed = breed;
        this.birthDate = birthDate;
        this.weight = weight;
        this.pathologies = pathologies;
        this.recommendedDailyKiloCalories = recommendedDailyKiloCalories;
        this.washFrequency = washFrequency;
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
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the gender of the pet.
     * @return The gender of the pet
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Set the gender of the pet.
     * @param gender The gender of the pet to set
     */
    public void setGender(Gender gender) {
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
    public float getWeight() {
        return weight;
    }

    /**
     * Set the weight of the pet.
     * @param weight The weight to set
     */
    public void setWeight(float weight) {
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
    public float getRecommendedDailyKiloCalories() {
        return recommendedDailyKiloCalories;
    }

    /**
     * Set the recommended daily kilo calories of the pet.
     * @param recommendedDailyKiloCalories The recommended daily kilo calories of the pet to set
     */
    public void setRecommendedDailyKiloCalories(float recommendedDailyKiloCalories) {
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
}
