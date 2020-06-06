package org.pesmypetcare.mypetcare.services.achievement;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
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
     * Update an achievement of an user.
     * @param nameAchievement The name of the achievement
     * @param newProgress The new progress of the achievement
     * @param user The user to update his achievement
     * @throws MyPetCareException if the user doesn't exist
     */
    void updateAchievement(String nameAchievement, Double newProgress, User user) throws MyPetCareException;
}
