package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.communitymanager.datacontainers.MessageReceiveData;
import org.pesmypetcare.httptools.MyPetCareException;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrGetPostImage {
    private User user;
    private Group group;
    private Forum forum;
    private Post post;
    private MessageDisplay messageData;
    private TrGetPostImage trGetPostImage;

    @Before
    public void setUp() throws IOException, MyPetCareException {
        user = new User("John Doe", "johndoe@gmail.com", "1234");
        group = new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15"));
        forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"), group);
        post = new Post("John Doe", "I think that the huskies have to be kept cleaned. What do you think?",
            DateTime.Builder.buildFullString("2020-04-21T20:55:10"), forum);

        messageData = new MessageDisplay(new MessageReceiveData("John Doe",
            "I think that the huskies have to be kept cleaned. What do you think?"));

        forum.addPost(post);
        trGetPostImage = new TrGetPostImage(new StubCommunityService());
    }

    @Test
    public void shouldGetImageFromPost() {
        trGetPostImage.setUser(user);
        trGetPostImage.setPost(post);
        trGetPostImage.setMessageData(messageData);
        trGetPostImage.execute();

        assertEquals("Should get the image from the post", 0x00, trGetPostImage.getResult()[0]);
    }
}
