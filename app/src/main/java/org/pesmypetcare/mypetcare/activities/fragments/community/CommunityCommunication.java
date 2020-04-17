package org.pesmypetcare.mypetcare.activities.fragments.community;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;

public interface CommunityCommunication {

    /**
     * Method responsible for obtaining all the groups.
     * @return A list containing all the groups of the system
     */
    List<Group> getAllGroups();

    /**
     * Method responsible for creating a new group.
     * @param user The owner of the group
     * @param groupName The name of the new group
     * @param creationDate The creationDate of the group
     * @param tags The tags of the new group
     */
    void createGroup(User user, String groupName, DateTime creationDate, List<String> tags);

    /**
     * Method responsible for deleting a group from the system.
     * @param groupName The name of the group that has to be deleted
     */
    void deleteGroup(String groupName);
}
