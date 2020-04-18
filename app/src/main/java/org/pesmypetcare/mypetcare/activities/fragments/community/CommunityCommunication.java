package org.pesmypetcare.mypetcare.activities.fragments.community;

import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.List;

public interface CommunityCommunication {

    /**
     * Method responsible for obtaining all the groups.
     * @return A list containing all the groups of the system
     */
    List<Group> getAllGroups();

    /**
     * Method responsible for deleting a group from the system.
     * @param groupName The name of the group that has to be deleted
     */
    void deleteGroup(String groupName);

    void showGroupFragment(InfoGroupFragment infoGroupFragment);
}
