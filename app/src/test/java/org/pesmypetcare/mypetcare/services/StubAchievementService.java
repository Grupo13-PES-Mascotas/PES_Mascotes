package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.achievement.AchievementService;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author Daniel Clemente
 */
public class StubAchievementService implements AchievementService {
    private Map<String, List<UserMedalData>> medals;

    public StubAchievementService() {
        medals = new TreeMap<>();
        UserMedalData userMedalData = new UserMedalData("Medal", Arrays.asList(10.0, 20.0, 30.0), "Test", new byte[0],
            0.0, 0.0, null);
        medals.put("JohnSmith", Collections.singletonList(userMedalData));
    }

    @Override
    public List<UserMedalData> getAllAchievements(User user) {
        return medals.get(user.getUsername());
    }

    @Override
    public void updateMedalProgress(User user, String medal) {
        List<UserMedalData> userMedals = medals.get(user.getUsername());

        for (UserMedalData userMedalData : Objects.requireNonNull(userMedals)) {
            if (userMedalData.getName().equals(medal)) {
                userMedalData.setProgress(userMedalData.getProgress() + 1);
                break;
            }
        }
    }
}
