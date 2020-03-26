package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.UserData;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class UserManagerAdapter implements UserManagerService {
    @Override
    public User findUserByUsername(String username) {
        UserData userData = null;

        try {
            userData = ServiceLocator.getInstance().getUserManagerClient().getUser(username);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new User(Objects.requireNonNull(userData).getUsername(), userData.getEmail(), "");
    }

    @Override
    public boolean userExists(String username) {
        UserData userData = null;

        try {
            userData = ServiceLocator.getInstance().getUserManagerClient().getUser(username);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return userData != null;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        ServiceLocator.getInstance().getUserManagerClient().updatePassword(user.getUsername(), newPassword);
        return true;
    }

    @Override
    public void deleteUser(User user) {
        ServiceLocator.getInstance().getUserManagerClient().deleteUser(user.getUsername());
    }

    @Override
    public void changeMail(String email, String username) {
        ServiceLocator.getInstance().getUserManagerClient().updateEmail(username, email);
    }

    @Override
    public void createUser(String uid, String email, String password) {
        ServiceLocator.getInstance().getUserManagerClient().signUp(uid, password, email);
    }
}
