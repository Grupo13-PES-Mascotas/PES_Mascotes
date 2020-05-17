package org.pesmypetcare.mypetcare.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.controllers.community.TrAddNewForum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumCreatedBeforeGroupException;
import org.pesmypetcare.mypetcare.features.community.forums.UserNotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrAddNewForum {
    private static final String FORUM_NAME = "Hair";
    private User user;
    private Group group;
    private List<String> tags;
    private TrAddNewForum trAddNewForum;

    @Before
    public void setUp() {
        user = new User("John Smith", "johnsmith@gmail.com", "1234");
        group = new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15"));
        user.addSubscribedGroup(group);

        tags = new ArrayList<>();
        tags.add("important");

        trAddNewForum = new TrAddNewForum(new StubCommunityService());
    }

    @Test(expected = UserNotSubscribedException.class)
    public void shouldUserBeSubscribedToGroup() throws UserNotSubscribedException, GroupNotFoundException,
        ForumCreatedBeforeGroupException {
        trAddNewForum.setUser(new User("Arthur Jones", "arthurjones@gmail.com", "1234"));
        trAddNewForum.setGroup(group);
        trAddNewForum.setForumName(FORUM_NAME);
        trAddNewForum.setTags(tags);
        trAddNewForum.setCreationDate(DateTime.Builder.buildFullString("2020-04-16T15:00:00"));
        trAddNewForum.execute();
    }

    @Test(expected = GroupNotFoundException.class)
    public void shouldTheGroupExist() throws UserNotSubscribedException, GroupNotFoundException,
        ForumCreatedBeforeGroupException {
        trAddNewForum.setUser(user);
        trAddNewForum.setGroup(new Group("Penguins", "Arthur Jones", DateTime.Builder.buildDateString("2020-04-22")));
        trAddNewForum.setForumName(FORUM_NAME);
        trAddNewForum.setTags(tags);
        trAddNewForum.setCreationDate(DateTime.Builder.buildFullString("2020-04-16T15:00:00"));
        trAddNewForum.execute();
    }

    @Test(expected = ForumCreatedBeforeGroupException.class)
    public void shouldTheForumBeCreatedAfterTheGroup() throws UserNotSubscribedException, GroupNotFoundException,
        ForumCreatedBeforeGroupException {
        trAddNewForum.setUser(user);
        trAddNewForum.setGroup(group);
        trAddNewForum.setForumName(FORUM_NAME);
        trAddNewForum.setTags(tags);
        trAddNewForum.setCreationDate(DateTime.Builder.buildFullString("2019-04-16T15:00:00"));
        trAddNewForum.execute();
    }

    @Test
    public void shouldCreateNewForum() throws UserNotSubscribedException, ForumCreatedBeforeGroupException,
        GroupNotFoundException {
        trAddNewForum.setUser(user);
        trAddNewForum.setGroup(group);
        trAddNewForum.setForumName(FORUM_NAME);
        trAddNewForum.setTags(tags);
        trAddNewForum.setCreationDate(DateTime.Builder.buildFullString("2020-04-16T15:00:00"));
        trAddNewForum.execute();

        assertEquals("Should create a new forum", 1, group.getForums().size());
    }

    @After
    public void afterTest() {
        StubCommunityService.addStubDefaultData();
    }
}
