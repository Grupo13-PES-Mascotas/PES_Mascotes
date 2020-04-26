package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

public class SubscriberComponentView extends CircularEntryView {
    private static final String HYPHEN = "-";
    private String username;
    private DateTime subscriptionDate;
    private Group group;

    public SubscriberComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubscriberComponentView(Context context, AttributeSet attrs, String username, DateTime subscriptionDate,
                                   Group group) {
        super(context, attrs);
        this.username = username;
        this.subscriptionDate = subscriptionDate;
        this.group = group;
    }

    @Override
    protected CircularImageView getImage() {
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        Drawable groupDrawable = getResources().getDrawable(R.drawable.user_icon_sample);

        image.setDrawable(groupDrawable);
        int imageDimensions = getImageDimensions();
        image.setLayoutParams(new LinearLayout.LayoutParams(imageDimensions, imageDimensions));
        int imageId = View.generateViewId();
        image.setId(imageId);

        return image;
    }

    @Override
    public Object getObject() {
        return username;
    }

    @Override
    protected String getFirstLineText() {
        if (group.getOwnerUsername().equals(username)) {
            return username + " " + getResources().getString(R.string.owner);
        }

        return username;
    }

    @Override
    protected String getSecondLineText() {
        return subscriptionDate.getYear() + HYPHEN + subscriptionDate.getMonth() + HYPHEN + subscriptionDate.getDay();
    }
}
