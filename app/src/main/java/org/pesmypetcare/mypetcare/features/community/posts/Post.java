package org.pesmypetcare.mypetcare.features.community.posts;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Post implements Comparable<Post> {
    private String username;
    private String text;
    private int likes;
    private int reportsCount;
    private boolean isBanned;
    private List<String> likerUsername;
    private DateTime creationDate;
    private Bitmap userImage;
    private Bitmap postImage;
    private Forum forum;

    public Post(String username, String text, DateTime creationDate, Forum forum) {
        this.username = username;
        this.text = text;
        this.creationDate = creationDate;
        this.forum = forum;
        this.likerUsername = new ArrayList<>();
        this.likerUsername.add(username);
        this.likes = 1;
    }

    /**
     * Get the username of the post.
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username of the post.
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the text of the post.
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * Set the text of the post.
     * @param text The text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Get the likes of the post.
     * @return The likes
     */
    public int getLikes() {
        return likes;
    }

    /**
     * Set the likes of the post.
     * @param likes The likes to set
     */
    public void setLikes(int likes) {
        this.likes = likes;
    }

    /**
     * Get the reports count of the post.
     * @return The reports count
     */
    public int getReportsCount() {
        return reportsCount;
    }

    /**
     * Set the reports count of the post.
     * @param reportsCount The reports count to set
     */
    public void setReportsCount(int reportsCount) {
        this.reportsCount = reportsCount;
    }

    /**
     * Check if the post is banned.
     * @return True if the post is banned
     */
    public boolean isBanned() {
        return isBanned;
    }

    /**
     * Set the banned state of the post.
     * @param banned The banned state to set
     */
    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    /**
     * Get the creation date of the post.
     * @return The creation date
     */
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date of the post.
     * @param creationDate The creation date to set
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the user image of the post.
     * @return The user image
     */
    public Bitmap getUserImage() {
        return userImage;
    }

    /**
     * Set the user image of the post.
     * @param userImage The user image to set
     */
    public void setUserImage(Bitmap userImage) {
        this.userImage = userImage;
    }

    /**
     * Get the post image of the post.
     * @return The post image
     */
    public Bitmap getPostImage() {
        return postImage;
    }

    /**
     * Set the image of the post.
     * @param postImage The image to set
     */
    public void setPostImage(Bitmap postImage) {
        this.postImage = postImage;
    }

    /**
     * Getter of the forum of the post.
     * @return The forum of the post
     */
    public Forum getForum() {
        return forum;
    }

    /**
     * Get the liker username.
     * @return The liker username
     */
    public List<String> getLikerUsername() {
        return likerUsername;
    }

    /**
     * Set the liker username.
     * @param likerUsername The liker username to set
     */
    public void setLikerUsername(List<String> likerUsername) {
        this.likerUsername = likerUsername;
    }

    /**
     * Add a liker username.
     * @param username The liker username
     */
    public void addLikerUsername(String username) {
        likerUsername.add(username);
        ++likes;
    }

    /**
     * Remove the liker username.
     * @param username The liker username
     */
    public void removeLikerUsername(String username) {
        likerUsername.remove(username);
        --likes;
    }

    /**
     * Check whether the user has given a like to the post.
     * @param username The username
     * @return True if the user has given a like to the post
     */
    public boolean isLikedByUser(String username) {
        return likerUsername.contains(username);
    }


    @Override
    public int compareTo(Post post) {
        if (!creationDate.equals(post.getCreationDate())) {
            return creationDate.compareTo(post.getCreationDate());
        }

        return username.compareTo(post.getUsername());
    }

    @Override
    public String toString() {
        return "Post{"
            + "username='" + username + '\''
            + ", text='" + text + '\''
            + '}';
    }

    /**
     * Increases the number of reports of the post.
     */
    public void reportPost() {
        reportsCount++;
    }
}
