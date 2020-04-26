package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostCreatedBeforeForumException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class TrAddNewPost {
    private CommunityService communityService;
    private User user;
    private Forum forum;
    private DateTime postCreationDate;
    private String postText;
    private boolean result;

    public TrAddNewPost(CommunityService communityService) {
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
     * Setter of the text of the post.
     * @param postText The text of the postt
     */
    public void setPostText(String postText) {
        this.postText = postText;
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
     * @throws PostAlreadyExistingException The post already exists
     * @throws ForumNotFoundException The forum is not found
     * @throws PostCreatedBeforeForumException The post is created before the forum
     */
    public void execute() throws PostAlreadyExistingException, ForumNotFoundException, PostCreatedBeforeForumException {
        result = false;
        if (postCreationDate.compareTo(forum.getCreationDate()) < 0) {
            throw new PostCreatedBeforeForumException();
        }
        Post post = new Post(user.getUsername(), postText, postCreationDate, forum);
        communityService.createPost(user, forum, post);
        result = true;
    }
}
