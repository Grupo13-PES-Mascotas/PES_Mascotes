package org.pesmypetcare.mypetcare.activities.views.circularentry.achievements;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularEntryView;
import org.pesmypetcare.mypetcare.activities.views.circularentry.CircularImageView;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;

import java.util.Locale;

/**
 * @author Álvaro Trius
 */
public class AchievementsComponentView extends CircularEntryView {
    private UserAchievement achievement;

    public AchievementsComponentView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AchievementsComponentView(Context context, AttributeSet attrs, UserAchievement achievement) {
        super(context, attrs);
        this.achievement = achievement;
    }

    @Override
    protected CircularImageView getImage() {
        CircularImageView image = new CircularImageView(getCurrentActivity(), null);
        int currentLevel = achievement.getCurrentLevel();
        Drawable achievementDrawable = getResources().getDrawable(R.drawable.medal_empty, null);

        if (currentLevel > 0) {
            String medalName = achievement.getName().toLowerCase(Locale.getDefault()).replace(' ', '_');
            String medalDrawableName = "medal_" + medalName + "_" + (currentLevel - 1);
            int id = getResources().getIdentifier(medalDrawableName, "drawable", getContext().getPackageName());
            achievementDrawable = getResources().getDrawable(id, null);
        }

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
        String medalName = achievement.getName().toLowerCase(Locale.getDefault()).replace(' ', '_');
        int id = getResources().getIdentifier("medal_" + medalName, "string", getContext().getPackageName());
        return getResources().getString(id);
    }

    @Override
    protected String getSecondLineText() {
        String medalName = achievement.getName().toLowerCase(Locale.getDefault()).replace(' ', '_');
        String medalNameString = "medal_" + medalName + "_description";
        int id = getResources().getIdentifier(medalNameString, "string", getContext().getPackageName());
        String description = getResources().getString(id);

        int currentLevel = achievement.getCurrentLevel();

        String levelInfo = getResources().getString(R.string.level) + " " + currentLevel + " - "
            + getResources().getString(R.string.next_level) + " ";

        if (currentLevel == 3) {
            return description + "\n" + levelInfo + getResources().getString(R.string.max);
        }

        double nextLevelBorder = achievement.getLevels().get(currentLevel);
        return description + "\n" + levelInfo + (int) achievement.getProgress() + " / " + (int) nextLevelBorder;
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
