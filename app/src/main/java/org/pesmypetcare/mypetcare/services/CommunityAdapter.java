package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;

public class CommunityAdapter implements CommunityService {

    @Override
    public List<Group> getAllGroups() {
        return null;
    }

    @Override
    public void createGroup(String groupName, String ownerUsername, DateTime creationDate) {

    }
}
