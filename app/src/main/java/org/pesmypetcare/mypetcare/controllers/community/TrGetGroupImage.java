package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

/**
 * @author Albert Pinto
 */
public class TrGetGroupImage {
    private CommunityService communityService;
    private User user;
    private Group group;
    private byte[] result;
    
    public TrGetGroupImage(CommunityService communityService) {
        this.communityService = communityService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void execute() {
        result = communityService.getGroupImage(user, group);
    }

    public byte[] getResult() {
        return result;
    }
}
