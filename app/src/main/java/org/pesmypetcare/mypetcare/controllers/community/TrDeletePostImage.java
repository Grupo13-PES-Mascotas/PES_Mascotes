package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrDeletePostImage {
    private CommunityService communityService;
    private User user;
    private Post post;
    private boolean result;

    public TrDeletePostImage(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the user that wants to delete the image from the post.
     * @param user The user that wants to delete the image from the post.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the post from which we want to delete the image.
     * @param post The post from which we want to delete the image.
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the delete was successful or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction
     */
    public void execute() throws NotPostOwnerException, PostNotFoundException {
        result = false;
        if (!user.getUsername().equals(post.getUsername())) {
            throw new NotPostOwnerException();
        }
        communityService.deletePostImage(user, post);
        post.setPostImage(null);
        result = true;
    }
}
