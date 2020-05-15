package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

/**
 * @author Xavier Campos
 */
public class TestTrDeletePostImage {
    private TrDeletePostImage trDeletePostImage;
    private User user;
    private Post post;

    @Before
    public void setUp() {
        trDeletePostImage = new TrDeletePostImage(new StubCommunityService());
        user = new User("Manolo Lama", "lamacope@gmail.com", "BICHO");
        Group group = new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15"));
        Forum forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"),
            group);
        post = new Post("Manolo Lama", "I would love to clean the Bicho",
            DateTime.Builder.buildFullString("2020-04-28T12:00:00"), forum);
    }

    @Test(expected = NotPostOwnerException.class)
    public void shouldNotDeleteImageIfNotPostOwner() throws NotPostOwnerException, PostNotFoundException {
        user.setUsername("Tomas Roncero");
        trDeletePostImage.setUser(user);
        trDeletePostImage.setPost(post);
        trDeletePostImage.execute();
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotDeleteImageFromNonExistingPost() throws NotPostOwnerException, PostNotFoundException {
        trDeletePostImage.setUser(user);
        post.setCreationDate(DateTime.Builder.buildFullString("3020-04-21T20:50:10"));
        trDeletePostImage.setPost(post);
        trDeletePostImage.execute();
    }

    @Test
    public void shouldDeleteImageFromPost() throws NotPostOwnerException, PostNotFoundException {
        trDeletePostImage.setUser(user);
        trDeletePostImage.setPost(post);
        trDeletePostImage.execute();
    }
    @After
    public void restartStubData() {
        StubCommunityService.addStubDefaultData();
    }
}
