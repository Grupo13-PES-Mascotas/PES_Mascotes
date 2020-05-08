package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.communitymanager.managers.ForumManagerClient;
import org.pesmypetcare.communitymanager.managers.GroupManagerClient;
import org.pesmypetcare.usermanager.clients.GoogleCalendarManagerClient;
import org.pesmypetcare.usermanager.clients.pet.FreqWashManagerClient;
import org.pesmypetcare.usermanager.clients.pet.MealManagerClient;
import org.pesmypetcare.usermanager.clients.pet.MedicationManagerClient;
import org.pesmypetcare.usermanager.clients.pet.PetManagerClient;
import org.pesmypetcare.usermanager.clients.pet.WeightManagerClient;
import org.pesmypetcare.usermanager.clients.user.UserManagerClient;

public class ServiceLocator {
    private static ServiceLocator instance;
    private UserManagerClient userManagerClient;
    private PetManagerClient petManagerClient;
    private MealManagerClient mealManagerClient;
    //private WashManagerClient washManagerClient;
    private GoogleCalendarManagerClient googleCalendarManagerClient;
    private GroupManagerClient groupManagerClient;
    private ForumManagerClient forumManagerClient;
    private MedicationManagerClient medicationManagerClient;
    private WeightManagerClient weightManagerClient;
    private FreqWashManagerClient freqWashManagerClient;

    private ServiceLocator() {
        userManagerClient = new UserManagerClient();
        petManagerClient = new PetManagerClient();
        mealManagerClient = new MealManagerClient();
        googleCalendarManagerClient = new GoogleCalendarManagerClient();
        groupManagerClient = new GroupManagerClient();
        forumManagerClient = new ForumManagerClient();
        medicationManagerClient = new MedicationManagerClient();
        weightManagerClient = new WeightManagerClient();
        freqWashManagerClient = new FreqWashManagerClient();
        //washManagerClient = new WashManagerClient();
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

    public GoogleCalendarManagerClient getGoogleCalendarManagerClient() {
        return googleCalendarManagerClient;
    }
    
    public GroupManagerClient getGroupManagerClient() {
        return groupManagerClient;
    }

    public ForumManagerClient getForumManagerClient() {
        return forumManagerClient;
    }
    
    public MedicationManagerClient getMedicationManagerClient() {
        return medicationManagerClient;
    }

    public WeightManagerClient getWeightManagerClient() {
        return weightManagerClient;
    }

    public FreqWashManagerClient getFreqWashManagerClient() {
        return freqWashManagerClient;
    }

    /*
    public Object getWashManagerClient() {
        return washManagerClient;
    }

    */
}
