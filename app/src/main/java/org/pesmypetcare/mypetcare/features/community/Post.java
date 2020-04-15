package org.pesmypetcare.mypetcare.features.community;

import android.graphics.Bitmap;

import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

public class Post {
    private String username;
    private String text;
    private int likes;
    private int reportsCount;
    private boolean isBanned;
    private DateTime creationDate;
    private Bitmap userImage;

    public Post(String username, String text, DateTime creationDate) {
        this.username = username;
        this.text = text;
        this.creationDate = creationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getReportsCount() {
        return reportsCount;
    }

    public void setReportsCount(int reportsCount) {
        this.reportsCount = reportsCount;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Bitmap getUserImage() {
        return userImage;
    }

    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }
}
