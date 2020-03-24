package org.pesmypetcare.mypetcare.activities.fragments;

import org.pesmypetcare.mypetcare.features.users.NotValidUserException;
import org.pesmypetcare.mypetcare.features.users.User;

public interface SettingsCommunication {

    /**
     * Passes the information to change the password to the main activity.
     * @param password The new password
     */
    void changePassword(String password);

    /**
     * Delete a user.
     * @param user The user
     */
    void deleteUser(User user) throws NotValidUserException;
}
