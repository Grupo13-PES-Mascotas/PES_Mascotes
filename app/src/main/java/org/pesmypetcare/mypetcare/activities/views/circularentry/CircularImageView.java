package org.pesmypetcare.mypetcare.activities.views.circularentry;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;

import org.pesmypetcare.mypetcare.R;

public class CircularImageView extends CardView {
    private static final float RADIUS = 200.0f;
    private ImageView imageView;

    public CircularImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        setRadius(RADIUS);

        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new CardView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.user_icon_sample, null));

        addView(imageView);
    }

    /**
     * Gets the drawable of the image.
     * @return The drawable of the image
     */
    public Drawable getDrawable() {
        return imageView.getDrawable();
    }

    /**
     * Sets the drawable of the image.
     * @param drawable The drawable of the image to set
     */
    public void setDrawable(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    @Override
    public void setRadius(float radius) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            radius = 0;
        }

        super.setRadius(radius);
    }
}
