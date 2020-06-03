package org.pesmypetcare.mypetcare.services.achievement;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class AchievementAdapter implements AchievementService {
    @Override
    public List<UserAchievement> getAllAchievements(User user) {
        return user.getAchievements();
    }
}
