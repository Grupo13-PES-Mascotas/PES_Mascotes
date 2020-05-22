package org.pesmypetcare.mypetcare.services.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanager.clients.user.UserManagerClient;
import org.pesmypetcare.usermanager.datacontainers.user.UserData;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Albert Pinto
 */
public class UserManagerAdapter implements UserManagerService {
    private static final int TIME = 20;
    private static final int TIMEOUT = 5;
    private byte[] userProfileImageBytes;

    @Override
    public User findUserByUsername(String uid, String token) throws MyPetCareException {
        UserData userData = null;

        try {
            userData = ServiceLocator.getInstance().getUserManagerClient().getUser(token, uid);
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
    private void assignUserImage(User user) throws MyPetCareException {
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
    private void assignImageFromServer(User user) throws MyPetCareException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        userProfileImageBytes = new byte[0];

        executorService.execute(() -> {
            try {
                userProfileImageBytes = ServiceLocator.getInstance().getUserManagerClient()
                    .downloadProfileImage(user.getToken(), user.getUsername());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        user.setUserProfileImage(BitmapFactory.decodeByteArray(userProfileImageBytes, 0,
            userProfileImageBytes.length));
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
    public void updateUserImage(User user, Bitmap bitmap) throws MyPetCareException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            byte[] imageBytes = ImageManager.getImageBytes(bitmap);
            try {
                ServiceLocator.getInstance().getUserManagerClient().saveProfileImage(user.getToken(),
                    user.getUsername(), imageBytes);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public boolean usernameExists(String username) throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        AtomicBoolean exists = new AtomicBoolean(false);

        executorService.execute(() -> {
            try {
                exists.set(ServiceLocator.getInstance().getUserManagerClient().usernameAlreadyExists(username));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(TIME, TimeUnit.SECONDS);

        return exists.get();
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

    @Override
    public Bitmap obtainUserImage(String username, String accessToken) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        userProfileImageBytes = new byte[0];

        executorService.execute(() -> {
            try {
                userProfileImageBytes = ServiceLocator.getInstance().getUserManagerClient()
                    .downloadProfileImage(accessToken, username);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeByteArray(userProfileImageBytes, 0, userProfileImageBytes.length);
    }

    @Override
    public void sendFirebaseMessagingToken(User user, String token) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getUserManagerClient().sendTokenToServer(user.getToken(), token);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }
}
