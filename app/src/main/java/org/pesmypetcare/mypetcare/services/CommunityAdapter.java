package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

public class CommunityAdapter implements CommunityService {

    @Override
    public List<Group> getAllGroups() {
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
}
