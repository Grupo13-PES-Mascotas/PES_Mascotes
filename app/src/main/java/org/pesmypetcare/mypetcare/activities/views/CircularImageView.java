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
    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        Drawable drawable = getDrawable();
        if (drawable != null) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            setImage(bitmap);
        }
    }

    public void setImage(int imageResourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageResourceId);
        setImage(bitmap);
    }

    public void setImage(Bitmap bitmap) {
        RoundedBitmapDrawable roundedBitmap = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundedBitmap.setCircular(true);
        setImageDrawable(roundedBitmap);
    }
}
