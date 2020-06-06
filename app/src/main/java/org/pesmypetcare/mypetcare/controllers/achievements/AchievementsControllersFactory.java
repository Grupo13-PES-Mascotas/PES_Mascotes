package org.pesmypetcare.mypetcare.controllers.achievements;

import org.pesmypetcare.mypetcare.services.achievement.AchievementAdapter;

/**
 * @author Albert Pinto
 */
public class AchievementsControllersFactory {

    private AchievementsControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for obtaining all the achievements.
     * @return The transaction for obtaining all the achievements
     */
    public static TrGetAllAchievements createTrGetAllAchievements() {
        return new TrGetAllAchievements(new AchievementAdapter());
    }

    /**
     * Create the transaction for updating the medal progress.
     * @return The transaction for updating the medal progress
     */
    public static TrUpdateMedalProgress createTrUpdateMedalProgress() {
        return new TrUpdateMedalProgress(new AchievementAdapter());
    }
}
