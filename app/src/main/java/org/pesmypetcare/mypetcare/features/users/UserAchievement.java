package org.pesmypetcare.mypetcare.features.users;

import java.util.List;
import java.util.Objects;

/**
 * @author Albert Pinto
 */
public class UserAchievement implements Comparable<UserAchievement> {
    private String name;
    private String description;
    private List<Double> levels;
    private int currentLevel;
    private double progress;

    public UserAchievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Double> getLevels() {
        return levels;
    }

    public void setLevels(List<Double> levels) {
        this.levels = levels;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void incrementProgress() {
        if (currentLevel != 3) {
            ++progress;

            if (progress == levels.get(currentLevel)) {
                ++currentLevel;
            }
        }
    }

    @Override
    public int compareTo(UserAchievement userAchievement) {
        return name.compareTo(userAchievement.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAchievement that = (UserAchievement) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
