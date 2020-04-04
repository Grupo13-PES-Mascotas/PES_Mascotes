package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
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
            try {
                byte[] userProfileImageBytes = ServiceLocator.getInstance().getUserManagerClient()
                    .downloadProfileImage(user.getToken(), user.getUsername());
                user.setUserProfileImage(BitmapFactory.decodeByteArray(userProfileImageBytes, 0,
                    userProfileImageBytes.length));
            } catch (ExecutionException | InterruptedException ignored) {

            }
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

    @Override
    public void updateUserImage(User user, Bitmap bitmap) {
        byte[] imageBytes = ImageManager.getImageBytes(bitmap);
        ServiceLocator.getInstance().getUserManagerClient().saveProfileImage(user.getToken(), user.getUsername(),
            imageBytes);
    }
}
