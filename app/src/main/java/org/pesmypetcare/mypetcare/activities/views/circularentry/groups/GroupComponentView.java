package org.pesmypetcare.mypetcare.activities.views.circularentry.groups;

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

import java.util.List;

/**
 * @author Xavier Campos & Albert Pinto
 */
public class GroupComponentView extends CircularEntryView {
    private Group group;

    public GroupComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupComponentView(Context context, AttributeSet attrs, Group group) {
        super(context, attrs);
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
        return group.getName();
    }

    @Override
    protected String getSecondLineText() {
        List<String> tags = group.getTags();
        StringBuilder strTags = new StringBuilder("");

        for (int actual = 0; actual < tags.size(); ++actual) {
            appendTags(tags, strTags, actual);
        }

        if (strTags.length() == 0) {
            strTags.append(getResources().getString(R.string.no_tags));
        }

        return strTags.toString();
    }

    @Override
    protected ImageView getRightImage() {
        return null;
    }

    @Override
    protected ImageView getBottomImage() {
        return null;
    }

    /**
     * Append the tags to the StringBuilder.
     * @param tags The list of tags
     * @param strTags The string of tags from the input
     * @param actual The actual tag index
     */
    private void appendTags(List<String> tags, StringBuilder strTags, int actual) {
        if (!tags.get(actual).equals("")) {
            if (actual != 0) {
                strTags.append(' ');
            }

            strTags.append('#').append(tags.get(actual));
        }
    }
}
