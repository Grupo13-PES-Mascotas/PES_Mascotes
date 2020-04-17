package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.community.GroupNotFoundException;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

public class StubCommunityService implements CommunityService {
    private static final int HUSKY = 0;
    private static final int TURTLES = 1;
    private static final int ELEPHANTS = 2;
    private static final int DINOSAURS = 3;

    private List<Group> groups;

    public StubCommunityService() {
        this.groups = new ArrayList<>();
        this.groups.add(new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15")));
        this.groups.add(new Group("Turtles", "John Doe", DateTime.Builder.buildDateString("2020-04-15")));
        this.groups.add(new Group("Elephants", "Enric", DateTime.Builder.buildDateString("2020-04-14")));
        this.groups.add(new Group("Dinosaur", "Gradle", DateTime.Builder.buildDateString("2019-11-23")));

        this.groups.get(HUSKY).addTag("dog");
        this.groups.get(HUSKY).addTag("domestic");
        this.groups.get(TURTLES).addTag("turtle");
        this.groups.get(TURTLES).addTag("wild");
        this.groups.get(ELEPHANTS).addTag("savanna");
        this.groups.get(ELEPHANTS).addTag("Africa");
        this.groups.get(DINOSAURS).addTag("extinct");
    }

    @Override
    public List<Group> getAllGroups() {
        return groups;
    }

    @Override
    public void createGroup(String groupName, String ownerUsername, DateTime creationDate) throws GroupAlreadyExistingException {
        Group tmp = new Group(groupName, ownerUsername, creationDate);
        for (int i = 0; i < groups.size(); ++i) {
            if (tmp.equals(groups.get(i))) {
                throw new GroupAlreadyExistingException();
            }
        }
        groups.add(tmp);
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
