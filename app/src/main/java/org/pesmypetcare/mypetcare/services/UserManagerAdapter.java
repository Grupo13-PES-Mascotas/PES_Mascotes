package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;
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
    public boolean userExists(String username) {
        UserData userData = null;

        try {
            userData = ServiceLocator.getInstance().getUserManagerClient().getUser("token", username);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return userData != null;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        /*ServiceLocator.getInstance().getUserManagerClient().updatePassword(user.getToken(), user.getUsername(),
            newPassword);*/

        try {
            ServiceLocator.getInstance().getUserManagerClient().updateField("token", user.getUsername(),
                UserManagerClient.PASSWORD, newPassword);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void deleteUser(User user) {
        try {
            ServiceLocator.getInstance().getUserManagerClient().deleteUser("token", user.getUsername());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeMail(String email, String username) {
        //ServiceLocator.getInstance().getUserManagerClient().updateEmail(user.getToken(), user.getUsername(), email);
        try {
            ServiceLocator.getInstance().getUserManagerClient().updateField("token", username,
                UserManagerClient.EMAIL, email);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser(String uid, String email, String password) {
        try {
            ServiceLocator.getInstance().getUserManagerClient().signUp(uid, password, email);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
