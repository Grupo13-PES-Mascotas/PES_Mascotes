package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.SortedSet;

public interface CommunityService {

    /**
     * Method responsible for obtaining all the groups.
     * @return A list containing all the groups of the system
     */
    SortedSet<Group> getAllGroups();

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

    /**
     * Check whether the group exists.
     * @param group Teh group to check
     * @return True if the group exists
     */
    boolean isGroupExisting(Group group);

    /**
     * Add a subscriber to the group.
     * @param user The subscriber to add
     * @param group The group
     */
    void addSubscriber(User user, Group group);

    /**
     * Remove a subscriber from the group.
     * @param user The subscriber to add
     * @param group The group
     */
    void deleteSubscriber(User user, Group group);

    void createForum(User user, Group group, Forum forum);

    /**
     * Remove a forum from the group.
     * @param user The creator of the forum
     * @param group The group where the forum is posted
     * @param forum The forum to delete
     */
    void deleteForum(User user, Group group, Forum forum) throws ForumNotFoundException, NotForumOwnerException;
}
