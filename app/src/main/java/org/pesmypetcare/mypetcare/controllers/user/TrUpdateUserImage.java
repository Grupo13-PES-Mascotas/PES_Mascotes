package org.pesmypetcare.mypetcare.controllers.user;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrUpdateUserImage {
    private UserManagerService userManagerService;
    private User user;
    private Bitmap image;

    public TrUpdateUserImage(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the user to update the image.
     * @param user The user that wants to update his profile image
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the image that has to be assigned to the user.
     * @param image The image that has to be assigned to the user
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Executes the transaction.
     */
    public void execute() {
        userManagerService.updateUserImage(user, image);
        user.setUserProfileImage(image);
    }
}
