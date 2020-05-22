package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrUpdatePost {
    private CommunityService communityService;
    private User user;
    private Post post;
    private String newText;
    private boolean result;

    public TrUpdatePost(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the user that wants to update the post.
     * @param user The user that wants to update the post
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the post to update.
     * @param post The post to update
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Setter of the new text of the post.
     * @param newText The new text of the post
     */
    public void setNewText(String newText) {
        this.newText = newText;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the update was successful or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws NotPostOwnerException, ForumNotFoundException, PostNotFoundException {
        result = false;
        if (!user.getUsername().equals(post.getUsername())) {
            throw new NotPostOwnerException();
        }
        communityService.updatePost(user, post, newText);
        post.setText(newText);
        result = true;
    }
}
