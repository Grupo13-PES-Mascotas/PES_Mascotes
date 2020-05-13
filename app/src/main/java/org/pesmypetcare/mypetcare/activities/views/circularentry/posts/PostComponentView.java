package org.pesmypetcare.mypetcare.activities.views.circularentry.posts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;

public class PostComponentView extends CircularEntryView {
    private static final int DATE = 0;
    private static final int HOUR = 1;
    private static final String DATE_TIME_SEPARATOR = "T";
    private static final char HOUR_SEPARATOR = ':';
    private static final String WHITE_SPACE = " ";
    private static final int BOTTOM_IMAGE_HEIGHT = 500;

    private Post post;
    private User user;

    public PostComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PostComponentView(Context context, AttributeSet attrs, Post post, User user) {
        super(context, attrs);
        this.post = post;
        this.user = user;
    }

    @Override
    protected CircularImageView getImage() {
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        Drawable groupDrawable = getResources().getDrawable(R.drawable.single_paw, null);

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

        return post.getUsername() + WHITE_SPACE + dateTimeParts[HOUR].substring(0,
            dateTimeParts[HOUR].lastIndexOf(HOUR_SEPARATOR));
    }

    @Override
    protected String getSecondLineText() {
        return post.getText();
    }

    @Override
    protected ImageView getRightImage() {
        if (post.getUsername().equals(user.getUsername())) {
            return null;
        }

        ImageView likeImage = getBasicImageView();

        if (post.isLikedByUser(user.getUsername())) {
            likeImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_like_blue, null));
        } else {
            likeImage.setImageDrawable(getResources().getDrawable(R.drawable.icon_like, null));
        }

        return likeImage;
    }

    /**
     * Get the basic image view.
     * @return The basic image view
     */
    private ImageView getBasicImageView() {
        ImageView imageView = new ImageView(getContext(), null);
        imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView.setId(View.generateViewId());
        return imageView;
    }

    @Override
    protected ImageView getBottomImage() {
        if (post.getPostImage() == null) {
            return null;
        }

        ImageView bottomImage = new ImageView(getContext(), null);
        bottomImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, BOTTOM_IMAGE_HEIGHT));
        bottomImage.setId(View.generateViewId());
        bottomImage.setImageBitmap(post.getPostImage());

        return bottomImage;
    }
}
