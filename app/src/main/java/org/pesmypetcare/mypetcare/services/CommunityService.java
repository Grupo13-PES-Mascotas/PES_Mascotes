package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

public interface CommunityService {

    /**
     * Method responsible for obtaining all the groups.
     * @return A list containing all the groups of the system
     */
    List<Group> getAllGroups();

    /**
     * Method responsible for creating a new group.
     * @param user The owner of the group
     * @param group The group that has to be created
     * @throws GroupAlreadyExistingException Exception thrown if the indicated group name is already registered
     */
    void createGroup(User user, Group group) throws GroupAlreadyExistingException;

    /**
     * Method responsible for deleting a group from the system.
     * @param groupName The name of the group that has to be deleted
     */
    void deleteGroup(String groupName) throws GroupNotFoundException;
}
