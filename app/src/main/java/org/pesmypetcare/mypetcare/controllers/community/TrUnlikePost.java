package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.posts.NotLikedPostException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;

/**
 * @author Albert Pinto
 */
public class TrUnlikePost {
    private CommunityService communityService;
    private User user;
    private Post post;

    public TrUnlikePost(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Set the user.
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the post.
     * @param post The post to set
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Execute the transaction.
     * @throws NotLikedPostException The post is not liked
     */
    public void execute() throws NotLikedPostException {
        if (!post.isLikedByUser(user.getUsername())) {
            throw new NotLikedPostException();
        }

        communityService.unlikePost(user, post);
        post.removeLikerUsername(user.getUsername());
    }
}
