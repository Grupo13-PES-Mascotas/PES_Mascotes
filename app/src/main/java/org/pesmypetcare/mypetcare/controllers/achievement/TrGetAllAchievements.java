package org.pesmypetcare.mypetcare.controllers.achievement;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.achievement.AchievementService;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class TrGetAllAchievements {
    private AchievementService achievementService;
    private User user;
    private List<UserMedalData> result;

    public TrGetAllAchievements(AchievementService achievementService) {
        this.achievementService = achievementService;
    }

    /**
     * Set the user.
     * @param user The user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Execute the transaction.
     * @throws MyPetCareException Throws an exception if user not exists
     */
    public void execute() throws MyPetCareException {
        result = achievementService.getAllAchievements(user);
    }

    /**
     * Return the result of the transaction.
     * @return All achievements of the user
     */
    public List<UserMedalData> getResult() {
        return result;
    }
}
