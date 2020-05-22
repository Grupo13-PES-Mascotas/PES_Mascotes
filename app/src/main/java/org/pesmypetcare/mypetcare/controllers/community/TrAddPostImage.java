package org.pesmypetcare.mypetcare.controllers.community;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrAddPostImage {
    private CommunityService communityService;
    private Post post;
    private User user;
    private Bitmap image;
    private boolean result;

    public TrAddPostImage(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the post to which we want to add the image.
     * @param post The post to which we want to add the image
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Setter of the user that wants to add the image to the post.
     * @param user The user that wants to add the image to the post
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the image that has to be added to the post.
     * @param image The image that has to be added to the post
     */
    public void setImage(Bitmap image) {
        this.image = image;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the image was added successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws NotPostOwnerException, PostNotFoundException {
        result = false;
        if (!user.getUsername().equals(post.getUsername())) {
            throw new NotPostOwnerException();
        }
        communityService.addPostImage(user, post, image);
        post.setPostImage(image);
        result = true;
    }
}
