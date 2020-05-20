package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyLikedException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class StubCommunityService implements CommunityService {
    private static final int HUSKY = 0;
    private static final int TURTLES = 1;
    private static final int ELEPHANTS = 2;
    private static final int DINOSAURS = 3;

    private static List<Group> groups;

    static {
        addStubDefaultData();
    }

    /**
     * Add the default data for the stub.
     */
    public static void addStubDefaultData() {
        addGroups();
        addTags();
        addForums();
    }

    /**
     * Add the forums.
     */
    private static void addForums() {
        new Forum("Washing", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:00:00"),
            StubCommunityService.groups.get(HUSKY));
        Forum forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"),
            StubCommunityService.groups.get(HUSKY));
        forum.addTag("important");
        new Forum("Washing", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:00:00"),
            StubCommunityService.groups.get(DINOSAURS));
        Forum forum2 = new Forum("Sickling", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:10:00"),
            StubCommunityService.groups.get(DINOSAURS));
        Post post = new Post("John Doe", "I think that the huskies have to be kept cleaned. What do you think?",
            DateTime.Builder.buildFullString("2020-04-21T20:55:10"), forum);
        post.setPostImage(BitmapFactory.decodeByteArray(new byte[]{(byte) 0x00}, 0, 1));
        forum.addPost(post);
        forum.addPost(new Post("John Doe", "I'm very interested in your answers",
            DateTime.Builder.buildFullString("2020-04-21T21:15:22"), forum));
        forum.addPost(new Post("Manolo Lama", "I would love to clean the Bicho",
            DateTime.Builder.buildFullString("2020-04-28T12:00:00"), forum));
        forum2.addPost(new Post("John Doe", "I would love to clean the Bicho 2",
            DateTime.Builder.buildFullString("2020-04-28T12:00:00"), forum2));
    }

    /**
     * Add the tags.
     */
    private static void addTags() {
        StubCommunityService.groups.get(HUSKY).addTag("dog");
        StubCommunityService.groups.get(HUSKY).addTag("domestic");
        StubCommunityService.groups.get(TURTLES).addTag("turtle");
        StubCommunityService.groups.get(TURTLES).addTag("wild");
        StubCommunityService.groups.get(ELEPHANTS).addTag("savanna");
        StubCommunityService.groups.get(ELEPHANTS).addTag("Africa");
        StubCommunityService.groups.get(DINOSAURS).addTag("extinct");
    }

    /**
     * Add the groups.
     */
    private static void addGroups() {
        StubCommunityService.groups = new ArrayList<>();
        StubCommunityService.groups.add(new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15")));
        StubCommunityService.groups.add(new Group("Turtles", "John Doe",
            DateTime.Builder.buildDateString("2020-04-16")));
        StubCommunityService.groups.add(new Group("Elephants", "Enric",
            DateTime.Builder.buildDateString("2020-04-14")));
        StubCommunityService.groups.get(HUSKY).addSubscriber(new User("John Smith",
            "johnSmith@gmail.com", "1234"));
        StubCommunityService.groups.add(new Group("Dinosaur", "Gradle",
            DateTime.Builder.buildDateString("2019-11-23")));
    }

    @Override
    public SortedSet<Group> getAllGroups() {
        return new TreeSet<>(groups);
    }

    @Override
    public void createGroup(User user, Group group) throws GroupAlreadyExistingException {
        for (Group actualGroup : groups) {
            if (group.equals(actualGroup)) {
                throw new GroupAlreadyExistingException();
            }
        }
        groups.add(group);
    }

    @Override
    public void deleteGroup(String groupName) throws GroupNotFoundException {
        boolean found = false;
        for (int i = 0; i < groups.size() && !found; ++i) {
            if (groupName.equals(groups.get(i).getName())) {
                groups.remove(i);
                found = true;
            }
        }
        if (!found) {
            throw new GroupNotFoundException();
        }
    }

    @Override
    public boolean isGroupExisting(Group group) {
        return groups.contains(group);
    }

    @Override
    public void addSubscriber(User user, Group group) {
        int index = groups.indexOf(group);
        user.addSubscribedGroup(groups.get(index));
    }

    @Override
    public void deleteSubscriber(User user, Group group) {
        int index = groups.indexOf(group);
        groups.get(index).removeSubscriber(user);
    }

    @Override
    public void createForum(User user, Group group, Forum forum) {
        int index = groups.indexOf(group);
        groups.get(index).addForum(forum);
    }

    @Override
    public void deleteForum(User user, Group group, Forum forum) throws ForumNotFoundException, NotForumOwnerException {
        if (!forumExists(group, forum)) {
            throw new ForumNotFoundException();
        }
        if (!forum.getOwnerUsername().equals(user.getUsername())) {
            throw new NotForumOwnerException();
        }
        group.removeForum(forum);
    }

    /**
     * Checks whether a forum exists or not.
     * @param group The group where the forum should be.
     * @param forum The forum to check
     * @return True if the forum was found or false otherwise
     */
    private boolean forumExists(Group group, Forum forum) {
        for (Group g : groups) {
            if (g.getName().equals(group.getName())) {
                for (Forum f : g.getForums()) {
                    if (f.getName().equals(forum.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void createPost(User user, Forum forum, Post post) throws ForumNotFoundException,
        PostAlreadyExistingException {
        if (!forumExists(forum.getGroup(), forum)) {
            throw new ForumNotFoundException();
        }
        for (Group g : groups) {
            createPostInGroup(forum, post, g);
        }
    }

    /**
     * Creates a post in the group.
     * @param forum The forum
     * @param post The post
     * @param group The group
     * @throws PostAlreadyExistingException The post already exists
     */
    private void createPostInGroup(Forum forum, Post post, Group group) throws PostAlreadyExistingException {
        if (group.getName().equals(forum.getGroup().getName())) {
            for (Forum f : group.getForums()) {
                if (f.getName().equals(forum.getName())) {
                    for (Post p : f.getPosts()) {
                        if (p.getUsername().equals(post.getUsername())
                            && p.getCreationDate().equals(post.getCreationDate())) {
                            throw new PostAlreadyExistingException();
                        }
                    }
                    f.addPost(post);
                }
            }
        }
    }

    @Override
    public void deletePost(User user, Forum forum, DateTime postCreationDate) throws ForumNotFoundException,
        PostNotFoundException {
        if (!forumExists(forum.getGroup(), forum)) {
            throw new ForumNotFoundException();
        }
        for (Group g : groups) {
            deleteGroupPosts(user, forum, postCreationDate, g);
        }
    }

    /**
     * Deletes the posts form a group.
     * @param user The user
     * @param forum The forum
     * @param postCreationDate The creation date of a post
     * @param group The group
     * @throws PostNotFoundException The post is not found
     */
    private void deleteGroupPosts(User user, Forum forum, DateTime postCreationDate, Group group)
        throws PostNotFoundException {
        if (group.getName().equals(forum.getGroup().getName())) {
            for (Forum f : group.getForums()) {
                if (f.getName().equals(forum.getName())) {
                    boolean found = false;
                    for (Post p : f.getPosts()) {
                        if (p.getUsername().equals(user.getUsername())
                            && p.getCreationDate().compareTo(postCreationDate) == 0) {
                            found = true;
                            f.removePost(user, postCreationDate);
                        }
                    }
                    if (!found) {
                        throw new PostNotFoundException();
                    }
                }
            }
        }
    }

    @Override
    public void updatePost(User user, Post post, String newText) throws ForumNotFoundException, PostNotFoundException {
        Forum postForum = post.getForum();
        Group postGroup = postForum.getGroup();
        if (!forumExists(postGroup, postForum)) {
            throw new ForumNotFoundException();
        }
        for (Group g : groups) {
            if (g.getName().equals(postGroup.getName())) {
                updateForums(user, post, newText, postForum, g);
            }
        }
    }

    @Override
    public void likePost(User user, Post post) throws PostNotFoundException, PostAlreadyLikedException {
        boolean found = false;
        for (Group g : groups) {
            if (g.getName().equals(post.getForum().getGroup().getName())) {
                for (Forum f : g.getForums()) {
                    if (f.getName().equals(post.getForum().getName())) {
                        for (Post p : f.getPosts()) {
                            if (p.getUsername().equals(post.getUsername())
                                && p.getCreationDate().compareTo(post.getCreationDate()) == 0) {
                                if (p.getLikerUsername().contains(user.getUsername())) {
                                    throw new PostAlreadyLikedException();
                                }
                                p.addLikerUsername(user.getUsername());
                                found = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!found) {
            throw new PostNotFoundException();
        }
    }

    @Override
    public void unlikePost(User user, Post post) {
        int groupIndex = groups.indexOf(post.getForum().getGroup());

        for (Forum forum : groups.get(groupIndex).getForums()) {
            if (forum.equals(post.getForum())) {
                for (Post forumPost : forum.getPosts()) {
                    if (forumPost.equals(post)) {
                        forumPost.removeLikerUsername(user.getUsername());
                    }
                }
            }
        }
    }

    @Override
    public void reportPost(User user, Post post, String reportMessage) {
        int groupIndex = groups.indexOf(post.getForum().getGroup());

        for (Forum forum : groups.get(groupIndex).getForums()) {
            if (forum.equals(post.getForum())) {
                for (Post forumPost : forum.getPosts()) {
                    if (forumPost.equals(post)) {
                        forumPost.reportPost();
                    }
                }
            }
        }
    }

    @Override
    public void addPostImage(User user, Post post, Bitmap image) throws PostNotFoundException {
        int groupIndex = groups.indexOf(post.getForum().getGroup());
        boolean found = false;

        for (Forum forum : groups.get(groupIndex).getForums()) {
            if (forum.getName().equals(post.getForum().getName())) {
                for (Post forumPost : forum.getPosts()) {
                    if (forumPost.getUsername().equals(post.getUsername())
                    && forumPost.getCreationDate().compareTo(post.getCreationDate()) == 0) {
                        post.setPostImage(image);
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            throw new PostNotFoundException();
        }
    }

    @Override
    public void deletePostImage(User user, Post post) throws PostNotFoundException {
        int groupIndex = groups.indexOf(post.getForum().getGroup());
        boolean found = false;

        for (Forum forum : groups.get(groupIndex).getForums()) {
            if (forum.getName().equals(post.getForum().getName())) {
                for (Post forumPost : forum.getPosts()) {
                    if (forumPost.getUsername().equals(post.getUsername())
                        && forumPost.getCreationDate().compareTo(post.getCreationDate()) == 0) {
                        post.setPostImage(null);
                        found = true;
                        break;
                    }
                }
            }
        }
        if (!found) {
            throw new PostNotFoundException();
        }
    }

    @Override
    public void addGroupImage(User user, Group group, Bitmap image) throws GroupNotFoundException {
        int groupIndex = groups.indexOf(group);
        if (groupIndex == -1) {
            throw new GroupNotFoundException();
        } else {
            groups.get(groupIndex).setGroupIcon(image);
        }
    }

    @Override
    public void deleteGroupImage(User user, Group group) throws GroupNotFoundException {
        int groupIndex = groups.indexOf(group);
        if (groupIndex == -1) {
            throw new GroupNotFoundException();
        } else {
            groups.get(groupIndex).setGroupIcon(null);
        }
    }

    @Override
    public byte[] getPostImage(User user, Post post, MessageDisplay messageDisplay) {
        int groupIndex = groups.indexOf(post.getForum().getGroup());
        Forum selectedForum = null;

        for (Forum forum : groups.get(groupIndex).getForums()) {
            if (post.getForum().getName().equals(forum.getName())) {
                selectedForum = forum;
                break;
            }
        }

        for (Post forumPost : Objects.requireNonNull(selectedForum).getPosts()) {
            if (forumPost.getCreationDate().compareTo(post.getCreationDate()) == 0) {
                return new byte[] {0x00};
            }
        }

        return null;
    }

    @Override
    public byte[] getGroupImage(User user, Group group) {
        for (Group g : groups) {
            if (g.getName().equals(group.getName())) {
                return new byte[] {0x00};
            }
        }

        return null;
    }

    /**
     * Update the forums.
     * @param user The user
     * @param post The post
     * @param newText The new text
     * @param postForum The forum of the post
     * @param group The group
     * @throws PostNotFoundException The post is not found
     */
    private void updateForums(User user, Post post, String newText, Forum postForum, Group group)
        throws PostNotFoundException {
        for (Forum f : group.getForums()) {
            if (f.getName().equals(postForum.getName())) {
                boolean found = false;
                for (Post p : f.getPosts()) {
                    if (p.getUsername().equals(user.getUsername())
                            && p.getCreationDate().compareTo(post.getCreationDate()) == 0) {
                        found = true;
                        p.setText(newText);
                    }
                }
                if (!found) {
                    throw new PostNotFoundException();
                }
            }
        }
    }
}
