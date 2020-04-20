package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.users.User;

/**
 * @author Albert Pinto
 */
public interface InfoGroupCommunication {
    /**
     * Get the current user.
     * @return The current user
     */
    User getUser();

    /**
     * Set the toolbar title.
     * @param title The toolbar title
     */
    void setToolbar(String title);

    /**
     * Add subscription to group.
     * @param group The group to add a subscription to
     */
    void addSubscription(Group group);
}