package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostReportedByAuthorException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class TestTrReportPost {
    private TrReportPost trReportPost;
    private User user;
    private Post post;

    @Before
    public void setUp() {
        trReportPost = new TrReportPost(new StubCommunityService());
        user = new User("Manolo Lama", "bicholover@gmail.com", "1234");
        Group group = new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15"));
        Forum forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"),
            group);
        post = new Post(user.getUsername(), "I would love to clean the Bicho",
            DateTime.Builder.buildFullString("2020-04-28T12:00:00"), forum);
    }

    @Test(expected = PostReportedByAuthorException.class)
    public void shouldNotReportPostIfUserIsAuthor() throws PostReportedByAuthorException {
        trReportPost.setUser(user);
        trReportPost.setPost(post);
        trReportPost.setReportMessage("Patata");
        trReportPost.execute();
    }

    @Test
    public void shouldReportPost() throws PostReportedByAuthorException {
        user.setUsername("Albert");
        trReportPost.setUser(user);
        trReportPost.setPost(post);
        trReportPost.setReportMessage("Patata");
        trReportPost.execute();
    }

    @After
    public void restartStubData() {
        StubCommunityService.addStubDefaultData();
    }
}
