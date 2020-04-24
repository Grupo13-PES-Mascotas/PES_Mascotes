package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class TrDeletePost {
    private CommunityService communityService;
    private User user;
    private Forum forum;
    private DateTime postCreationDate;
    private boolean result;

    public TrDeletePost(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the author of the post.
     * @param user The author of the post
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the forum where the post has to be posted.
     * @param forum The forum where the post has to be posted
     */
    public void setForum(Forum forum) {
        this.forum = forum;
    }

    /**
     * Setter of the creation date of the post.
     * @param postCreationDate The creation date of the post
     */
    public void setPostCreationDate(DateTime postCreationDate) {
        this.postCreationDate = postCreationDate;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the post was added successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Execute the transaction.
     * @throws ForumNotFoundException The forum is not found
     * @throws PostNotFoundException The post is not found
     */
    public void execute() throws ForumNotFoundException, PostNotFoundException {
        result = false;
        communityService.deletePost(user, forum, postCreationDate);
        forum.removePost(user, postCreationDate);
        result = true;
    }
}
