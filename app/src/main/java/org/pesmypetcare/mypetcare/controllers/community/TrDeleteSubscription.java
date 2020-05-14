package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.NotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.OwnerCannotDeleteSubscriptionException;
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

    /**
     * Setter of the user that has to be deleted from the group.
     * @param user The user that has to be deleted from the group
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the group from which the user has to be deleted.
     * @param group The group from which the user has to be deleted
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Execute the transaction.
     * @throws GroupNotFoundException Exception thrown when the group does not exist
     * @throws NotSubscribedException Exception thrown when the user is not subscribed to the group
     * @throws OwnerCannotDeleteSubscriptionException Exception thrown when the owner of the group tries to
     * unsubscribe from the group
     */
    public void execute() throws GroupNotFoundException, NotSubscribedException,
        OwnerCannotDeleteSubscriptionException {
        boolean groupExisting = communityService.isGroupExisting(group);
        if (!groupExisting) {
            throw new GroupNotFoundException();
        } else if (group.getOwnerUsername().equals(user.getUsername())) {
            throw new OwnerCannotDeleteSubscriptionException();
        } else if (!group.isUserSubscriber(user)) {
            throw new NotSubscribedException();
        }

        communityService.deleteSubscriber(user, group);
        user.removeSubscribedGroup(group);
    }
}
