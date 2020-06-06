package org.pesmypetcare.mypetcare.services.achievement;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public interface AchievementService {

    /**
     * Get all the achievements of a user and his information.
     * @param user The user to get the achievements
     * @return The achievements
     */
    List<UserMedalData> getAllAchievements(User user);
}
