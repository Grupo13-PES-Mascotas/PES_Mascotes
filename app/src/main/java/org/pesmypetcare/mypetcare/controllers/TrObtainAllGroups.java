package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.services.CommunityService;

import java.util.ArrayList;
import java.util.List;

public class TrObtainAllGroups {
    private List<Group> result;
    private CommunityService communityService;

    public TrObtainAllGroups(CommunityService communityService) {
        this.communityService = communityService;
        result = new ArrayList<>();
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = communityService.getAllGroups();
    }

    /**
     * Obtain the result of the transaction.
     * @return A list that contains all the groups of the system
     */
    public List<Group> getResult() {
        return result;
    }
}
