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
            userData = ServiceLocator.getInstance().getUserManagerClient().getUser("token", username);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new User(Objects.requireNonNull(userData).getUsername(), userData.getEmail(), "");
    }

    @Override
    public boolean userExists(User user) {
        UserData userData = null;

        try {
            userData = ServiceLocator.getInstance().getUserManagerClient().getUser(user.getToken(), user.getUsername());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return userData != null;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        ServiceLocator.getInstance().getUserManagerClient().updatePassword(user.getToken(), user.getUsername(),
            newPassword);
        return true;
    }

    @Override
    public void deleteUser(User user) {
        ServiceLocator.getInstance().getUserManagerClient().deleteUser(user.getToken(), user.getUsername());
    }

    @Override
    public void changeMail(String email, User user) {
        ServiceLocator.getInstance().getUserManagerClient().updateEmail(user.getToken(), user.getUsername(), email);
    }

    @Override
    public void createUser(String uid, String email, String password) {
        ServiceLocator.getInstance().getUserManagerClient().signUp(uid, password, email);
    }
}
