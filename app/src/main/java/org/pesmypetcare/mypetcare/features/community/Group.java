package org.pesmypetcare.mypetcare.features.community;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Group implements Comparable<Group> {
    private String name;
    private String ownerUsername;
    private String description;
    private DateTime creationDate;
    private Bitmap groupIcon;
    private List<String> participants;
    private Map<String, DateTime> subscribers;
    private List<Forum> forums;
    private List<String> tags;

    public Group(String name, String ownerUsername, DateTime creationDate) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.creationDate = creationDate;
        this.participants = new ArrayList<>();
        this.forums = new ArrayList<>();
        this.tags = new ArrayList<>();
        this.subscribers = new TreeMap<>();

        this.subscribers.put(ownerUsername, creationDate);
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

    public Map<String, DateTime> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(User user) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-d");
        Date date = new Date();
        String strData = dateFormat.format(date);

        subscribers.put(user.getUsername(), DateTime.Builder.buildDateString(strData));
    }

    public boolean isUserSubscriber(User user) {
        return subscribers.containsKey(user.getUsername());
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

    @Override
    public int compareTo(Group group) {
        return name.compareTo(group.getName());
    }
}
