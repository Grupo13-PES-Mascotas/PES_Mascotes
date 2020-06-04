package org.pesmypetcare.mypetcare.activities.fragments.achievements;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente & Álvaro Trius
 */
public interface AchievementsCommunication {
    /**
     * Get all the achievements.
     * @return The achievements
     * @throws MyPetCareException if the user doesn't exist
     */
    List<UserMedalData> getAllAchievements() throws MyPetCareException;
}
