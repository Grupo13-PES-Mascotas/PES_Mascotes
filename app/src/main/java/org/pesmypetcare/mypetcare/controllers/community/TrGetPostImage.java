package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

/**
 * @author Albert Pinto
 */
public class TrGetPostImage {
    private CommunityService communityService;
    private User user;
    private Post post;
    private MessageDisplay messageData;
    private byte[] result;

    public TrGetPostImage(CommunityService communityService) {
        this.communityService = communityService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setMessageData(MessageDisplay messageData) {
        this.messageData = messageData;
    }

    public void execute() {
        result = communityService.getPostImage(user, post, messageData);
    }

    public byte[] getResult() {
        return result;
    }
}
