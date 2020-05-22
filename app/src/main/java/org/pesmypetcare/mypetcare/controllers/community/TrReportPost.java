package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostReportedByAuthorException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

/**
 * @author Xavier Campos
 */
public class TrReportPost {
    private CommunityService communityService;
    private User user;
    private Post post;
    private String reportMessage;
    private boolean result;

    public TrReportPost(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the user who wants to report the post.
     * @param user The user who wants to report the post
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter of the post to report.
     * @param post The post to report
     */
    public void setPost(Post post) {
        this.post = post;
    }

    /**
     * Setter of the report message.
     * @param reportMessage The report message
     */
    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the transaction was successful or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     */
    public void execute() throws PostReportedByAuthorException {
        result = false;
        if (user.getUsername().equals(post.getUsername())) {
            throw new PostReportedByAuthorException();
        }
        communityService.reportPost(user, post, reportMessage);
        post.reportPost();
        result = true;
    }
}
