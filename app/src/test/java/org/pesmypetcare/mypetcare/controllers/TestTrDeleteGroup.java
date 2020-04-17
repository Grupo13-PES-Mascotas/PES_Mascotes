package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.GroupNotFoundException;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException;

import static org.junit.Assert.assertTrue;

public class TestTrDeleteGroup {
    private final String groupName = "Elephants";
    private TrDeleteGroup trDeleteGroup;
    private DateTime creationDate;
    @Before
    public void setUp() throws InvalidFormatException {
        trDeleteGroup = new TrDeleteGroup(new StubCommunityService());
    }

    @Test
    public void shouldDeleteGroup() throws GroupNotFoundException {
        trDeleteGroup.setGroupName(groupName);
        trDeleteGroup.execute();
        assertTrue("Should be true", trDeleteGroup.isResult());
    }

    @Test (expected = GroupNotFoundException.class)
    public void shouldThrowException() throws GroupNotFoundException {
        trDeleteGroup.setGroupName(groupName);
        trDeleteGroup.execute();
        trDeleteGroup.execute();
    }
}
