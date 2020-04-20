package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.users.User;

/**
 * @author Albert Pinto
 */
public interface InfoGroupCommunication {

    User getUser();

    void setToolbar(String name);

    void addSubscription(Group group);
}
