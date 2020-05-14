package org.pesmypetcare.mypetcare.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.MyPetCareException;
import org.pesmypetcare.mypetcare.controllers.user.TrUpdateUserImage;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertEquals;

public class TestTrUpdateUserImage {
    private static final int COLOR_GREEN = 0x00FF00;
    private static final int COLOR_BLUE = 0x0000FF;
    private User user;
    private TrUpdateUserImage trUpdateUserImage;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        user.setUserProfileImage(BitmapFactory.decodeByteArray(new byte[] {(byte) COLOR_GREEN}, 0, 1));
        trUpdateUserImage = new TrUpdateUserImage(new StubUserManagerService());
    }

    @Test
    public void shouldUpdateUserImage() throws MyPetCareException {
        Bitmap bitmap = BitmapFactory.decodeByteArray(new byte[] {(byte) COLOR_BLUE}, 0, 1);
        trUpdateUserImage.setUser(user);
        trUpdateUserImage.setImage(bitmap);
        trUpdateUserImage.execute();

        assertEquals("Should update user image", bitmap, user.getUserProfileImage());
    }
}
