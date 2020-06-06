package org.pesmypetcare.mypetcare.controllers.achievements;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;
import org.pesmypetcare.mypetcare.services.StubAchievementService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Daniel Clemente
 */
public class TestTrGetAllAchievements {
    private User user;
    private TrGetAllAchievements trGetAllAchievements;

    @Before
    public void setUp() {
        user = new User("JohnSmith", "johndoe@gmail.com", "1234");
        trGetAllAchievements = new TrGetAllAchievements(new StubAchievementService());
    }

    @Test
    public void shouldGetAllAchievementsFromUser() {
        trGetAllAchievements.setUser(user);
        trGetAllAchievements.execute();

        List<String> achievementsName = new ArrayList<>();

        for (UserAchievement userAchievement : user.getAchievements()) {
            achievementsName.add(userAchievement.getName());
        }

        assertEquals("Should get all achievements", "[Medal]", achievementsName.toString());
    }
}
