package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

/**
 * @author Albert Pinto
 */
public class TestTrAddNewForum {
    private User user;
    private Group group;

    @Before
    public void setUp() {
        user = new User("John Smith", "johnsmith@gmail.com", "1234");
        group = new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15"));

        user.addSubscribedGroup(group);
    }

    @Test
    public void shouldUserBeSubscribedToGroup() {

    }
}
