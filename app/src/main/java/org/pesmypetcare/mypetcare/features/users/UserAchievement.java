package org.pesmypetcare.mypetcare.features.users;

import org.pesmypetcare.mypetcare.utilities.DateTime;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class UserAchievement extends Achievement{

    private Integer progress;
    private List<DateTime> completedLevelsDate;

    public UserAchievement(String name, int description) {
        super(name, description);
    }

    public void updateProgress(Integer newProgress) throws InvalidFormatException {
        DateTime today = DateTime.Builder.build(2020, 5, 29);
        this.progress = newProgress;
        List<Integer> levels = getLevels();
        for (int i = 0; i < levels.size(); ++i) {
            if(progress.equals(levels.get(i))) {
                completedLevelsDate.add(i, today);
            }
        }
    }

    public Integer getProgress() {
        return progress;
    }

    public int getCurrentLevel() {
        return completedLevelsDate.size();
    }

    public double getCurrentGoal() {
        int level = getCurrentLevel();
        List<Integer> levels = getLevels();
        return levels.get(level+1);
    }
}
