package org.pesmypetcare.mypetcare.activities.fragments.imagezoom;

import android.graphics.drawable.Drawable;

/**
 * @author Albert Pinto
 */
public interface ImageZoomCommunication {

    /**
     * Update the image of the user.
     * @param drawable The image of the user that has to be updated
     */
    void updateUserImage(Drawable drawable);
}
