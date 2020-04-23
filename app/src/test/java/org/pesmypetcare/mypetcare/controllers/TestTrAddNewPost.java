package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostCreatedBeforeForumException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import static org.junit.Assert.assertTrue;

/**
 * @author Xavier Campos
 */
public class TestTrAddNewPost {
    private User user;
    private Group group;
    private Forum forum;
    private Post post;
    private TrAddNewPost trAddNewPost;

    @Before
    public void setUp() {
        user = new User("John Doe", "johndoe@gmail.com", "1234");
        group = new Group("Dinosaur", "Gradle",
            DateTime.Builder.buildDateString("2019-11-23"));
        forum = new Forum("Sickling", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:10:00"),
            group);
        post = new Post(user.getUsername(), "Ok bro", DateTime.Builder.buildFullString("2033-04-22T10:10:00"),
            forum);
        trAddNewPost = new TrAddNewPost(new StubCommunityService());
    }

    @Test(expected = ForumNotFoundException.class)
    public void shouldNotAddIfForumDoesNotExist() throws PostAlreadyExistingException, ForumNotFoundException, PostCreatedBeforeForumException {
        forum.setName("Pepe");
        trAddNewPost.setUser(user);
        trAddNewPost.setPostCreationDate(post.getCreationDate());
        trAddNewPost.setPostText(post.getText());
        trAddNewPost.setForum(forum);
        trAddNewPost.execute();
    }

    @Test(expected = PostCreatedBeforeForumException.class)
    public void shouldNotAddIfPostCreatedBeforeForum() throws PostAlreadyExistingException, ForumNotFoundException,
        PostCreatedBeforeForumException {
        post.setCreationDate(DateTime.Builder.buildFullString("1033-04-22T10:10:00"));
        trAddNewPost.setUser(user);
        trAddNewPost.setPostCreationDate(post.getCreationDate());
        trAddNewPost.setPostText(post.getText());
        trAddNewPost.setForum(forum);
        trAddNewPost.execute();
    }

    @Test(expected = PostAlreadyExistingException.class)
    public void shouldNotAddIfPostAlreadyExists() throws PostAlreadyExistingException, ForumNotFoundException,
        PostCreatedBeforeForumException {
        trAddNewPost.setUser(user);
        trAddNewPost.setPostCreationDate(post.getCreationDate());
        trAddNewPost.setPostText(post.getText());
        trAddNewPost.setForum(forum);
        trAddNewPost.execute();
        trAddNewPost.execute();
    }

    @Test
    public void shouldAddPost() throws PostAlreadyExistingException, ForumNotFoundException,
        PostCreatedBeforeForumException {
        trAddNewPost.setUser(user);
        trAddNewPost.setPostCreationDate(post.getCreationDate());
        trAddNewPost.setPostText(post.getText());
        trAddNewPost.setForum(forum);
        trAddNewPost.execute();
        assertTrue("The result of the transaction should be true", trAddNewPost.isResult());
    }
}
