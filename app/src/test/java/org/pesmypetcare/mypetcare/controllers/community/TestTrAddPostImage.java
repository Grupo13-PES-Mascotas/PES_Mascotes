package org.pesmypetcare.mypetcare.controllers.community;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.junit.Before;
import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

/**
 * @author Xavier Campos
 */
public class TestTrAddPostImage {
    private TrAddPostImage trAddPostImage;
    private Bitmap image;
    private User user;
    private Post post;

    @Before
    public void setUp() {
        trAddPostImage = new TrAddPostImage(new StubCommunityService());
        image = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.test);

    }
}
