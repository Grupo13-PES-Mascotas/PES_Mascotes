package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.forums.NotForumOwnerException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;
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

    public static void addStubDefaultData() {
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

        StubCommunityService.groups.get(HUSKY).addTag("dog");
        StubCommunityService.groups.get(HUSKY).addTag("domestic");
        StubCommunityService.groups.get(TURTLES).addTag("turtle");
        StubCommunityService.groups.get(TURTLES).addTag("wild");
        StubCommunityService.groups.get(ELEPHANTS).addTag("savanna");
        StubCommunityService.groups.get(ELEPHANTS).addTag("Africa");
        StubCommunityService.groups.get(DINOSAURS).addTag("extinct");

        new Forum("Washing", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:00:00"),
            StubCommunityService.groups.get(HUSKY));
        Forum forum = new Forum("Cleaning", "John Doe", DateTime.Builder.buildFullString("2020-04-21T20:50:10"),
            StubCommunityService.groups.get(HUSKY));
        forum.addTag("important");
        new Forum("Washing", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:00:00"),
            StubCommunityService.groups.get(DINOSAURS));
        new Forum("Sickling", "John Doe", DateTime.Builder.buildFullString("2020-04-22T10:10:00"),
            StubCommunityService.groups.get(DINOSAURS));
        forum.addPost(new Post("John Doe", "I think that the huskies have to be kept cleaned. What do you think?",
            DateTime.Builder.buildFullString("2020-04-21T20:55:10"), forum));
        forum.addPost(new Post("John Doe", "I'm very interested in your answers",
            DateTime.Builder.buildFullString("2020-04-21T21:15:22"), forum));
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
    public void createPost(User user, Forum forum, Post post) throws ForumNotFoundException, PostAlreadyExistingException {
        if (!forumExists(forum.getGroup(), forum)) {
            throw new ForumNotFoundException();
        }
        for (Group g : groups) {
            if (g.getName().equals(forum.getGroup().getName())) {
                for (Forum f : g.getForums()) {
                    if (f.getName().equals(forum.getName())) {
                        for (Post p : f.getPosts()) {
                            if (p.getUsername().equals(post.getUsername()) && p.getCreationDate().equals(post.getCreationDate())) {
                                throw new PostAlreadyExistingException();
                            }
                        }
                        f.addPost(post);
                    }
                }
            }
        }
    }

    @Override
    public void deletePost(User user, Forum forum, DateTime postCreationDate) throws ForumNotFoundException, PostNotFoundException {
        if (!forumExists(forum.getGroup(), forum)) {
            throw new ForumNotFoundException();
        }
        for (Group g : groups) {
            if (g.getName().equals(forum.getGroup().getName())) {
                for (Forum f : g.getForums()) {
                    if (f.getName().equals(forum.getName())) {
                        boolean found = false;
                        for (Post p : f.getPosts()) {
                            if (p.getUsername().equals(user.getUsername()) &&
                                p.getCreationDate().compareTo(postCreationDate) == 0) {
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
                for (Forum f : g.getForums()) {
                    if (f.getName().equals(postForum.getName())) {
                        boolean found = false;
                        for (Post p : f.getPosts()) {
                            if (p.getUsername().equals(user.getUsername()) &&
                            p.getCreationDate().compareTo(post.getCreationDate()) == 0) {
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
    }
}
