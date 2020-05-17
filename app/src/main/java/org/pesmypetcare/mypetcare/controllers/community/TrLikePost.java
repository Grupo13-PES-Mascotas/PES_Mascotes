package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyLikedException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrLikePost {
    private CommunityService communityService;
    private User user;
    private Post post;
    private boolean result;

    public TrLikePost(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the user who wants to like the post.
     * @param user The user that wants to like the post
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the post to like.
     * @param post The post to like
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the post was liked successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws PostNotFoundException, PostAlreadyLikedException {
        result = false;
        Forum forum = post.getForum();
        Group group = forum.getGroup();
        communityService.likePost(user, post);
        post.addLikerUsername(user.getUsername());
        result = true;
    }
}
