package org.pesmypetcare.mypetcare.controllers.achievements;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.UserAchievement;
import org.pesmypetcare.mypetcare.services.StubAchievementService;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrUpdateMedalProgress {
    private User user;
    private TrUpdateMedalProgress trUpdateMedalProgress;

    @Before
    public void setUp() {
        user = new User("JohnDoe", "johndoe@gmail.com", "1234");
        trUpdateMedalProgress = new TrUpdateMedalProgress(new StubAchievementService());
    }

    @Test
    public void shouldUpdateProgress() {
        UserAchievement userAchievement = new UserAchievement("Medal", "A stub medal");
        userAchievement.setLevels(Arrays.asList(10.0, 20.0, 30.0));
        user.addAchievement(userAchievement);

        trUpdateMedalProgress.setUser(user);
        trUpdateMedalProgress.setMedalName("Medal");
        trUpdateMedalProgress.execute();

        assertEquals("Should update the medal progress", 1, user.getMedalProgress("Medal"));
    }

    @Test
    public void shouldChangeLevel() {
        UserAchievement userAchievement = new UserAchievement("Medal", "A stub medal");
        userAchievement.setLevels(Arrays.asList(10.0, 20.0, 30.0));
        userAchievement.setProgress(9.0);
        user.addAchievement(userAchievement);

        trUpdateMedalProgress.setUser(user);
        trUpdateMedalProgress.setMedalName("Medal");
        trUpdateMedalProgress.execute();

        assertEquals("Should change the medal level", 1, user.getMedalLevel("Medal"));
    }

    @Test
    public void shouldNotIncrementProgressIfMaximumLevel() {
        UserAchievement userAchievement = new UserAchievement("Medal", "A stub medal");
        userAchievement.setLevels(Arrays.asList(10.0, 20.0, 30.0));
        userAchievement.setProgress(30.0);
        userAchievement.setCurrentLevel(3);
        user.addAchievement(userAchievement);

        trUpdateMedalProgress.setUser(user);
        trUpdateMedalProgress.setMedalName("Medal");
        trUpdateMedalProgress.execute();

        assertEquals("Should change the medal level", 30.0, user.getMedalProgress("Medal"), 0.005);
    }

    @Test
    public void shouldNotChangeLevelIfMaximumLevel() {
        UserAchievement userAchievement = new UserAchievement("Medal", "A stub medal");
        userAchievement.setLevels(Arrays.asList(10.0, 20.0, 30.0));
        userAchievement.setProgress(30.0);
        userAchievement.setCurrentLevel(3);
        user.addAchievement(userAchievement);

        trUpdateMedalProgress.setUser(user);
        trUpdateMedalProgress.setMedalName("Medal");
        trUpdateMedalProgress.execute();

        assertEquals("Should change the medal level", 3, user.getMedalLevel("Medal"));
    }
}
