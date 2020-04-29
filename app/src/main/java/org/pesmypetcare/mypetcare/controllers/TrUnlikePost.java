package org.pesmypetcare.mypetcare.controllers;

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

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void execute() throws NotLikedPostException {
        if (!post.isLikedByUser(user.getUsername())) {
            throw new NotLikedPostException();
        }

        communityService.unlikePost(user, post);
        post.removeLikerUsername(user.getUsername());
    }
}
