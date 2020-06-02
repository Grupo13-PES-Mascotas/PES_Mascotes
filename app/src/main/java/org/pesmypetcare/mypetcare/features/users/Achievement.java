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

    public String getName() {
        return name;
    }

    public int getDescription() { return description; }

    public List<Integer> getLevels() { return levels; }

    public void setLevels(List<Integer> levels) {
        this.levels = levels;
    }
}


