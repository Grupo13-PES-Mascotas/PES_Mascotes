package org.pesmypetcare.mypetcare.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.community.TrLikePost;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyLikedException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrLikePost {
    private TrLikePost trLikePost;
    private User user;
    private Post post;

    @Before
    public void setUp() {
        trLikePost = new TrLikePost(new StubCommunityService());
        user = new User("Manolo Lama", "bicholover@gmail.com", "1234");
        Group group = new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15"));
        Forum forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"),
            group);
        post = new Post(user.getUsername(), "I would love to clean the Bicho",
            DateTime.Builder.buildFullString("2020-04-28T12:00:00"), forum);
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotLikeNonExistingPosts() throws PostNotFoundException, PostAlreadyLikedException {
        trLikePost.setUser(user);
        post.setUsername("CR6_Jr");
        trLikePost.setPost(post);
        trLikePost.execute();
    }

    @Test(expected = PostAlreadyLikedException.class)
    public void shouldNotLikeAlreadyLikedPost() throws PostNotFoundException, PostAlreadyLikedException {
        trLikePost.setUser(user);
        trLikePost.setPost(post);
        trLikePost.execute();
    }

    @Test
    public void shouldLikePost() throws PostAlreadyLikedException, PostNotFoundException {
        int postLikes = post.getLikes();
        trLikePost.setUser(user);
        user.setUsername("Tomas Roncero");
        trLikePost.setPost(post);
        trLikePost.execute();
        assertEquals("Likes should have increased by 1", postLikes + 1, post.getLikes());
    }

    @After
    public void restartStubData() {
        StubCommunityService.addStubDefaultData();
    }
}
