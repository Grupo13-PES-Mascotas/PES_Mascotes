package org.pesmypetcare.mypetcare.services.achievement;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public interface AchievementService {

    List<UserAchievement> getAllAchievements(User user);
}
