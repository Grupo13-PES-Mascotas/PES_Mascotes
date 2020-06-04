package org.pesmypetcare.mypetcare.services.community;

import android.graphics.Bitmap;

import org.pesmypetcare.communitymanager.datacontainers.ForumData;
import org.pesmypetcare.communitymanager.datacontainers.GroupData;
import org.pesmypetcare.communitymanager.datacontainers.MessageDisplay;
import org.pesmypetcare.communitymanager.datacontainers.MessageSendData;
import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.community.posts.PostNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.mypetcare.utilities.ImageManager;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Albert Pinto
 */
public class CommunityAdapter implements CommunityService {
    private static final int TIME = 20;
    private static final int TIMEOUT = 2;
    private byte[] imageBytes;

    @Override
    public SortedSet<Group> getAllGroups() {
        List<GroupData> groupsData = getAllGroupsFromServer();
        Group[] groupArray = new Group[groupsData.size()];

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int actual = 0; actual < groupsData.size(); ++actual) {
            int finalActual = actual;
            executorService.execute(() -> createActualGroup(groupsData, groupArray, finalActual));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(TIMEOUT, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new TreeSet<>(Arrays.asList(groupArray));
    }

    /**
     * Create the actual group.
     * @param groupsData The groups from the server
     * @param groupArray The actual groups created
     * @param actual The actual group
     */
    private void createActualGroup(List<GroupData> groupsData, Group[] groupArray, int actual) {
        Group group = createGroup(groupsData.get(actual));
        addForums(group);
        groupArray[actual] = group;
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
        GroupData groupData;

        if (!"".equals(group.getDescription()) && group.getTags() != null) {
            groupData = new GroupData(group.getName(), group.getOwnerUsername(), group.getDescription(),
                group.getTags());
        } else if (!"".equals(group.getDescription())) {
            groupData = new GroupData(group.getName(), group.getOwnerUsername(), group.getTags());
        } else {
            groupData = new GroupData(group.getName(), group.getOwnerUsername(), group.getDescription());
        }

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
                MessageSendData message;
                boolean isImage = false;

                if (!"".equals(post.getText()) && post.getPostImage() != null) {
                    byte[] imageBytes = ImageManager.getImageBytes(post.getPostImage());
                    message = new MessageSendData(user.getUsername(), post.getText(), imageBytes);
                    isImage = true;
                } else if (!"".equals(post.getText())) {
                    message = new MessageSendData(user.getUsername(), post.getText());
                } else {
                    byte[] imageBytes = ImageManager.getImageBytes(post.getPostImage());
                    message = new MessageSendData(user.getUsername(), imageBytes);
                    isImage = true;
                }

                if (isImage) {
                    ExecutorService imageExecutorService = Executors.newSingleThreadExecutor();
                    imageExecutorService.execute(() -> {
                        String fileName = user.getUsername() + "_" + post.getCreationDate().toString() + "_"
                            + post.getForum().getName() + "_" + post.getForum().getGroup().getName();
                        byte[] imageBytes = ImageManager.getImageBytes(post.getPostImage());
                        ImageManager.writeImage(ImageManager.POST_IMAGES_PATH, fileName, imageBytes);
                    });

                    imageExecutorService.shutdown();
                }

                ServiceLocator.getInstance().getForumManagerClient().postMessage(user.getToken(),
                    forum.getGroup().getName(), forum.getName(), message);
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
    public void likePost(User user, Post post) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().likeMessage(user.getToken(), user.getUsername(),
                    post.getForum().getGroup().getName(), post.getForum().getName(), post.getUsername(),
                    post.getCreationDate().toString(), true);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void unlikePost(User user, Post post) {
        System.out.println("user token " + user.getToken());
        System.out.println("username " + user.getUsername());
        System.out.println("post group name " + post.getForum().getGroup().getName());
        System.out.println("post forum name" + post.getForum().getName());
        System.out.println("post author " + post.getUsername());
        System.out.println("post creation date" + post.getCreationDate());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().likeMessage(user.getToken(), user.getUsername(),
                    post.getForum().getGroup().getName(), post.getForum().getName(), post.getUsername(),
                    post.getCreationDate().toString(), false);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void reportPost(User user, Post post, String reportMessage) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().reportMessage(user.getToken(),
                        post.getForum().getGroup().getName(), post.getForum().getName(), post.getUsername(), user.getUsername(),
                        post.getCreationDate().toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void addPostImage(User user, Post post, Bitmap image) throws PostNotFoundException {
        // This method has been replaced
    }

    @Override
    public void deletePostImage(User user, Post post) throws PostNotFoundException {
        // This method has been replaced
    }

    @Override
    public void addGroupImage(User user, Group group, Bitmap image) throws MyPetCareException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            byte[] imageBytes = ImageManager.getImageBytes(image);
            try {
                ServiceLocator.getInstance().getGroupManagerClient().updateGroupIcon(user.getToken(), group.getName(),
                    imageBytes);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void deleteGroupImage(User user, Group group) throws GroupNotFoundException {
        // Not implemented yet
    }

    @Override
    public byte[] getPostImage(User user, Post post, MessageDisplay messageDisplay) {
        /*String fileName = post.getUsername() + "_" + post.getCreationDate().toString() + "_"
            + post.getForum().getName() + "_" + post.getForum().getGroup().getName();

        try {
            imageBytes = ImageManager.readImage(ImageManager.POST_IMAGES_PATH, fileName);
        } catch (IOException e) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    Map<String, byte[]> forumImages = ServiceLocator.getInstance().getForumManagerClient()
                        .getAllPostsImagesFromForum(user.getToken(), post.getForum().getGroup().getName(),
                            post.getForum().getName());
                    imageBytes = forumImages.get(messageDisplay.getImagePath());
                } catch (MyPetCareException ex) {
                    ex.printStackTrace();
                }
            });

            executorService.shutdown();

            try {
                executorService.awaitTermination(2, TimeUnit.MINUTES);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }*/

        return new byte[0];
    }

    @Override
    public byte[] getGroupImage(User user, Group group) {
        imageBytes = new byte[0];
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                imageBytes = ServiceLocator.getInstance().getGroupManagerClient().getGroupIcon(group.getName());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();

        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return imageBytes;
    }

    @Override
    public void unbanPost(User user, Post post) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getForumManagerClient().unbanMessage(user.getToken(),
                        post.getForum().getGroup().getName(), post.getForum().getName(), post.getUsername(),
                        post.getCreationDate().toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
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
                MessageSendData message = new MessageSendData(post.getUsername(), post.getText());
                ServiceLocator.getInstance().getForumManagerClient().postMessage(user.getToken(),
                    post.getForum().getGroup().getName(), post.getForum().getName(), message);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        return executorService;
    }
}
