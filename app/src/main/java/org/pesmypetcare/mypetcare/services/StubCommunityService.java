package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.GroupNotFoundException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

public class StubCommunityService implements CommunityService {
    private static final int HUSKY = 0;
    private static final int TURTLES = 1;
    private static final int ELEPHANTS = 2;
    private static final int DINOSAURS = 3;

    private static List<Group> groups;

    static {
        StubCommunityService.groups = new ArrayList<>();
        StubCommunityService.groups.add(new Group("Husky", "John Doe",
            DateTime.Builder.buildDateString("2020-04-15")));
        StubCommunityService.groups.add(new Group("Turtles", "John Doe",
            DateTime.Builder.buildDateString("2020-04-16")));
        StubCommunityService.groups.add(new Group("Elephants", "Enric",
            DateTime.Builder.buildDateString("2020-04-14")));
        StubCommunityService.groups.add(new Group("Dinosaur", "Gradle",
            DateTime.Builder.buildDateString("2019-11-23")));

        StubCommunityService.groups.get(HUSKY).addTag("dog");
        StubCommunityService.groups.get(HUSKY).addTag("domestic");
        StubCommunityService.groups.get(TURTLES).addTag("turtle");
        StubCommunityService.groups.get(TURTLES).addTag("wild");
        StubCommunityService.groups.get(ELEPHANTS).addTag("savanna");
        StubCommunityService.groups.get(ELEPHANTS).addTag("Africa");
        StubCommunityService.groups.get(DINOSAURS).addTag("extinct");
    }

    @Override
    public List<Group> getAllGroups() {
        return groups;
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
}