package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumCreatedBeforeGroupException;
import org.pesmypetcare.mypetcare.features.community.forums.UserNotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void execute() throws UserNotSubscribedException, GroupNotExistingException,
        ForumCreatedBeforeGroupException {
        if (!communityService.isGroupExisting(group)) {
            throw new GroupNotExistingException();
        } else if (!group.isUserSubscriber(user)) {
            throw new UserNotSubscribedException();
        } else if (group.getCreationDate().compareTo(creationDate) > 0) {
            throw new ForumCreatedBeforeGroupException();
        }

        Forum forum = new Forum(forumName, user.getUsername(), creationDate, group);
        communityService.createForum(user, group, forum);
        group.addForum(forum);
    }
}
