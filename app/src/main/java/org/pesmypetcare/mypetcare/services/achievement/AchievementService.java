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

    /**
     * Update the medal progress.
     * @param user The user to update the medal progress
     * @param medal The medal name to update
     */
    void updateMedalProgress(User user, String medal);
}
