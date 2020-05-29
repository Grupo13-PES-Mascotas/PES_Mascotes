package org.pesmypetcare.mypetcare.features.users;

import org.pesmypetcare.mypetcare.utilities.DateTime;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class UserAchievement extends Achievement{

    private Double progress;
    private List<DateTime> completedLevelsDate;

    public UserAchievement(String name, String description) {
        super(name, description);
    }

    public void updateProgress(Double newProgress) throws InvalidFormatException {
        DateTime today = DateTime.Builder.build(2020, 5, 29);
        this.progress = newProgress;
        List<Double> levels = getLevels();
        for (int i = 0; i < levels.size(); ++i) {
            if(progress.equals(levels.get(i))) {
                completedLevelsDate.add(i, today);
            }
        }
    }

    public Double getProgress() {
        return progress;
    }
}
