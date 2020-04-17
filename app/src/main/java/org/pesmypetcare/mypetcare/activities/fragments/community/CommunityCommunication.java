package org.pesmypetcare.mypetcare.activities.fragments.community;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;

public interface CommunityCommunication {

    List<Group> getAllGroups();

    void createGroup(String groupName, String ownerUsername, DateTime creationDate);
}
