package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrGetGroupImage {
    private User user;
    private Group group;
    private TrGetGroupImage trGetGroupImage;

    @Before
    public void setUp() {
        user = new User("John Doe", "johndoe@gmail.com", "1234");
        group = new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15"));
        trGetGroupImage = new TrGetGroupImage(new StubCommunityService());
    }

    @Test
    public void shouldGetGroupImage() {
        trGetGroupImage.setUser(user);
        trGetGroupImage.setGroup(group);
        trGetGroupImage.execute();

        assertEquals("Should get group image", "[0]", Arrays.toString(trGetGroupImage.getResult()));
    }
}
