package org.pesmypetcare.mypetcare.controllers.achievement;

import org.pesmypetcare.mypetcare.services.achievement.AchievementAdapter;

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
        //si quieres probar un stub, cambia el new AchievementAdapter por el stub
    }

    public static TrUpdateAchievement createTrUpdateAchievement() {
        return new TrUpdateAchievement(new AchievementAdapter());
    }
}
