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

    /**
     * Set the name of the achievement to update.
     * @param nameAchievement The name of the achievement
     */
    public void setNameAchievement(String nameAchievement) {
        this.nameAchievement = nameAchievement;
    }

    /**
     * Set the new value to add to progress of the achievement.
     * @param newProgress The value to add
     */
    public void setNewProgress(int newProgress) {
        this.newProgress = newProgress;
    }

    /**
     * Execute the transaction.
     * @throws InvalidFormatException throws an exception if the date is in an invalid format
     * @throws MyPetCareException throws an exception if the current not exists
     */
    public void execute() throws InvalidFormatException, MyPetCareException {
        user.updateAchievementProgress(this.nameAchievement, newProgress);
        Double progress = (double) newProgress;
        achievementService.updateAchievement(nameAchievement, progress, user);
    }

}
