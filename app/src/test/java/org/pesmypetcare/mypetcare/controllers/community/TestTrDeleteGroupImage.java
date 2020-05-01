package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.NotGroupOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

/**
 * @author Xavier Campos
 */
public class TestTrDeleteGroupImage {
    private TrDeleteGroupImage trDeleteGroupImage;
    private User user;
    private Group group;

    @Before
    public void setUp() {
        trDeleteGroupImage = new TrDeleteGroupImage(new StubCommunityService());
        user = new User("John Doe", "lamacope@gmail.com", "BICHO");
        group = new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15"));
    }

    @Test(expected = GroupNotFoundException.class)
    public void shouldNotDeleteImageIfNonExistingGroup() throws NotGroupOwnerException, GroupNotFoundException {
        trDeleteGroupImage.setUser(user);
        group.setName("El Bicho");
        trDeleteGroupImage.setGroup(group);
        trDeleteGroupImage.execute();
    }

    @Test(expected = NotGroupOwnerException.class)
    public void shouldNotDeleteImageIfNotGroupOwner() throws NotGroupOwnerException, GroupNotFoundException {
        trDeleteGroupImage.setUser(user);
        user.setUsername("Tomas Roncero");
        trDeleteGroupImage.setGroup(group);
        trDeleteGroupImage.execute();
    }

    @Test
    public void shouldDeleteGroupImage() throws NotGroupOwnerException, GroupNotFoundException {
        trDeleteGroupImage.setUser(user);
        trDeleteGroupImage.setGroup(group);
        trDeleteGroupImage.execute();
    }

    @After
    public void restartStubData() {
        StubCommunityService.addStubDefaultData();
    }
}
