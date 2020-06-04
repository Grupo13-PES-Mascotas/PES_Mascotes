package org.pesmypetcare.mypetcare.services.achievement;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public interface AchievementService {

    List<UserMedalData> getAllAchievements(User user) throws MyPetCareException;

    void updateAchievement(String nameAchievement, Double newProgress, User user) throws MyPetCareException;
}
