package org.pesmypetcare.mypetcare.features.community.groups;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import org.pesmypetcare.httptools.exceptions.InvalidFormatException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Group implements Comparable<Group> {
    private String name;
    private String ownerUsername;
    private String description;
    private DateTime creationDate;
    private Bitmap groupIcon;
    private DateTime lastGroupImage;
    private List<String> participants;
    private Map<String, DateTime> subscribers;
    private Map<String, Bitmap> subscribersImages;
    private SortedSet<Forum> forums;
    private List<String> tags;

    public Group(String name, String ownerUsername, DateTime creationDate) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.creationDate = creationDate;
        this.participants = new ArrayList<>();
        this.forums = new TreeSet<>();
        this.tags = new ArrayList<>();
        this.subscribers = new TreeMap<>();
        this.subscribersImages = new TreeMap<>();
        try {
            this.lastGroupImage = DateTime.Builder.build(2020, 1, 1);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        this.subscribers.put(ownerUsername, creationDate);
    }

    /**
     * Get the name.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the owner username.
     * @return The owner username
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Set the owner username.
     * @param ownerUsername The owner username to set
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * Get the description.
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the participants.
     * @return The participants
     */
    public List<String> getParticipants() {
        return participants;
    }

    /**
     * Set the participants.
     * @param participants The participants to set
     */
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    /**
     * Get the creation date.
     * @return The creation date
     */
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date.
     * @param creationDate The creation date to set
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the group icon.
     * @return The group icon
     */
    public Bitmap getGroupIcon() {
        return groupIcon;
    }

    /**
     * Set the group icon.
     * @param groupIcon The group icon to set
     */
    public void setGroupIcon(Bitmap groupIcon) {
        this.groupIcon = groupIcon;
    }

    /**
     * Get the subscribers.
     * @return The subscribers
     */
    public Map<String, DateTime> getSubscribers() {
        return subscribers;
    }

    /**
     * Get the tags.
     * @return The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Get the forums.
     * @return The forums
     */
    public SortedSet<Forum> getForums() {
        return forums;
    }

    /**
     * Add a tag.
     * @param tag The tag to add
     */
    public void addTag(String tag) {
        tags.add(tag);
    }

    /**
     * Add a forum.
     * @param forum The forum to add
     */
    public void addForum(Forum forum) {
        forums.add(forum);
    }

    /**
     * Add a subscriber.
     * @param subscriber The subscriber to add
     */
    public void addSubscriber(User subscriber) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        String strData = dateFormat.format(date);

        subscribers.put(subscriber.getUsername(), DateTime.Builder.buildDateString(strData));
        subscribersImages.put(subscriber.getUsername(), subscriber.getUserProfileImage());
    }

    /**
     * Add a subscriber.
     * @param username The username of the subscriber
     * @param date The date of the subscription
     */
    public void addSubscriber(String username, DateTime date) {
        subscribers.put(username, date);
    }

    /**
     * Check if the user is a subscriber.
     * @param user The user
     * @return True if the user is a subscriber
     */
    public boolean isUserSubscriber(User user) {
        return subscribers.containsKey(user.getUsername());
    }

    /**
     * Remove a subscriber.
     * @param subscriber The subscriber to remove
     */
    public void removeSubscriber(User subscriber) {
        subscribers.remove(subscriber.getUsername());
        subscribersImages.remove(subscriber.getUsername());
    }

    /**
     * Remove a forum.
     * @param forum The forum to remove
     */
    public void removeForum(Forum forum) {
        forums.remove(forum);
    }

    public Map<String, Bitmap> getSubscribersImages() {
        return subscribersImages;
    }

    public void addSubscriberImage(String username, Bitmap image) {
        subscribersImages.put(username, image);
    }

    public Bitmap getUserImage(String username) {
        return subscribersImages.get(username);
    }

    public DateTime getLastGroupImage() {
        return lastGroupImage;
    }

    public void setLastGroupImage(DateTime lastGroupImage) {
        this.lastGroupImage = lastGroupImage;
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

    @NonNull
    @Override
    public String toString() {
        return "Group{"
            + "name='" + name + '\''
            + ", ownerUsername='" + ownerUsername + '\''
            + ", description='" + description + '\''
            + ", creationDate=" + creationDate
            + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Group group) {
        return name.compareTo(group.getName());
    }
}
