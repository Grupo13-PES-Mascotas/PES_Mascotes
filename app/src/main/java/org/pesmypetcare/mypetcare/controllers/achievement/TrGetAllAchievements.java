package org.pesmypetcare.mypetcare.controllers.achievement;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;
import org.pesmypetcare.mypetcare.services.achievement.AchievementService;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class TrGetAllAchievements {
    private AchievementService achievementService;
    private User user;
    private List<UserAchievement> result;

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

    public void execute() {
        result = user.getAchievements();
    }

    public List<UserAchievement> getResult() {
        return result;
    }
}
