package org.pesmypetcare.mypetcare.activities.views.circularentry.userachievements;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Álvaro Trius Béjar
 */
public class UserAchievementComponentView extends CircularEntryView {
    private UserMedalData achievement;

    public UserAchievementComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UserAchievementComponentView(Context context, AttributeSet attrs, UserMedalData achievement) {
        super(context, attrs);
        this.achievement = achievement;
    }

    @Override
    protected CircularImageView getImage() {
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        Drawable achievementDrawable = getResources().getDrawable(R.drawable.medalla, null);

        /*
        if (achievement.getAchievementIcon() != null) {
            achievementDrawable = new BitmapDrawable(getResources(), achievement.getAchievementIcon());
        }
        */

        image.setDrawable(achievementDrawable);
        int imageDimensions = getImageDimensions();
        image.setLayoutParams(new LinearLayout.LayoutParams(imageDimensions, imageDimensions));
        int imageId = View.generateViewId();
        image.setId(imageId);

        return image;
    }

    @Override
    public Object getObject() {
        return achievement;
    }

    @Override
    protected String getFirstLineText() {
        return achievement.getName();
    }

    @Override
    protected String getSecondLineText() {
        Double newData = achievement.getCurrentLevel();
        int currentLevel = newData.intValue();
        List<Double> list = achievement.getLevels();
        list.get(currentLevel + 1);
        return achievement.getProgress().toString() + "/" + achievement.getCurrentLevel();
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
