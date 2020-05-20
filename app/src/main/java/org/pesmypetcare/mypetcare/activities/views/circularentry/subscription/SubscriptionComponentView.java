package org.pesmypetcare.mypetcare.activities.views.circularentry.subscription;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * @author Xavier Campos & Albert Pinto
 */
public class SubscriptionComponentView extends CircularEntryView {
    private User user;
    private Group group;

    public SubscriptionComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SubscriptionComponentView(Context context, AttributeSet attrs, User user, Group group) {
        super(context, attrs);
        this.user = user;
        this.group = group;
    }

    @Override
    protected CircularImageView getImage() {
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        Drawable groupDrawable = getResources().getDrawable(R.drawable.icon_group, null);

        if (group.getGroupIcon() != null) {
            groupDrawable = new BitmapDrawable(getResources(), group.getGroupIcon());
        }

        image.setDrawable(groupDrawable);
        int imageDimensions = getImageDimensions();
        image.setLayoutParams(new LinearLayout.LayoutParams(imageDimensions, imageDimensions));
        int imageId = View.generateViewId();
        image.setId(imageId);

        return image;
    }

    @Override
    public Object getObject() {
        return group;
    }

    @Override
    protected String getFirstLineText() {
        StringBuilder groupName = new StringBuilder(group.getName());

        if (group.getOwnerUsername().equals(user.getUsername())) {
            groupName.append(' ').append(getResources().getString(R.string.owner));
        }

        return groupName.toString();
    }

    @Override
    protected String getSecondLineText() {
        List<String> tags = group.getTags();
        StringBuilder strTags = new StringBuilder("");

        for (int actual = 0; actual < tags.size(); ++actual) {
            addActualTag(tags, strTags, actual);
        }

        if (strTags.length() == 0) {
            strTags.append(getResources().getString(R.string.no_tags));
        }

        return strTags.toString();
    }

    /**
     * Add the actual tag.
     * @param tags The tag list
     * @param strTags The tags from the input
     * @param actual The actual tag index
     */
    private void addActualTag(List<String> tags, StringBuilder strTags, int actual) {
        if (!tags.get(actual).equals("")) {
            if (actual != 0) {
                strTags.append(',');
            }

            strTags.append('#').append(tags.get(actual));
        }
    }

    @Override
    protected ImageView getRightImage() {
        return null;
    }

    @Override
    protected ImageView getBottomImage() {
        return null;
    }
}
