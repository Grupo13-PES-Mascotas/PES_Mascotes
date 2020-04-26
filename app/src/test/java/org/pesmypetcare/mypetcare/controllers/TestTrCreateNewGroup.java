package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.community.TrCreateNewGroup;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;
import org.pesmypetcare.usermanager.exceptions.InvalidFormatException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestTrCreateNewGroup {
    private static final int YEAR = 2020;
    private static final int DAY = 26;
    private static final int HOUR = 15;
    private static final int MINUTES = 23;
    private static final int SECONDS = 56;
    private final String groupName = "Octopus";
    private User owner;
    private TrCreateNewGroup trCreateNewGroup;
    private DateTime creationDate;
    private String description;
    private List<String> tags;

    @Before
    public void setUp() throws InvalidFormatException {
        trCreateNewGroup = new TrCreateNewGroup(new StubCommunityService());
        creationDate = DateTime.Builder.build(YEAR, 2, DAY, HOUR, MINUTES, SECONDS);
        owner = new User("johndoe", "johndoe@gmail.com", "1234");
        description = "Description";
        tags = new ArrayList<>();
        tags.add(groupName);
        tags.add("Sea");
    }

    @Test
    public void shouldCreateNewGroup() throws GroupAlreadyExistingException {
        trCreateNewGroup.setGroupName(groupName);
        trCreateNewGroup.setOwner(owner);
        trCreateNewGroup.setDescription(description);
        trCreateNewGroup.setCreationDate(creationDate);
        trCreateNewGroup.setTags(tags);
        trCreateNewGroup.execute();
        assertNotNull("Should be true", trCreateNewGroup.getResult());
    }

    @Test (expected = GroupAlreadyExistingException.class)
    public void shouldThrowException() throws GroupAlreadyExistingException {
        trCreateNewGroup.setGroupName(groupName);
        trCreateNewGroup.setOwner(owner);
        trCreateNewGroup.setDescription(description);
        trCreateNewGroup.setCreationDate(creationDate);
        trCreateNewGroup.setTags(tags);
        trCreateNewGroup.execute();
        trCreateNewGroup.execute();
    }

    @Test
    public void shouldOwnerBeSubscriber() throws GroupAlreadyExistingException {
        trCreateNewGroup.setGroupName(groupName + "2");
        trCreateNewGroup.setOwner(owner);
        trCreateNewGroup.setDescription(description);
        trCreateNewGroup.setCreationDate(creationDate);
        trCreateNewGroup.setTags(tags);
        trCreateNewGroup.execute();

        assertEquals("Should owner be subscriber", "[johndoe]",
            trCreateNewGroup.getResult().getSubscribers().keySet().toString());
    }
}
