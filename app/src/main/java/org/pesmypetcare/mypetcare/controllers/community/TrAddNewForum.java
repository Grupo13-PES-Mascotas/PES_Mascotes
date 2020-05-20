package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumCreatedBeforeGroupException;
import org.pesmypetcare.mypetcare.features.community.forums.UserNotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

import java.util.List;

/**
 * @author Albert Pinto
 */
public class TrAddNewForum {
    private CommunityService communityService;
    private User user;
    private Group group;
    private String forumName;
    private List<String> tags;
    private DateTime creationDate;

    public TrAddNewForum(CommunityService communityService) {
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
     * Set the forum name.
     * @param forumName The forum name to set
     */
    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    /**
     * Set the tags.
     * @param tags The tags to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Set the creation date.
     * @param creationDate The creation date to set
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Execute the transaction.
     * @throws UserNotSubscribedException The user is not subscribed
     * @throws GroupNotFoundException The group does not exist
     * @throws ForumCreatedBeforeGroupException The forum has been created before the group
     */
    public void execute() throws UserNotSubscribedException, GroupNotFoundException,
        ForumCreatedBeforeGroupException {

        if (!communityService.isGroupExisting(group)) {
            throw new GroupNotFoundException();
        } else if (!group.isUserSubscriber(user)) {
            throw new UserNotSubscribedException();
        } else if (group.getCreationDate().compareTo(creationDate) > 0) {
            throw new ForumCreatedBeforeGroupException();
        }

        Forum forum = new Forum(forumName, user.getUsername(), creationDate, group);

        for (String tag : tags) {
            forum.addTag(tag);
        }

        communityService.createForum(user, group, forum);
        group.addForum(forum);
    }
}
