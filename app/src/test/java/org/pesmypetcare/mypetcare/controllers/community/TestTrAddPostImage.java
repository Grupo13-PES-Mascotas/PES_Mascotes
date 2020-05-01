package org.pesmypetcare.mypetcare.controllers.community;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class TestTrAddPostImage {
    private TrAddPostImage trAddPostImage;
    private Bitmap image;
    private User user;
    private Post post;

    @Before
    public void setUp() {
        trAddPostImage = new TrAddPostImage(new StubCommunityService());
        image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.test);
        user = new User("Manolo Lama", "lamacope@gmail.com", "BICHO");
        Group group = new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15"));
        Forum forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"),
            group);
        post = new Post("Manolo Lama", "I would love to clean the Bicho",
            DateTime.Builder.buildFullString("2020-04-28T12:00:00"), forum);
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotAddImageIfNonExistingPost() throws NotPostOwnerException, PostNotFoundException {
        trAddPostImage.setUser(user);
        post.setCreationDate(DateTime.Builder.buildFullString("3020-04-28T12:00:00"));
        trAddPostImage.setPost(post);
        trAddPostImage.setImage(image);
        trAddPostImage.execute();
    }

    @Test(expected = NotPostOwnerException.class)
    public void shouldNotAddImageIfNotPostOwner() throws NotPostOwnerException, PostNotFoundException {
        trAddPostImage.setUser(user);
        user.setUsername("Tomas Roncero");
        trAddPostImage.setPost(post);
        trAddPostImage.setImage(image);
        trAddPostImage.execute();
    }

    @Test
    public void shouldAddPostImage() throws NotPostOwnerException, PostNotFoundException {
        trAddPostImage.setUser(user);
        trAddPostImage.setPost(post);
        trAddPostImage.setImage(image);
        trAddPostImage.execute();
    }
}
