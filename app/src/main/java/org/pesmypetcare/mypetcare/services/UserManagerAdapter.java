package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAlreadyExistingException;

public class UserManagerAdapter implements UserManagerService {
    @Override
    public boolean registerNewUser(String username, String email, String password) throws UserAlreadyExistingException {
        return false;
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        return false;
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void changeMail(String mail, String username) {

    }
}
