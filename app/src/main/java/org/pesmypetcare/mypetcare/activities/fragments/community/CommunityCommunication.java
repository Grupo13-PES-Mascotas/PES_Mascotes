package org.pesmypetcare.mypetcare.activities.fragments.community;

import org.pesmypetcare.mypetcare.activities.fragments.community.groups.InfoGroupFragment;
import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.SortedSet;

public interface CommunityCommunication {

    /**
     * Method responsible for obtaining all the groups.
     * @return A list containing all the groups of the system
     */
    SortedSet<Group> getAllGroups();

    /**
     * Method responsible for deleting a group from the system.
     * @param groupName The name of the group that has to be deleted
     */
    void deleteGroup(String groupName);

    /**
     * Show the group fragment.
     * @param infoGroupFragment The group fragment to show
     */
    void showGroupFragment(InfoGroupFragment infoGroupFragment);

    /**
     * Get the current user.
     * @return The current user
     */
    User getUser();
}
