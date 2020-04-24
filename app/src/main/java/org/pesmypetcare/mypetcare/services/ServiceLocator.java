package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.communitymanager.managers.ForumManagerClient;
import org.pesmypetcare.communitymanager.managers.GroupManagerClient;
import org.pesmypetcare.usermanagerlib.clients.MealManagerClient;
import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;

public class ServiceLocator {
    private static ServiceLocator instance;
    private UserManagerClient userManagerClient;
    private PetManagerClient petManagerClient;
    private MealManagerClient mealManagerClient;
    private GroupManagerClient groupManagerClient;
    private ForumManagerClient forumManagerClient;

    private ServiceLocator() {
        userManagerClient = new UserManagerClient();
        petManagerClient = new PetManagerClient();
        mealManagerClient = new MealManagerClient();
        groupManagerClient = new GroupManagerClient();
        forumManagerClient = new ForumManagerClient();
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

    public GroupManagerClient getGroupManagerClient() {
        return groupManagerClient;
    }

    public ForumManagerClient getForumManagerClient() {
        return forumManagerClient;
    }
}
