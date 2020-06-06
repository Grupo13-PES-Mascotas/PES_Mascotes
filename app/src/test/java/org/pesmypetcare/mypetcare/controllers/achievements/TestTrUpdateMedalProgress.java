package org.pesmypetcare.mypetcare.controllers.achievements;

import org.junit.Before;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;

import java.util.Arrays;

/**
 * @author Albert Pinto
 */
public class TestTrUpdateMedalProgress {
    private User user;

    @Before
    public void setUp() {
        user = new User("JohnDoe", "johndoe@gmail.com", "1234");

        UserAchievement userAchievement = new UserAchievement("Medal", "A stub medal");
        userAchievement.setLevels(Arrays.asList(10.0, 20.0, 30.0));
        user.addAchievement(userAchievement);
    }
}
