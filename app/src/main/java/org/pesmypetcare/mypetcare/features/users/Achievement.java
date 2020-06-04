package org.pesmypetcare.mypetcare.features.users;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class Achievement {
    private String name;
    private int description;
    private List<Integer> levels;

    public Achievement(String name, int description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Get the name of the achievement.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the achievement.
     * @return The description
     */
    public int getDescription() {
        return description;
    }

    /**
     * Get a list with the goal of an every level.
     * @return The list of the levels
     */
    public List<Integer> getLevels() {
        return levels;
    }

    /**
     * Set the levels with a list that contains the goal of every level.
     * @param levels The levels
     */
    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }
}


