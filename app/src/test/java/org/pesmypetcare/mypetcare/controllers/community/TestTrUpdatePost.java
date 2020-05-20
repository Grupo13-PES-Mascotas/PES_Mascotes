package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.controllers.community.TrAddNewPost;
import org.pesmypetcare.mypetcare.controllers.community.TrUpdatePost;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.NotPostOwnerException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostCreatedBeforeForumException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

import static org.junit.Assert.assertTrue;

/**
 * @author Xavier Campos
 */
public class TestTrUpdatePost {
    private TrUpdatePost trUpdatePost;
    private TrAddNewPost trAddNewPost;
    private User user;
    private Forum forum;
    private Post post;
    private String newText;

    @Before
    public void setUp() {
        this.trUpdatePost = new TrUpdatePost(new StubCommunityService());
        this.trAddNewPost = new TrAddNewPost(new StubCommunityService());
        user = new User("John Doe", "johndoe@gmail.com", "1234");
        Group group = new Group("Dinosaur", "Gradle",
            DateTime.Builder.buildDateString("2019-11-23"));
        forum = new Forum("Sickling", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:10:00"),
            group);
        post = new Post("John Doe", "Gradle is a Dinosaur.",
            DateTime.Builder.buildFullString("2040-04-21T20:55:10"), forum);
        forum.addPost(post);
        newText = "Gradle is still a Dinosaur";
    }

    @Test(expected = ForumNotFoundException.class)
    public void shouldNotUpdatePostIfForumNotFound() throws ForumNotFoundException, NotPostOwnerException,
        PostNotFoundException {
        forum.setName("Potato");
        trUpdatePost.setUser(user);
        trUpdatePost.setPost(post);
        trUpdatePost.setNewText(newText);
        trUpdatePost.execute();
    }

    @Test(expected = NotPostOwnerException.class)
    public void shouldNotUpdatePostIfNotPostOwner() throws ForumNotFoundException, NotPostOwnerException,
        PostNotFoundException {
        user.setUsername("M.Rajoy");
        trUpdatePost.setUser(user);
        trUpdatePost.setPost(post);
        trUpdatePost.setNewText(newText);
        trUpdatePost.execute();
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotUpdatePostIfPostNotFound() throws ForumNotFoundException, NotPostOwnerException,
        PostNotFoundException {
        post.setCreationDate(DateTime.Builder.buildFullString("2033-04-21T20:55:10"));
        trUpdatePost.setUser(user);
        trUpdatePost.setPost(post);
        trUpdatePost.setNewText(newText);
        trUpdatePost.execute();
    }

    @Test
    public void shouldUpdatePost() throws PostAlreadyExistingException, ForumNotFoundException,
        PostCreatedBeforeForumException, NotPostOwnerException, PostNotFoundException {
        trAddNewPost.setUser(user);
        trAddNewPost.setForum(forum);
        trAddNewPost.setPostCreationDate(post.getCreationDate());
        trAddNewPost.setPostText(post.getText());
        trAddNewPost.execute();
        trUpdatePost.setUser(user);
        trUpdatePost.setPost(post);
        trUpdatePost.setNewText(newText);
        trUpdatePost.execute();
        assertTrue("The result of the transaction should be true", trUpdatePost.isResult());
    }
}
