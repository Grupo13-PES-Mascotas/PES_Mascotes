package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupNotExistingException;
import org.pesmypetcare.mypetcare.features.community.NotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.OwnerCannotDeleteSubscriptionException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrDeleteSubscription {
    private CommunityService communityService;
    private User user;
    private Group group;

    public TrDeleteSubscription(CommunityService communityService) {
        this.communityService = communityService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void execute() throws GroupNotExistingException, NotSubscribedException, OwnerCannotDeleteSubscriptionException {
        if (!communityService.isGroupExisting(group)) {
            throw new GroupNotExistingException();
        } else if (group.getOwnerUsername().equals(user.getUsername())) {
            throw new OwnerCannotDeleteSubscriptionException();
        } else if (!group.isUserSubscriber(user)) {
            throw new NotSubscribedException();
        }
        communityService.deleteSubscriber(user, group);
        user.removeSubscribedGroup(group);
    }
}
