package org.pesmypetcare.mypetcare.controllers.achievement;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.achievement.AchievementService;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

/**
 * @author Daniel Clemente
 */
public class TrUpdateAchievement {
    private AchievementService achievementService;
    private User user;
    private String nameAchievement;
    private int newProgress;

    public TrUpdateAchievement(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    /**
     * Set the user.
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void setNameAchievement(String nameAchievement) {
        this.nameAchievement = nameAchievement;
    }

    public void setNewProgress(int newProgress) {
        this.newProgress = newProgress;
    }

    public void execute() throws InvalidFormatException, MyPetCareException {
        user.updateAchievementProgress(this.nameAchievement, newProgress);
        Double progress = (double) newProgress;
        achievementService.updateAchievement(nameAchievement, progress, user);
    }

}
