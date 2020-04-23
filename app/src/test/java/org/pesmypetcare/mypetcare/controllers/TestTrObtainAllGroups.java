package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

import static org.junit.Assert.assertEquals;

public class TestTrObtainAllGroups {
    private TrObtainAllGroups trObtainAllGroups;
    private StubCommunityService communityService;

    @Before
    public void setUp() {
        communityService = new StubCommunityService();
        trObtainAllGroups = new TrObtainAllGroups(communityService);
    }

    @Test
    public void shouldReturnAllGroups() {
        trObtainAllGroups.execute();
        assertEquals("Should be equal", trObtainAllGroups.getResult(), communityService.getAllGroups());
    }
}
