package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.communitymanager.managers.ForumManagerClient;
import org.pesmypetcare.communitymanager.managers.GroupManagerClient;
import org.pesmypetcare.usermanager.clients.GoogleCalendarManagerClient;
import org.pesmypetcare.usermanager.clients.pet.PetCollectionsManagerClient;
import org.pesmypetcare.usermanager.clients.pet.PetManagerClient;
import org.pesmypetcare.usermanager.clients.user.UserManagerClient;
import org.pesmypetcare.usermanager.clients.user.UserMedalManagerClient;

/**
 * @author Albert Pinto
 */
public class ServiceLocator {
    private static ServiceLocator instance;
    private UserManagerClient userManagerClient;
    private PetManagerClient petManagerClient;
    private GoogleCalendarManagerClient googleCalendarManagerClient;
    private PetCollectionsManagerClient petCollectionsManagerClient;
    private GroupManagerClient groupManagerClient;
    private ForumManagerClient forumManagerClient;
    private UserMedalManagerClient userMedalManagerClient;

    private ServiceLocator() {
        userManagerClient = new UserManagerClient();
        petManagerClient = new PetManagerClient();
        googleCalendarManagerClient = new GoogleCalendarManagerClient();
        groupManagerClient = new GroupManagerClient();
        forumManagerClient = new ForumManagerClient();
        petCollectionsManagerClient = new PetCollectionsManagerClient();
        userMedalManagerClient = new UserMedalManagerClient();
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

    public GoogleCalendarManagerClient getGoogleCalendarManagerClient() {
        return googleCalendarManagerClient;
    }
    
    public GroupManagerClient getGroupManagerClient() {
        return groupManagerClient;
    }

    public ForumManagerClient getForumManagerClient() {
        return forumManagerClient;
    }

    public PetCollectionsManagerClient getPetCollectionsManagerClient() {
        return petCollectionsManagerClient;
    }

    public UserMedalManagerClient getUserMedalManagerClient() {
        return userMedalManagerClient;
    }
}
