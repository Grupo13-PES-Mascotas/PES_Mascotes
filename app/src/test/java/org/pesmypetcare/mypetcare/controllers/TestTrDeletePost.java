package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostCreatedBeforeForumException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class TestTrDeletePost {
    private TrDeletePost trDeletePost;
    private TrAddNewPost trAddNewPost;
    private User user;
    private Forum forum;
    private Post post;

    @Before
    public void setUp() {
        trDeletePost = new TrDeletePost(new StubCommunityService());
        trAddNewPost = new TrAddNewPost(new StubCommunityService());
        user = new User("John Doe", "johndoe@gmail.com", "1234");
        Group group = new Group("Dinosaur", "Gradle",
            DateTime.Builder.buildDateString("2019-11-23"));
        forum = new Forum("Sickling", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:10:00"),
            group);
        post = new Post(user.getUsername(), "Ok bro", DateTime.Builder.buildFullString("2033-04-22T10:10:00"),
            forum);
    }

    @Test(expected = ForumNotFoundException.class)
    public void shouldNotDeleteIfForumDoesNotExist() throws ForumNotFoundException, PostNotFoundException {
        forum.setName("Rock eater");
        trDeletePost.setUser(user);
        trDeletePost.setForum(forum);
        trDeletePost.setPostCreationDate(post.getCreationDate());
        trDeletePost.execute();
    }

    @Test(expected = PostNotFoundException.class)
    public void shouldNotDeleteIfPostDoesNotExist() throws ForumNotFoundException, PostNotFoundException {
        trDeletePost.setUser(user);
        trDeletePost.setForum(forum);
        trDeletePost.setPostCreationDate(post.getCreationDate());
        trDeletePost.execute();
    }

    @Test
    public void shouldDeletePost() throws PostAlreadyExistingException, ForumNotFoundException,
        PostCreatedBeforeForumException, PostNotFoundException {
        trAddNewPost.setUser(user);
        trAddNewPost.setForum(forum);
        trAddNewPost.setPostText(post.getText());
        trAddNewPost.setPostCreationDate(post.getCreationDate());
        trAddNewPost.execute();

        trDeletePost.setUser(user);
        trDeletePost.setForum(forum);
        trDeletePost.setPostCreationDate(post.getCreationDate());
        trDeletePost.execute();
    }
}
