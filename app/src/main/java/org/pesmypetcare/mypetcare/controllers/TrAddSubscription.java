package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupNotExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

/**
 * @author Albert Pinto
 */
public class TrAddSubscription {
    private CommunityService communityService;
    private User user;
    private Group group;

    public TrAddSubscription(CommunityService communityService) {
        this.communityService = communityService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void execute() throws GroupNotExistingException {
        if (!communityService.isGroupExisting(group)) {
            throw new GroupNotExistingException();
        }

        communityService.addSubscriber(user, group);
        user.addSubscribedGroup(group);
    }
}
