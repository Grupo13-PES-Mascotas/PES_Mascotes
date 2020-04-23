package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.usermanagerlib.clients.MealManagerClient;
import org.pesmypetcare.usermanagerlib.clients.MedicationManagerClient;
import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;

public class ServiceLocator {
    private static ServiceLocator instance;
    private UserManagerClient userManagerClient;
    private PetManagerClient petManagerClient;
    private MealManagerClient mealManagerClient;
    private MedicationManagerClient medicationManagerClient;

    private ServiceLocator() {
        userManagerClient = new UserManagerClient();
        petManagerClient = new PetManagerClient();
        mealManagerClient = new MealManagerClient();
        medicationManagerClient = new MedicationManagerClient();
    }

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }

        return instance;
    }

    public UserManagerClient getUserManagerClient() {
        return userManagerClient;
    }

    public PetManagerClient getPetManagerClient() {
        return petManagerClient;
    }

    public MealManagerClient getMealManagerClient() {
        return mealManagerClient;
    }

    public MedicationManagerClient getMedicationManagerClient() {
        return medicationManagerClient;
    }
}
