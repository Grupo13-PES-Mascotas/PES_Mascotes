package org.pesmypetcare.mypetcare.services.achievement;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class AchievementAdapter implements AchievementService {
    @Override
    public List<UserMedalData> getAllAchievements(User user) throws MyPetCareException {
        return ServiceLocator.getInstance().getUserMedalManagerClient().getAllMedals(user.getToken(), user.getUsername());
    }

    @Override
    public void updateAchievement(String nameAchievement, Double newProgress, User user) throws MyPetCareException {
        UserMedalData medal = ServiceLocator.getInstance().getUserMedalManagerClient().
                getMedal(user.getToken(), user.getUsername(), nameAchievement);
        Double progress = medal.getProgress();
        ServiceLocator.getInstance().getUserMedalManagerClient().
                updateField(user.getToken(), user.getUsername(), nameAchievement, "progress",
                        progress + newProgress);
    }

}
