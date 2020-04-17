package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;

public interface CommunityService {

    List<Group> getAllGroups();

    void createGroup(String groupName, String ownerUsername, DateTime creationDate) throws GroupAlreadyExistingException;
}
