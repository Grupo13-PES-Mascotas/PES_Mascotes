package org.pesmypetcare.mypetcare.controllers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubUserManagerService;

import static org.junit.Assert.assertEquals;

public class TestTrUpdateUserImage {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final float RIGHT = 20.0f;
    private static final float BOTTOM = 20.0f;

    private User user;
    private TrUpdateUserImage trUpdateUserImage;

    @Before
    public void setUp() {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        user.setUserProfileImage(BitmapFactory.decodeByteArray(new byte[] {(byte) 0x00FF00}, 0, 1));
        trUpdateUserImage = new TrUpdateUserImage(new StubUserManagerService());
    }

    @Test
    public void shouldUpdateUserImage() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(new byte[] {(byte) 0x0000FF}, 0, 1);
        trUpdateUserImage.setUser(user);
        trUpdateUserImage.setImage(bitmap);
        trUpdateUserImage.execute();

        assertEquals("Should update user image", bitmap, user.getUserProfileImage());
    }

    private Bitmap getBitmap(int color) {
        return BitmapFactory.decodeByteArray(new byte[] {(byte) color}, 0, 1);
    }
}
