package org.pesmypetcare.mypetcare.services.user;

import android.graphics.Bitmap;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.concurrent.ExecutionException;

/**
 * @author Albert Pinto
 */
public interface UserManagerService {

    /**
     * Registers a new user.
     *
     * @param uid The uid of the user
     * @param token The username of the user to be registered
     * @return True if the register has been done without any problems
     */
    User findUserByUsername(String uid, String token) throws MyPetCareException;

    /**
     * Checks if the user had been registered.
     * @param user The user id
     * @return True if the user already exists, false otherwise
     */
    boolean userExists(User user);

    /**
     * Change the password of a user.
     * @param user The user
     * @param newPassword The new password
     * @return True if the change has been done without any problems
     */
    boolean changePassword(User user, String newPassword);

    /**
     * Delete a user.
     * @param user The user
     */
    void deleteUser(User user);

    /**
     * Changes the mail.
     * @param mail The mail that user wants to set
     * @param user The user which wants to change his mail
     */
    void changeMail(String mail, User user);

    /**
     * Creates the user.
     * @param username User username
     * @param uid User identifier
     * @param email User email
     * @param password User password
     */
    void createUser(String uid, String username, String email, String password);

    /**
     * Delete a user from database.
     * @param username User username
     */
    void deleteUserFromDatabase(String username);

    /**
     * Updates the image of the user.
     * @param user The user that wants his image to be updated
     * @param bitmap The bitmap of the image to be assigned
     */
    void updateUserImage(User user, Bitmap bitmap) throws MyPetCareException;

    /**
     * User with this username exists.
     * @param username The username
     * @return True if exists a user with the same username, false otherwise
     */
    boolean usernameExists(String username) throws ExecutionException, InterruptedException;

    /**
     * Change the username of a user.
     * @param user The user
     * @param newUsername The new username
     * @return True if the change has been done without any problems
     */
    void changeUsername(User user, String newUsername);

    /**
     * Obtains the image of the given username.
     * @param username The username from which we want to obtain the image
     * @param accessToken The access token of the current user
     * @throws MyPetCareException There has been a problem with the server connection
     */
    Bitmap obtainUserImage(String username, String accessToken) throws ExecutionException, InterruptedException,
        MyPetCareException;

    /**
     * Sent the firebase messaging token.
     * @param user The user of the token
     * @param token The messaging token
     */
    void sendFirebaseMessagingToken(User user, String token);
}
