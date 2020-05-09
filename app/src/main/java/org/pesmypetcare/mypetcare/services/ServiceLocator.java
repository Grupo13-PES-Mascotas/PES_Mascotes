package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.usermanagerlib.clients.FreqWashManagerClient;
import org.pesmypetcare.usermanagerlib.clients.GoogleCalendarManagerClient;
import org.pesmypetcare.usermanagerlib.clients.MealManagerClient;
import org.pesmypetcare.usermanagerlib.clients.PetCollectionsManagerClient;
import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;

public class ServiceLocator {
    private static ServiceLocator instance;
    private UserManagerClient userManagerClient;
    private PetManagerClient petManagerClient;
    private MealManagerClient mealManagerClient;
    //private WashManagerClient washManagerClient;
    private GoogleCalendarManagerClient googleCalendarManagerClient;
    private PetCollectionsManagerClient petCollectionsManagerClient;
    //private GroupManagerClient groupManagerClient;
    //private ForumManagerClient forumManagerClient;
    //private MedicationManagerClient medicationManagerClient;
    //private WeightManagerClient weightManagerClient;
    private FreqWashManagerClient freqWashManagerClient;

    private ServiceLocator() {
        userManagerClient = new UserManagerClient();
        petManagerClient = new PetManagerClient();
        mealManagerClient = new MealManagerClient();
        googleCalendarManagerClient = new GoogleCalendarManagerClient();
        //groupManagerClient = new GroupManagerClient();
        //forumManagerClient = new ForumManagerClient();
        //medicationManagerClient = new MedicationManagerClient();
        //weightManagerClient = new WeightManagerClient();
        freqWashManagerClient = new FreqWashManagerClient();
        //washManagerClient = new WashManagerClient();
        petCollectionsManagerClient = new PetCollectionsManagerClient();
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
    
    /*public GroupManagerClient getGroupManagerClient() {
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

     */

    public FreqWashManagerClient getFreqWashManagerClient() {
        return freqWashManagerClient;
    }

    public PetCollectionsManagerClient getPetCollectionsManagerClient() {
        return petCollectionsManagerClient;
    }

    /*
    public Object getWashManagerClient() {
        return washManagerClient;
    }

    */
}
