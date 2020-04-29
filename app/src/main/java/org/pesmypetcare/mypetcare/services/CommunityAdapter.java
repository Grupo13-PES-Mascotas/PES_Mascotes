package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.communitymanager.datacontainers.ForumData;
import org.pesmypetcare.communitymanager.datacontainers.GroupData;
import org.pesmypetcare.communitymanager.datacontainers.MessageData;
import org.pesmypetcare.httptools.MyPetCareException;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class CommunityAdapter implements CommunityService {
    public static final int TIME = 20;

    @Override
    public SortedSet<Group> getAllGroups() {
        List<GroupData> groupsData = getAllGroupsFromServer();
        SortedSet<Group> groups = new TreeSet<>();

        for (GroupData groupData : groupsData) {
            Group group = createGroup(groupData);
            addForums(group);

            groups.add(group);
        }

        return groups;
    }

    /**
     * Create the group.
     * @param groupData The data of the group
     * @return The created group
     */
    private Group createGroup(GroupData groupData) {
        Group group = new Group(groupData.getName(), groupData.getCreator(),
            DateTime.Builder.buildFullString(groupData.getCreationDate()));
        group.setDescription(groupData.getDescription());

        for (String tag : groupData.getTags()) {
            group.addTag(tag);
        }

        for (Map.Entry<String, String> subscription : groupData.getMembers().entrySet()) {
            group.addSubscriber(subscription.getKey(),
                DateTime.Builder.buildFullString(subscription.getValue()));
        }
        return group;
    }

    /**
     * Add the forums to the group.
     * @param group The group to which the forums have to be added
     */
    private void addForums(Group group) {
        AtomicReference<List<ForumData>> forumsData = new AtomicReference<>();

        ExecutorService executorService = createAddForumsExecutorService(group, forumsData);

        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        createForums(group, forumsData);
    }

    /**
     * Create the add forums executor service.
     * @param group The group
     * @param forumsData The forums data
     * @return The executor service for adding a new forum
     */
    private ExecutorService createAddForumsExecutorService(Group group, AtomicReference<List<ForumData>> forumsData) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                forumsData.set(ServiceLocator.getInstance().getForumManagerClient().getAllForums(group.getName()));
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        return executorService;
    }

    /**
     * Create the forums.
     * @param group The group to add the forums
     * @param forumsData The data of the forums
     */
    private void createForums(Group group, AtomicReference<List<ForumData>> forumsData) {
        for (ForumData forumData : forumsData.get()) {
            Forum forum = new Forum(forumData.getName(), forumData.getCreator(),
                DateTime.Builder.buildFullString(forumData.getCreationDate()), group);

            group.addForum(forum);
        }
    }

    /**
     * Get all the groups from server.
     * @return The groups from the server
     */
    private List<GroupData> getAllGroupsFromServer() {
        AtomicReference<List<GroupData>> groupsData = new AtomicReference<>();

        ExecutorService executorService = getExecutorServiceForGetAllGroups(groupsData);

        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return groupsData.get();
    }

    /**
     * Create the executor service to get all groups.
     * @param groupsData The data of the groups
     * @return The executor service to get all groups
     */
    private ExecutorService getExecutorServiceForGetAllGroups(AtomicReference<List<GroupData>> groupsData) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                groupsData.set(ServiceLocator.getInstance().getGroupManagerClient().getAllGroups());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        return executorService;
    }

    @Override
    public void createGroup(User user, Group group) {
        GroupData groupData = new GroupData(group.getName(), group.getOwnerUsername(), group.getDescription(),
            group.getTags());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGroupManagerClient().createGroup(groupData);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void deleteGroup(String groupName) throws GroupNotFoundException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGroupManagerClient().deleteGroup(groupName);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public boolean isGroupExisting(Group group) {
        AtomicBoolean isGroupExisting = new AtomicBoolean(true);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGroupManagerClient().getGroup(group.getName());
            } catch (MyPetCareException e) {
                isGroupExisting.set(false);
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isGroupExisting.get();
    }

    @Override
    public void addSubscriber(User user, Group group) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGroupManagerClient().subscribe(user.getToken(), group.getName(),
                    user.getUsername());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void deleteSubscriber(User user, Group group) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGroupManagerClient().unsubscribe(user.getToken(), group.getName(),
                    user.getUsername());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void createForum(User user, Group group, Forum forum) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ForumData forumData = new ForumData(forum.getName(), forum.getOwnerUsername(), forum.getTags());
                ServiceLocator.getInstance().getForumManagerClient().createForum(group.getName(), forumData);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void deleteForum(User user, Group group, Forum forum) throws ForumNotFoundException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().deleteForum(group.getName(), forum.getName());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void createPost(User user, Forum forum, Post post) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                MessageData messageData = new MessageData(post.getUsername(), post.getText());
                ServiceLocator.getInstance().getForumManagerClient().postMessage(user.getToken(),
                    forum.getGroup().getName(), forum.getName(), messageData);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void deletePost(User user, Forum forum, DateTime postCreationDate) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().deleteMessage(user.getToken(),
                    forum.getGroup().getName(), forum.getName(), user.getUsername(),
                    postCreationDate.toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updatePost(User user, Post post, String newText) {
        ExecutorService executorService = getExecutorServiceForDeletingPost(user, post);
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        executorService = getCreatePostExecutorService(user, post);
        executorService.shutdown();
    }

    @Override
    public void likePost(String likerName, String authorName, DateTime creationDate, String forumName,
                         String groupName) throws PostNotFoundException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        /*executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().likePost(...);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });*/
    }

    @Override
    public void unlikePost(User user, Post post) {
        // Not implemented yet
    }

    /**
     * Create the executor service for deleting post.
     * @param user The user
     * @param post The post
     * @return The executor service for deleting a post
     */
    private ExecutorService getExecutorServiceForDeletingPost(User user, Post post) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().deleteMessage(user.getToken(),
                    post.getForum().getGroup().getName(), post.getForum().getName(), user.getUsername(),
                    post.getForum().getCreationDate().toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        return executorService;
    }

    /**
     * Get the create post executor service.
     * @param user The user
     * @param post The post
     * @return The executor service to create the post
     */
    private ExecutorService getCreatePostExecutorService(User user, Post post) {
        ExecutorService executorService;
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                MessageData messageData = new MessageData(post.getUsername(), post.getText());
                ServiceLocator.getInstance().getForumManagerClient().postMessage(user.getToken(),
                    post.getForum().getGroup().getName(), post.getForum().getName(), messageData);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        return executorService;
    }
}
