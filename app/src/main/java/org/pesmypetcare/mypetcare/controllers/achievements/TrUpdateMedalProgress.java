package org.pesmypetcare.mypetcare.controllers.achievements;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.achievement.AchievementService;

/**
 * @author Albert Pinto
 */
public class TrUpdateMedalProgress {
    private AchievementService achievementService;
    private User user;
    private String medal;
    
    public TrUpdateMedalProgress(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMedalName(String medal) {
        this.medal = medal;
    }

    public void execute() {
        user.incrementMedalProgress(medal);
        achievementService.updateMedalProgress(user, medal);
    }
}
