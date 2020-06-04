package org.pesmypetcare.mypetcare.activities.fragments.achievements;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente & Álvaro Trius
 */
public interface AchievementsCommunication {

    List<UserMedalData> getAllAchievements() throws MyPetCareException;
}
