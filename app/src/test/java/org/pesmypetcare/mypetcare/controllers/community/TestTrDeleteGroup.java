package org.pesmypetcare.mypetcare.controllers.community;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteGroup;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

import static org.junit.Assert.assertTrue;

/**
 * @author Xavier Campos
 */
public class TestTrDeleteGroup {
    private final String groupName = "Elephants";
    private TrDeleteGroup trDeleteGroup;

    @Before
    public void setUp() {
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
