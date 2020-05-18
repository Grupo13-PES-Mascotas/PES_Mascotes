package org.pesmypetcare.mypetcare.controllers.user;

import android.graphics.Bitmap;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.services.UserManagerService;

import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class TrObtainUserImage {
    private UserManagerService userManagerService;
    private String username;
    private String accessToken;
    private Bitmap result;

    public TrObtainUserImage(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Setter of the username from which we want to obtain the image.
     * @param username The username from which we want to obtain the image
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter of the token of the current user.
     * @param accessToken The token of the current user
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Getter of the image of the user.
     * @return The image of the user
     */
    public Bitmap getResult() {
        return result;
    }

    public void execute() throws ExecutionException, InterruptedException, MyPetCareException {
        result = userManagerService.obtainUserImage(username, accessToken);
    }
}
