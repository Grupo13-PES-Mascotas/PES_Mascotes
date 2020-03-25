package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.usermanagerlib.UserManagerClient;

public class ServiceLocator {
    private static ServiceLocator instance;
    private UserManagerClient userManagerClient;

    private ServiceLocator() {
        userManagerClient = new UserManagerClient();
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
}
