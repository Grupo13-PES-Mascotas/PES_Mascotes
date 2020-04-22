package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.SortedSet;

public class CommunityAdapter implements CommunityService {

    @Override
    public SortedSet<Group> getAllGroups() {
        return null;
    }

    @Override
    public void createGroup(User user, Group group) {
        // Not implemented yet
    }

    @Override
    public void deleteGroup(String groupName) throws GroupNotFoundException {
        // Not implemented yet
    }

    @Override
    public boolean isGroupExisting(Group group) {
        return false;
    }

    @Override
    public void addSubscriber(User user, Group group) {
        // Not implemented yet
    }

    @Override
    public void deleteSubscriber(User user, Group group) {
        //Not implemented yet
    }

    @Override
    public void createForum(User user, Group group, Forum forum) {
        // Not implemented yet
    }
}
