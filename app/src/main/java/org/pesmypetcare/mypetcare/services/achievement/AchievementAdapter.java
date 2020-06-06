package org.pesmypetcare.mypetcare.services.achievement;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.user.UserMedalData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Daniel Clemente
 */
public class AchievementAdapter implements AchievementService {
    private static final int TIME = 20;


    @Override
    public List<UserMedalData> getAllAchievements(User user) {
        AtomicReference<List<UserMedalData>> listMedals = new AtomicReference<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                listMedals.set(ServiceLocator.getInstance().getUserMedalManagerClient()
                        .getAllMedals(user.getToken(), user.getUsername()));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return listMedals.get();
    }

    @Override
    public void updateAchievement(String nameAchievement, Double newProgress, User user) throws MyPetCareException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            UserMedalData medal = null;
            try {
                medal = ServiceLocator.getInstance().getUserMedalManagerClient()
                        .getMedal(user.getToken(), user.getUsername(), nameAchievement);
                Double progress = medal.getProgress();
                ServiceLocator.getInstance().getUserMedalManagerClient()
                        .updateField(user.getToken(), user.getUsername(), nameAchievement, "progress",
                                progress + newProgress);
                Double currentLevel = (Double) ServiceLocator.getInstance().getUserMedalManagerClient()
                        .getField(user.getToken(), user.getUsername(), nameAchievement, "currentLevel");
                List<Double> levels = (List<Double>) ServiceLocator.getInstance().getUserMedalManagerClient()
                        .getField(user.getToken(), user.getUsername(), nameAchievement, "levels");
                if (progress >= levels.get((int) (currentLevel + 1))) {
                    ServiceLocator.getInstance().getUserMedalManagerClient()
                            .updateField(user.getToken(), user.getUsername(), nameAchievement, "currentLevel",
                                    currentLevel + 1);
                }
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }


}
