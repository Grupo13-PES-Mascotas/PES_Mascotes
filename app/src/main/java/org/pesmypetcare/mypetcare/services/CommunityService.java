package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.communitymanager.datacontainers.MessageData;
import org.pesmypetcare.httptools.MyPetCareException;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyLikedException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

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

    /**
     * Add a forum to the group.
     * @param user The author of the forum
     * @param group The group were the forum will be added
     * @param forum The forum that has to be added to the group
     */
    void createForum(User user, Group group, Forum forum);

    /**
     * Remove a forum from the group.
     * @param user The creator of the forum
     * @param group The group where the forum is posted
     * @param forum The forum to delete
     */
    void deleteForum(User user, Group group, Forum forum) throws ForumNotFoundException, NotForumOwnerException;

    /**
     * Add a post to a forum.
     * @param user The author of the post
     * @param forum The forum were the post will be added
     * @param post The post to add
     */
    void createPost(User user, Forum forum, Post post) throws ForumNotFoundException, PostAlreadyExistingException;

    /**
     * Deletes a post from the forum.
     * @param user The user that wants to delete the post
     * @param forum The forum from where the post has to be deleted
     * @param postCreationDate The creation date of the post that has to be deleted
     */
    void deletePost(User user, Forum forum, DateTime postCreationDate) throws ForumNotFoundException,
        PostNotFoundException;

    /**
     * Updates the text from the given post.
     * @param user The user that wants to edit the post
     * @param post The post that has to be edited
     * @param newText The text that has to be set
     */
    void updatePost(User user, Post post, String newText) throws ForumNotFoundException, PostNotFoundException;

    /**
     * The indicated user likes the indicated post.
     * @param user The user that wants to give a like to the post
     * @param post The post to give a like
     */
    void likePost(User user, Post post)
        throws PostNotFoundException, PostAlreadyLikedException;

    /**
     * Unlike the post.
     * @param user The user that wants to unlike the post
     * @param post The post that has ti have the user removes from liker username
     */
    void unlikePost(User user, Post post);

    /**
     * The indicated user reports the given post.
     * @param user The user who wants to report a post
     * @param post The post that has been reported
     * @param reportMessage The message of the report
     */
    void reportPost(User user, Post post, String reportMessage);

    /**
     * The given user add the given image to the indicated post.
     * @param user The user that wants to add the image to the post
     * @param post The post where the image has to be added
     * @param image The image that has to be added to the post
     */
    void addPostImage(User user, Post post, Bitmap image) throws PostNotFoundException;

    /**
     * The given user wants to delete the image from the indicated post.
     * @param user The user that wants to delete the image
     * @param post The post from where the image has to be deleted
     */
    void deletePostImage(User user, Post post) throws PostNotFoundException;

    /**
     * The given user adds the given image to the indicated group.
     * @param user The user that wants to add the image to the group
     * @param group The group where the image has to be added
     * @param image The image that has to be added
     */
    void addGroupImage(User user, Group group, Bitmap image) throws GroupNotFoundException, MyPetCareException;

    /**
     * The given user wants to delete the image from the indicated group.
     * @param user The user that wants to delete the image from the group
     * @param group The group from where the image has to be deleted
     */
    void deleteGroupImage(User user, Group group) throws GroupNotFoundException;

    /**
     * Get the image of the post.
     * @param user The user that makes the petition
     * @param post The post to get the image from
     * @param messageData The data received from the server
     * @return The image of the post in form of a byte array
     */
    byte[] getPostImage(User user, Post post, MessageData messageData);
}
