package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrDeleteForum {
    private CommunityService communityService;
    private User user;
    private Group group;
    private Forum forum;
    private boolean result;

    public TrDeleteForum(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the author of the forum to delete.
     * @param user The author of the forum to delete
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the group from which the forum has to be deleted.
     * @param group The group from which the forum has to be deleted
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Setter of the forum to delete.
     * @param forum The forum to delete
     */
    public void setForum(Forum forum) {
        this.forum = forum;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the forum was deleted successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws ForumNotFoundException, NotForumOwnerException {
        result = false;
        communityService.deleteForum(user, group, forum);
        group.removeForum(forum);
        result = true;
    }
}
