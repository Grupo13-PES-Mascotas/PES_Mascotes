package org.pesmypetcare.mypetcare.controllers.achievement;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;
import org.pesmypetcare.mypetcare.services.achievement.AchievementAdapter;
import org.pesmypetcare.mypetcare.utilities.InvalidFormatException;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Clemente
 */
public class TestTrUpdateAchievement {
    private TrUpdateAchievement trUpdateAchievement;
    private User user;
    private User user2;
    private Integer progress = 1;
    private Integer one = 1;
    private Integer zero = 0;
    private UserAchievement userAchievement;
    private String name = "Contributor";
    private String name2 = "Superwalker";

    @Before
    public void setUp() {
        trUpdateAchievement = new TrUpdateAchievement(new AchievementAdapter());
        user = new User("John Doe", "lamacope@gmail.com", "BICHO");
        user2 = new User("Dani", "guapeton@gmail.com", "1234");
    }

    @Test
    public void shouldUpdateContributorAchievement() throws InvalidFormatException, MyPetCareException {
        trUpdateAchievement.setUser(user);
        trUpdateAchievement.setNewProgress(progress);
        trUpdateAchievement.setNameAchievement(name);
        trUpdateAchievement.execute();
        List<UserAchievement> list = user.getAchievements();
        for(UserAchievement achievement : list) {
            if (achievement.getName().equals(name)) {
                userAchievement = achievement;
            }
        }
        assertEquals("Should be one", one, userAchievement.getProgress());
    }

    @Test
    public void shouldNoUpdateAchievementFromOtherUser() throws InvalidFormatException, MyPetCareException {
        trUpdateAchievement.setUser(user);
        trUpdateAchievement.setNewProgress(progress);
        trUpdateAchievement.setNameAchievement(name2);
        trUpdateAchievement.execute();
        List<UserAchievement> list = user2.getAchievements();
        for (UserAchievement achievement : list) {
            if (achievement.getName().equals(name2)) {
                userAchievement = achievement;
            }
        }
        assertEquals("Should be zero", zero, userAchievement.getProgress());
    }
}
