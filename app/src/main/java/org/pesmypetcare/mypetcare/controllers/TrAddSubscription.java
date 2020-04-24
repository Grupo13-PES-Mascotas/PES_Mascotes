package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotExistingException;
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

    /**
     * Set the user.
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the group.
     * @param group The group to set
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Execute the transaction.
     * @throws GroupNotExistingException The group does not exist
     */
    public void execute() throws GroupNotExistingException {
        if (!communityService.isGroupExisting(group)) {
            throw new GroupNotExistingException();
        }

        communityService.addSubscriber(user, group);
        user.addSubscribedGroup(group);
    }
}
