package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrUnbanPost {
    private CommunityService communityService;
    private User user;
    private Post post;
    private boolean result;

    public TrUnbanPost(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the user that wants to unban the post.
     * @param user The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the post that has to be unbanned.
     * @param post The post
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Returns the result of the transaction.
     * @return True if the transaction was successful or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     * @throws NotForumOwnerException The given user is not the owner of the forum were the post is made
     */
    public void execute() throws NotForumOwnerException {
        if (post.getForum().getOwnerUsername().equals(user.getUsername())) {
            this.communityService.unbanPost(user, post);
        } else {
            throw new NotForumOwnerException();
        }
    }
}
