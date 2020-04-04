package org.pesmypetcare.mypetcare.controllers;

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void execute() {
        userManagerService.updateUserImage(user, image);
        user.setUserProfileImage(image);
    }
}
