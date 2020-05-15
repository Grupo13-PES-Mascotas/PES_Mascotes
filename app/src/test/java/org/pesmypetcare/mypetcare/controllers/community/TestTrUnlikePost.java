package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.NotLikedPostException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrUnlikePost {
    private User user;
    private Post post;
    private TrUnlikePost trUnlikePost;

    @Before
    public void setUp() {
        user = new User("John Smith", "johndoe@gmail.com", "1234");
        Group group = new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15"));
        Forum forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"), group);
        post = new Post("John Doe", "I think that the huskies have to be kept cleaned. What do you think?",
            DateTime.Builder.buildFullString("2020-04-21T20:55:10"), forum);
        forum.addPost(post);
        trUnlikePost = new TrUnlikePost(new StubCommunityService());
    }

    @Test(expected = NotLikedPostException.class)
    public void shouldNotUnlikeNonLikedPost() throws NotLikedPostException {
        trUnlikePost.setUser(user);
        trUnlikePost.setPost(post);
        trUnlikePost.execute();
    }

    @Test
    public void shouldUnlikePost() throws NotLikedPostException {
        post.addLikerUsername(user.getUsername());
        trUnlikePost.setUser(user);
        trUnlikePost.setPost(post);
        trUnlikePost.execute();

        assertEquals("Should unlike post", "[John Doe]", post.getLikerUsername().toString());
    }

    @Test
    public void shouldDecrementTheNumberOfLikes() throws NotLikedPostException {
        post.addLikerUsername(user.getUsername());
        trUnlikePost.setUser(user);
        trUnlikePost.setPost(post);
        trUnlikePost.execute();

        assertEquals("Should decrement the number of likes", 1, post.getLikes());
    }

    @After
    public void restoreStubData() {
        StubCommunityService.addStubDefaultData();
    }
}
