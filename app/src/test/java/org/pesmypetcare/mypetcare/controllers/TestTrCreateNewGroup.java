package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import static org.junit.Assert.assertTrue;

public class TestTrCreateNewGroup {
    private static final int YEAR = 2020;
    private static final int DAY = 26;
    private static final int HOUR = 15;
    private static final int MINUTES = 23;
    private static final int SECONDS = 56;
    private final String groupName = "Elephants";
    private final String ownerUsername = "johndoe";
    private TrCreateNewGroup trCreateNewGroup;
    private DateTime creationDate;
    @Before
    public void setUp() throws InvalidFormatException {
        trCreateNewGroup = new TrCreateNewGroup(new StubCommunityService());
        creationDate = DateTime.Builder.build(YEAR, 2, DAY, HOUR, MINUTES, SECONDS);
    }

    @Test
    public void shouldCreateNewGroup() throws GroupAlreadyExistingException {
        trCreateNewGroup.setGroupName(groupName);
        trCreateNewGroup.setOwnerUsername(ownerUsername);
        trCreateNewGroup.setCreationDate(creationDate);
        trCreateNewGroup.execute();
        assertTrue("Should be true", trCreateNewGroup.getResult());
    }

    @Test (expected = GroupAlreadyExistingException.class)
    public void shouldThrowException() throws GroupAlreadyExistingException {
        trCreateNewGroup.setGroupName(groupName);
        trCreateNewGroup.setOwnerUsername(ownerUsername);
        trCreateNewGroup.setCreationDate(creationDate);
        trCreateNewGroup.execute();
        trCreateNewGroup.execute();
    }
}
