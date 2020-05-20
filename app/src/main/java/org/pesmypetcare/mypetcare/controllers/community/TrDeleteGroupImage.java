package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.NotGroupOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrDeleteGroupImage {
    private CommunityService communityService;
    private User user;
    private Group group;
    private boolean result;

    public TrDeleteGroupImage(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the user that wants to delete the image of the group.
     * @param user The user that wants to delete the image of the group
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the group which image has to deleted.
     * @param group The group which image has to be deleted
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the image was deleted successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     * @throws NotGroupOwnerException Exception thrown if the user is not the owner of the group
     */
    public void execute() throws NotGroupOwnerException, GroupNotFoundException {
        result = false;
        if (!user.getUsername().equals(group.getOwnerUsername())) {
            throw new NotGroupOwnerException();
        }
        communityService.deleteGroupImage(user, group);
        group.setGroupIcon(null);
        result = true;
    }
}
