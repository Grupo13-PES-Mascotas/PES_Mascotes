package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.communitymanager.datacontainers.GroupData;
import org.pesmypetcare.httptools.MyPetCareException;
import org.pesmypetcare.mypetcare.features.community.forums.Forum;
import org.pesmypetcare.mypetcare.features.community.forums.ForumNotFoundException;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.community.posts.Post;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class CommunityAdapter implements CommunityService {

    @Override
    public SortedSet<Group> getAllGroups() {
        List<GroupData> groupsData = getAllGroupsFromServer();
        SortedSet<Group> groups = new TreeSet<>();

        for (GroupData groupData : groupsData) {
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

            groups.add(group);
        }

        return groups;
    }

    private List<GroupData> getAllGroupsFromServer() {
        AtomicReference<List<GroupData>> groupsData = new AtomicReference<>();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                groupsData.set(ServiceLocator.getInstance().getGroupManager().getAllGroups());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return groupsData.get();
    }

    @Override
    public void createGroup(User user, Group group) {
        GroupData groupData = new GroupData(group.getName(), group.getOwnerUsername(),
            group.getCreationDate().toString(), group.getDescription(), group.getTags());

        System.out.println("GROUP: " + group.toString());

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getGroupManager().createGroup(groupData);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

        executorService.shutdown();
    }

    @Override
    public void deleteGroup(String groupName) throws GroupNotFoundException {
        // Not implemented yet
    }

    @Override
    public boolean isGroupExisting(Group group) {
        return false;
    }

    @Override
    public void addSubscriber(User user, Group group) {
        // Not implemented yet
    }

    @Override
    public void deleteSubscriber(User user, Group group) {
        //Not implemented yet
    }

    @Override
    public void createForum(User user, Group group, Forum forum) {
        // Not implemented yet
    }

    @Override
    public void deleteForum(User user, Group group, Forum forum) throws ForumNotFoundException {
        // Not implemented yet
    }

    @Override
    public void createPost(User user, Forum forum, Post post) {
        // Not implemented yet
    }

    @Override
    public void deletePost(User user, Forum forum, DateTime postCreationDate) {
        // Not implemented yet
    }

    @Override
    public void updatePost(User user, Post post, String newText) {
        // Not implemented yet
    }
}
