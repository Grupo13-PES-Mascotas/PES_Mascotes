package org.pesmypetcare.mypetcare.features.community;

import android.graphics.Bitmap;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String name;
    private String ownerUsername;
    private List<String> participants;
    private DateTime creationDate;
    private Bitmap groupIcon;

    public Group(String name, String ownerUsername, DateTime creationDate) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.creationDate = creationDate;
        this.participants = new ArrayList<>();
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
}
