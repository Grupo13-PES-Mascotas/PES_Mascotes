package org.pesmypetcare.mypetcare.activities.fragments.community.groups;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

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

    /**
     * Remove the current user from the group.
     * @param group The group to remove the user from
     */
    void removeSubscription(Group group);

    /**
     * Remove the forum from its group.
     * @param forum The forum that has to be removed
     */
    void deleteForum(Forum forum);

    /**
     * Shows a forum.
     * @param postsFragment The fragment to show
     */
    void showForum(PostsFragment postsFragment);

    /**
     * Add a new post to the forum.
     * @param forum The forum were the post will be added
     * @param postImage The post of the image
     */
    void addNewPost(Forum forum, @Nullable String postText, @Nullable Bitmap postImage);

    /**
     * Deletes a post from the given forum.
     * @param forum The forum from where the post has to be deleted
     * @param postCreationDate The creation date of the post that has to be deleted
     */
    void deletePost(Forum forum, DateTime postCreationDate);

    /**
     * Updates the given post with the given text.
     * @param postToUpdate The post to update
     * @param newText The new text of the post
     */
    void updatePost(Post postToUpdate, String newText);

    /**
     * The current user likes the given post.
     * @param postToLike The post that the current user wants to like
     */
    void likePost(Post postToLike);

    /**
     * The current user unlikes the given post.
     * @param post The post that the current user wants to unlike
     */
    void unlikePost(Post post);

    /**
     * Report the post.
     * @param post The post
     * @param reportMessage The report message
     */
    void reportPost(Post post, String reportMessage);

    /**
     * Get the image from the user.
     * @param username The username
     * @return The user image
     */
    Bitmap findImageByUser(String username);

    /**
     * Add the given image to the post.
     * @param post The post where the image has to be added
     * @param image The image that has to be added to the post
     */
    void addPostImage(Post post, Bitmap image);

    /**
     * Delete post image.
     * @param post The post to delete the image
     */
    void deletePostImage(Post post);

    /**
     * Make the zoom image of the group.
     * @param drawable The image to display
     */
    void makeGroupZoomImage(Drawable drawable);

    /**
     * Get the image for the post.
     * @param post The post to get the image from
     * @param messageData The message data of the post
     * @return The image of the post
     */
    byte[] getImageFromPost(Post post, MessageDisplay messageData);

    void refreshActualFragment();
}
