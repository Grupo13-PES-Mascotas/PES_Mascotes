package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.community.posts.Post;

public class PostComponentView extends CircularEntryView {
    private static final int DATE = 0;
    private static final int HOUR = 1;
    public static final String DATE_TIME_SEPARATOR = "T";
    public static final char HOUR_SEPARATOR = ':';
    private Post post;

    public PostComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostComponentView(Context context, AttributeSet attrs, Post post) {
        super(context, attrs);
        this.post = post;
    }

    @Override
    protected CircularImageView getImage() {
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        Drawable groupDrawable = getResources().getDrawable(R.drawable.single_paw);

        image.setDrawable(groupDrawable);
        int imageDimensions = getImageDimensions();
        image.setLayoutParams(new LinearLayout.LayoutParams(imageDimensions, imageDimensions));
        int imageId = View.generateViewId();
        image.setId(imageId);

        return image;
    }

    @Override
    public Object getObject() {
        return post;
    }

    @Override
    protected String getFirstLineText() {
        String strCreationDate = post.getCreationDate().toString();
        String[] dateTimeParts = strCreationDate.split(DATE_TIME_SEPARATOR);

        return post.getUsername() + " " + dateTimeParts[DATE] + " "
            + dateTimeParts[HOUR].substring(0, dateTimeParts[HOUR].lastIndexOf(HOUR_SEPARATOR));
    }

    @Override
    protected String getSecondLineText() {
        return post.getText();
    }
}
