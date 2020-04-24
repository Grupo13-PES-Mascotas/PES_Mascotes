package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class TestTrDeleteForum {
    private static final String JOHN_DOE = "John Doe";
    private TrDeleteForum trDeleteForum;
    private User user;
    private Group group;
    private Forum newForum;
    private Forum existingForum1;
    private Forum existingForum2;

    @Before
    public void setUp() {
        trDeleteForum = new TrDeleteForum(new StubCommunityService());
        user = new User("John Smith", "johnsmith@gmail.com", "1234");
        group = new Group("Dinosaur", "Gradle", DateTime.Builder.buildDateString("2019-11-23"));
        newForum = new Forum("El Foro", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:00:00"),
            group);
        existingForum1 = new Forum("Washing", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:00:00"),
            group);
        existingForum2 = new Forum("Sickling", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:10:00"),
            group);
    }

    @Test(expected = ForumNotFoundException.class)
    public void shouldLaunchExceptionIfForumNotFound() throws ForumNotFoundException, NotForumOwnerException {
        trDeleteForum.setUser(user);
        trDeleteForum.setGroup(group);
        trDeleteForum.setForum(newForum);
        trDeleteForum.execute();
    }

    @Test(expected = NotForumOwnerException.class)
    public void shouldLaunchExceptionIfFNotOwnerOfTheForum() throws ForumNotFoundException, NotForumOwnerException {
        trDeleteForum.setUser(user);
        trDeleteForum.setGroup(group);
        trDeleteForum.setForum(existingForum1);
        trDeleteForum.execute();
    }

    @Test
    public void shouldDeleteForum() throws ForumNotFoundException, NotForumOwnerException {
        trDeleteForum.setUser(new User(JOHN_DOE, "johndoe@gmail.com", "PASS"));
        trDeleteForum.setGroup(group);
        trDeleteForum.setForum(existingForum2);
        trDeleteForum.execute();
    }
}
