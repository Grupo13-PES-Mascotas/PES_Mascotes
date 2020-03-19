package org.pesmypetcare.mypetcare.features.pets;

import java.time.LocalDate;

public class Pet {
    private String name;
    private Gender gender;
    private String breed;
    private LocalDate birthDate;
    private float weight;
    private String pathologies;
    private float recommendedDailyKiloCalories;
    private int washFrequency;

    public Pet(String name, Gender gender, String breed, LocalDate birthDate, float weight, String pathologies,
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getPathologies() {
        return pathologies;
    }

    public void setPathologies(String pathologies) {
        this.pathologies = pathologies;
    }

    public float getRecommendedDailyKiloCalories() {
        return recommendedDailyKiloCalories;
    }

    public void setRecommendedDailyKiloCalories(float recommendedDailyKiloCalories) {
        this.recommendedDailyKiloCalories = recommendedDailyKiloCalories;
    }

    public int getWashFrequency() {
        return washFrequency;
    }

    public void setWashFrequency(int washFrequency) {
        this.washFrequency = washFrequency;
    }
}
