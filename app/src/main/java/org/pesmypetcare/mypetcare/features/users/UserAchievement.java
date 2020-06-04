package org.pesmypetcare.mypetcare.features.users;

import org.pesmypetcare.mypetcare.utilities.DateTime;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class UserAchievement extends Achievement {

    private Integer progress;
    private List<DateTime> completedLevelsDate;
    private int year = 2020;

    public UserAchievement(String name, int description) {

        super(name, description);
        progress = 0;
    }

    /**
     * Update the progress of an achievement.
     * @param newProgress The value to add to progress
     * @throws InvalidFormatException Throw if the date is in invalid format
     */
    public void updateProgress(Integer newProgress) throws InvalidFormatException {
        DateTime today = DateTime.Builder.build(year, 5, 29);
        this.progress += newProgress;
        List<Integer> levels = getLevels();
        for (int i = 0; i < levels.size(); ++i) {
            if (progress.equals(levels.get(i))) {
                completedLevelsDate.add(i, today);
            }
        }
    }

    /**
     * Get the progress of an achievement.
     * @return The progress
     */
    public Integer getProgress() {
        return progress;
    }

    /**
     * Get the current level of an achievement.
     * @return The current level
     */
    public int getCurrentLevel() {
        return completedLevelsDate.size();
    }

    /**
     * Get the goal to get the next level.
     * @return The goal
     */
    public double getCurrentGoal() {
        int level = getCurrentLevel();
        List<Integer> levels = getLevels();
        if (level + 1 <= levels.size() - 1) {
            return levels.get(level + 1);
        } else {
            return levels.get(levels.size() - 1);
        }
    }
}
