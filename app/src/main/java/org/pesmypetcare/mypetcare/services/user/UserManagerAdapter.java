package org.pesmypetcare.mypetcare.services.user;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanager.clients.user.UserManagerClient;
import org.pesmypetcare.usermanager.datacontainers.user.UserData;
import org.pesmypetcare.usermanager.datacontainers.user.UserDataSender;

import java.io.IOException;
import java.util.Objects;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Albert Pinto
 */
public class UserManagerAdapter implements UserManagerService {
    private static final int TIME = 20;
    private static final int TIMEOUT = 5;
    private byte[] userProfileImageBytes;

    @Override
    public User findUserByUsername(String uid, String token) {
        AtomicReference<User> user = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            UserData userData = null;
            try {
                userData = ServiceLocator.getInstance().getUserManagerClient().getUser(token, uid);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            User userReceived = new User(Objects.requireNonNull(userData).getUsername(), userData.getEmail(), "");
            userReceived.setSubscribedGroups(new TreeSet<>(userData.getGroupSubscriptions()));
            user.set(userReceived);
            try {
                assignUserImage(user.get());
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
        return user.get();
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
        AtomicReference<UserData> userData = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                userData.set(ServiceLocator.getInstance().getUserManagerClient().getUser(user.getToken(),
                        user.getUsername()));
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
        return userData.get() != null;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        /*ServiceLocator.getInstance().getUserManagerClient().updatePassword(user.getToken(), user.getUsername(),
            newPassword);*/
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getUserManagerClient().updateField(user.getToken(), user.getUsername(),
                        UserManagerClient.PASSWORD, newPassword);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        return true;
    }

    @Override
    public void deleteUser(User user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getUserManagerClient().deleteUser(user.getToken(), user.getUsername());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void changeMail(String email, User user) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getUserManagerClient().updateField(user.getToken(), user.getUsername(),
                        UserManagerClient.EMAIL, email);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void createUser(String uid, String username, String email, String password) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getUserManagerClient().createUser(new UserDataSender(uid, username,
                        email, password));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deleteUserFromDatabase(User user, String username) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getUserManagerClient().deleteUserFromDatabase(user.getToken(), username);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
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
    public boolean usernameExists(String username) {
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
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return exists.get();
    }

    @Override
    public void changeUsername(User user, String newUsername) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getUserManagerClient().updateField(user.getToken(), user.getUsername(),
                        UserManagerClient.USERNAME, newUsername);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
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
