package org.pesmypetcare.mypetcare.controllers.community;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.NotGroupOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

/**
 * @author Xavier Campos
 */
public class TestTrAddGroupImage {
    private TrAddGroupImage trAddGroupImage;
    private Bitmap image;
    private User user;
    private Group group;

    @Before
    public void setUp() {
        trAddGroupImage = new TrAddGroupImage(new StubCommunityService());
        image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.test);
        user = new User("John Doe", "lamacope@gmail.com", "BICHO");
        group = new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15"));
    }

    @Test(expected = GroupNotFoundException.class)
    public void shouldNotAddImageIfNonExistingGroup() throws NotGroupOwnerException, GroupNotFoundException,
        MyPetCareException {
        trAddGroupImage.setUser(user);
        group.setName("El Bicho");
        trAddGroupImage.setGroup(group);
        trAddGroupImage.setImage(image);
        trAddGroupImage.execute();
    }

    @Test(expected = NotGroupOwnerException.class)
    public void shouldNotAddImageIfNotGroupOwner() throws NotGroupOwnerException, GroupNotFoundException,
        MyPetCareException {
        trAddGroupImage.setUser(user);
        user.setUsername("Tomas Roncero");
        trAddGroupImage.setGroup(group);
        trAddGroupImage.setImage(image);
        trAddGroupImage.execute();
    }

    @Test
    public void shouldAddGroupImage() throws NotGroupOwnerException, GroupNotFoundException, MyPetCareException {
        trAddGroupImage.setUser(user);
        trAddGroupImage.setGroup(group);
        trAddGroupImage.setImage(image);
        trAddGroupImage.execute();
    }

    @After
    public void restartStubData() {
        StubCommunityService.addStubDefaultData();
    }
}
