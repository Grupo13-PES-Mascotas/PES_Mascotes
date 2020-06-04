package org.pesmypetcare.mypetcare.controllers.achievement;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.achievement.AchievementAdapter;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;

import static org.junit.Assert.assertFalse;


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

    @Test
    public void shouldGetAllAchievements() throws MyPetCareException {
        trGetAllAchievements.setUser(user);
        trGetAllAchievements.execute();
        List<UserMedalData> list = trGetAllAchievements.getResult();
        assertFalse("Should have the correct size", list.isEmpty());
    }

}
