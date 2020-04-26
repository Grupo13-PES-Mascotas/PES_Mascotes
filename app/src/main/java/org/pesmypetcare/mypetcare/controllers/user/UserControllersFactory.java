package org.pesmypetcare.mypetcare.controllers.user;

import org.pesmypetcare.mypetcare.services.MealManagerAdapter;
import org.pesmypetcare.mypetcare.services.MedicationManagerAdapter;
import org.pesmypetcare.mypetcare.services.PetManagerAdapter;
import org.pesmypetcare.mypetcare.services.UserManagerAdapter;

/**
 * @author Albert Pinto
 */
public class UserControllersFactory {
    private UserControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for changing the password.
     * @return The transaction for changing the password
     */
    public static TrChangePassword createTrChangePassword() {
        return new TrChangePassword(new UserManagerAdapter());
    }

    /**
     * Create the transaction for deleting the user.
     * @return The transaction for deleting the user
     */
    public static TrDeleteUser createTrDeleteUser() {
        return new TrDeleteUser(new UserManagerAdapter(), new PetManagerAdapter(), new MealManagerAdapter(),
            new MedicationManagerAdapter());
    }

    /**
     * Create the transaction for obtaining a user.
     * @return The transaction for obtaining a user
     */
    public static TrObtainUser createTrObtainUser() {
        return new TrObtainUser(new UserManagerAdapter(), new PetManagerAdapter());
    }

    /**
     * Create the transaction for changing the mail.
     * @return The transaction for changing the mail
     */
    public static TrChangeMail createTrChangeMail() {
        return new TrChangeMail(new UserManagerAdapter());
    }

    /**
     * Create the transaction for updating the user image.
     * @return The transaction for updating the user image
     */
    public static TrUpdateUserImage createTrUpdateUserImage() {
        return new TrUpdateUserImage(new UserManagerAdapter());
    }

    /**
     * Create the transaction for changing the username.
     * @return The transaction for changing the username
     */
    public static TrChangeUsername createTrChangeUsername() {
        return new TrChangeUsername(new UserManagerAdapter());
    }

    /**
     * Create the transaction for checking whether the user exists.
     * @return The transaction for checking whether the user exists
     */
    public static TrExistsUsername createTrExistsUsername() {
        return new TrExistsUsername(new UserManagerAdapter());
    }
}
