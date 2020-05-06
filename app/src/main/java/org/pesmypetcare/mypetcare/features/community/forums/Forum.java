package org.pesmypetcare.mypetcare.features.community.forums;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class Forum implements Comparable<Forum> {
    private String name;
    private String ownerUsername;
    private DateTime creationDate;
    private List<String> participants;
    private SortedSet<Post> posts;
    private List<String> tags;
    private Group group;

    public Forum(String name, String ownerUsername, DateTime creationDate, Group group) {
        this.name = name;
        this.ownerUsername = ownerUsername;
        this.creationDate = creationDate;
        this.participants = new ArrayList<>();
        this.posts = new TreeSet<>();
        this.tags = new ArrayList<>();
        this.group = group;

        this.group.addForum(this);
    }

    /**
     * Get the name of the forum.
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the forum.
     * @param name The name of the forum to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the owner username of the forum.
     * @return The owner username
     */
    public String getOwnerUsername() {
        return ownerUsername;
    }

    /**
     * Set the owner username of the forum.
     * @param ownerUsername The owner username of the forum to set
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * Get the creation date of the forum.
     * @return The creation date
     */
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * Set the creation date of the forum.
     * @param creationDate The creation date of the forum to set
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Get the participants of the forum.
     * @return The participants
     */
    public List<String> getParticipants() {
        return participants;
    }

    /**
     * Set the participants of the forum.
     * @param participants The participants of the forum to set
     */
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    /**
     * Get the posts of the forum.
     * @return The posts
     */
    public SortedSet<Post> getPosts() {
        return posts;
    }

    /**
     * Set the posts of the forum.
     * @param posts The posts of the forum to set
     */
    public void setPosts(SortedSet<Post> posts) {
        this.posts = posts;
    }

    /**
     * Get the tags of the forum.
     * @return The tags
     */
    public List<String> getTags() {
        return tags;
    }

    /**
     * Set the tags of the forum.
     * @param tags The tags of the forum to set
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    /**
     * Add a tag to the forum.
     * @param tag The tag to add
     */
    public void addTag(String tag) {
        tags.add(tag);
    }

    /**
     * Get the group.
     * @return The group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Adds the given post to the forum.
     * @param post The post that has to be added to the forum
     */
    public void addPost(Post post) {
        posts.add(post);
    }

    /**
     * Removes a post from the forum.
     * @param user The author of the post that has to be removed
     * @param creationDate The creation date of the post that has to be removed
     */
    public void removePost(User user, DateTime creationDate) {
        Post aux = findPost(user, creationDate);
        if (aux != null) {
            posts.remove(aux);
        }
    }

    /**
     * Obtains a post from its author and creation date.
     * @param author The author of the post that we want to find
     * @param creationDate The creation date of the post that we want to find
     * @return The post if it exists in the forum or false otherwise
     */
    private Post findPost(User author, DateTime creationDate) {
        for (Post p : posts) {
            if (p.getCreationDate().compareTo(creationDate) == 0 && p.getUsername().equals(author.getUsername())) {
                return p;
            }
        }
        return null;
    }

    @Override
    public int compareTo(Forum forum) {
        if (!creationDate.equals(forum.getCreationDate())) {
            return creationDate.compareTo(forum.getCreationDate());
        }

        return name.compareTo(forum.name);
    }

    @Override
    public String toString() {
        return "Forum{"
            + "name='" + name + '\''
            + '}';
    }


}
