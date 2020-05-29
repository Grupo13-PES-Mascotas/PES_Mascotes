package org.pesmypetcare.mypetcare.features.users;

import java.util.List;

/**
 * @author Daniel Clemente
 */
public class Achievement {
    private String name;
    private String description;
    private List<Double> levels;

    public Achievement(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() { return description; }

    public List<Double> getLevels() { return levels; }

    public void setLevels(List<Double> levels) {
        this.levels = levels;
    }
}


