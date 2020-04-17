package org.pesmypetcare.mypetcare.activities.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.features.community.Group;

import java.util.List;

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
            if (actual != 0) {
                strTags.append(',');
            }

            strTags.append('#').append(tags.get(actual));
        }

        return strTags.toString();
    }
}
