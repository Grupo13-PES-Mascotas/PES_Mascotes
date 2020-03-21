package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class CircularImageView extends AppCompatImageView {
    private Bitmap bitmap;

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Drawable drawable = getDrawable();
        if (drawable != null) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
            setImage(bitmap);
        }
    }

    /**
     * Sets the image to be displayed, given its resource id.
     * @param imageResourceId Resource id of the image to be displayed
     */
    public void setImage(int imageResourceId) {
        bitmap = BitmapFactory.decodeResource(getResources(), imageResourceId);
        setImage(bitmap);
    }

    /**
     * Sets the image to be displayed, given its bitmap.
     * @param bitmap Bitmap of the image to be displayed
     */
    public void setImage(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmap.setCircular(true);
        setImageDrawable(roundedBitmap);
    }

    /**
     * Get the bitmap of the image.
     * @return The bitmap of the image
     */
    public Bitmap getBitmap() {
        return bitmap;
    }
}
