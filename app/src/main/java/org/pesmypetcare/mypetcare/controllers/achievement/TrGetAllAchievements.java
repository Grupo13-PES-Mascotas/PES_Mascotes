package org.pesmypetcare.mypetcare.controllers.achievement;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;
import org.pesmypetcare.mypetcare.services.achievement.AchievementService;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class TrGetAllAchievements {
    private AchievementService achievementService;
    private User user;

    public TrGetAllAchievements(AchievementService achievementService) {
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
     * Execute the transaction.
     */
    public void execute() {
        List<UserMedalData> userMedalDataList = achievementService.getAllAchievements(user);

        for (UserMedalData medalData : userMedalDataList) {
            UserAchievement achievement = new UserAchievement(medalData.getName(), medalData.getDescription());
            achievement.setLevels(medalData.getLevels());
            achievement.setCurrentLevel(medalData.getCurrentLevel().intValue());
            achievement.setProgress(medalData.getProgress());

            user.addAchievement(achievement);
        }
    }
}
