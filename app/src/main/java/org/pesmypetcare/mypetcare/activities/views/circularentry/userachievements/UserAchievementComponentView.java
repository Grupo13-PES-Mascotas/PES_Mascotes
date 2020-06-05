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

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        String medalName = achievement.getName();
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        Drawable achievementDrawable = getResources().getDrawable(R.drawable.gourmet, null);

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
        int currentLevel = achievement.getCurrentLevel().intValue();
        int currentLevelBorder;

        if (currentLevel == 0) {
            currentLevelBorder = 0;
        } else {
            currentLevelBorder = achievement.getLevels().get(currentLevel - 1).intValue();
        }

        double nextLevelBorder = achievement.getLevels().get(currentLevel);
        double percentage = (achievement.getProgress() - currentLevelBorder) / (nextLevelBorder - currentLevel);
        String levelInfo = getResources().getString(R.string.level) + " " + (currentLevel + 1) + " - "
            + getResources().getString(R.string.next_level) + " ";

        if (currentLevel == 2 && achievement.getProgress() >= nextLevelBorder) {
            return levelInfo + getResources().getString(R.string.max);
        }

        BigDecimal bigDecimal = new BigDecimal(percentage);
        bigDecimal = bigDecimal.setScale(0, RoundingMode.HALF_EVEN);

        return levelInfo + achievement.getProgress() + " / " + nextLevelBorder
            + "(" + bigDecimal.toString() + "%)";
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
