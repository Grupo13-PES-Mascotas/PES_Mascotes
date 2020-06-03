package org.pesmypetcare.mypetcare.activities.fragments.achievements;

import org.pesmypetcare.mypetcare.features.users.UserAchievement;

import java.util.List;

/**
 * @author Daniel Clemente & Álvaro Trius
 */
public interface AchievementsCommunication {

    List<UserAchievement> getAllAchievements();
}
