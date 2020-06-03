package org.pesmypetcare.mypetcare.controllers.achievement;

import org.junit.Before;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.achievement.AchievementAdapter;

/**
 * @author Daniel Clemente
 */
public class TestTrGetAllAchievements {
    private TrGetAllAchievements trGetAllAchievements;
    private User user;

    @Before
    public void setUp() {
        trGetAllAchievements = new TrGetAllAchievements(new AchievementAdapter());
        user = new User("John Doe", "lamacope@gmail.com", "BICHO");
    }


}
