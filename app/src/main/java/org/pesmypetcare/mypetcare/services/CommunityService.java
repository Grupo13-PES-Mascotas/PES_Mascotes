package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.GroupNotFoundException;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;

public interface CommunityService {

    /**
     * Method responsible for obtaining all the groups.
     * @return A list containing all the groups of the system
     */
    List<Group> getAllGroups();

    /**
     * Method responsible for creating a new group.
     * @param groupName The name of the new group
     * @param ownerUsername The username of the owner of the new group
     * @param creationDate The creationDate of the group
     * @throws GroupAlreadyExistingException Exception thrown if the indicated group name is already registered
     */
    void createGroup(String groupName, String ownerUsername, DateTime creationDate) throws GroupAlreadyExistingException;

    /**
     * Method responsible for deleting a group from the system.
     * @param groupName The name of the group that has to be deleted
     */
    void deleteGroup(String groupName) throws GroupNotFoundException;
}
