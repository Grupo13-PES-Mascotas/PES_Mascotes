package org.pesmypetcare.mypetcare.features.community;

import android.graphics.Bitmap;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Group {
    private String name;
    private String ownerUsername;
    private String description;
    private DateTime creationDate;
    private Bitmap groupIcon;
    private List<String> participants;
    private List<Forum> forums;
    private List<String> tags;

    public Group(String name, String ownerUsername, DateTime creationDate) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.creationDate = creationDate;
        this.participants = new ArrayList<>();
        this.forums = new ArrayList<>();
        this.tags = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Bitmap getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(Bitmap groupIcon) {
        this.groupIcon = groupIcon;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Group group = (Group) o;
        return name.equals(group.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Group{"
            + "name='" + name + '\''
            + '}';
    }
}
