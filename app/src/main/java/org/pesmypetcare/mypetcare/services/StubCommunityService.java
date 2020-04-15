package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.List;

public class StubCommunityService implements CommunityService {
    private List<Group> groups;

    public StubCommunityService() {
        this.groups = new ArrayList<>();
        this.groups.add(new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15")));
        this.groups.add(new Group("Turtles", "John Doe", DateTime.Builder.buildDateString("2020-04-15")));
        this.groups.add(new Group("Elephants", "Enric", DateTime.Builder.buildDateString("2020-04-14")));
        this.groups.add(new Group("Dinosaur", "Gradle", DateTime.Builder.buildDateString("2019-11-23")));
    }

    @Override
    public List<Group> getAllGroups() {
        return groups;
    }
}
