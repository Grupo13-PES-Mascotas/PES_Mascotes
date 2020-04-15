package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanagerlib.clients.UserManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.UserData;

import java.io.IOException;
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
        User user = new User(Objects.requireNonNull(userData).getUsername(), userData.getEmail(), "");
        assignUserImage(user);

        return user;
    }

    /**
     * Assign the image of the user.
     * @param user The user to whom the image has to be assigned
     */
    private void assignUserImage(User user) {
        try {
            byte[] userProfileImageBytes = ImageManager.readImage(ImageManager.USER_PROFILE_IMAGES_PATH,
                user.getUsername());
            user.setUserProfileImage(BitmapFactory.decodeByteArray(userProfileImageBytes, 0,
                userProfileImageBytes.length));
        } catch (IOException e) {
            assignImageFromServer(user);
        }
    }

    /**
     * Assign the user image from the server.
     * @param user The user that has to be assigned an image
     */
    private void assignImageFromServer(User user) {
        try {
            byte[] userProfileImageBytes = ServiceLocator.getInstance().getUserManagerClient()
                .downloadProfileImage(user.getToken(), user.getUsername());
            user.setUserProfileImage(BitmapFactory.decodeByteArray(userProfileImageBytes, 0,
                userProfileImageBytes.length));
        } catch (ExecutionException | InterruptedException ignored) {

        }
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
        /*ServiceLocator.getInstance().getUserManagerClient().updatePassword(user.getToken(), user.getUsername(),
            newPassword);*/

        try {
            ServiceLocator.getInstance().getUserManagerClient().updateField(user.getToken(), user.getUsername(),
                UserManagerClient.PASSWORD, newPassword);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public void deleteUser(User user) {
        try {
            ServiceLocator.getInstance().getUserManagerClient().deleteUser(user.getToken(), user.getUsername());
            ServiceLocator.getInstance().getUserManagerClient().deleteUserFromDatabase(user.getToken(),
                    user.getUsername());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeMail(String email, User user) {
        //ServiceLocator.getInstance().getUserManagerClient().updateEmail(user.getToken(), user.getUsername(), email);
        try {
            ServiceLocator.getInstance().getUserManagerClient().updateField(user.getToken(), user.getUsername(),
                UserManagerClient.EMAIL, email);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser(String uid, String username, String email, String password) {
        try {
            ServiceLocator.getInstance().getUserManagerClient().createUser(uid,
                    new UserData(username, email, password));
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserFromDatabase(String username) {
        try {
            ServiceLocator.getInstance().getUserManagerClient().deleteUserFromDatabase("token", username);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUserImage(User user, Bitmap bitmap) {
        byte[] imageBytes = ImageManager.getImageBytes(bitmap);
        try {
            ServiceLocator.getInstance().getUserManagerClient().saveProfileImage(user.getToken(), user.getUsername(),
                imageBytes);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean usernameExists(String username) throws ExecutionException, InterruptedException {
        return ServiceLocator.getInstance().getUserManagerClient().usernameAlreadyExists(username);
    }

    @Override
    public void changeUsername(User user, String newUsername) {
        try {
            ServiceLocator.getInstance().getUserManagerClient().updateField(user.getToken(), user.getUsername(),
                    UserManagerClient.USERNAME, newUsername);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
