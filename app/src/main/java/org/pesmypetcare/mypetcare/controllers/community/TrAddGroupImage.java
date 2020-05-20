package org.pesmypetcare.mypetcare.controllers.community;

import android.graphics.Bitmap;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.NotGroupOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrAddGroupImage {
    private CommunityService communityService;
    private User user;
    private Group group;
    private Bitmap image;
    private boolean result;

    public TrAddGroupImage(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the user that wants to add an image to the group.
     * @param user The user that wants to add an image to the group
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the group where the image has to be added.
     * @param group The group where the image has to be added
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Setter of the image that has to be added to the group.
     * @param image The image that has to be added to the group
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Getter of the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws NotGroupOwnerException, GroupNotFoundException, MyPetCareException {
        result = false;
        if (!user.getUsername().equals(group.getOwnerUsername())) {
            throw new NotGroupOwnerException();
        }
        communityService.addGroupImage(user, group, image);
        group.setGroupIcon(image);
        result = true;
    }
}
