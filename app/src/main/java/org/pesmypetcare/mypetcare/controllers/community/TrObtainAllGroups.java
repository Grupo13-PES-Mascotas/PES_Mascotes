package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.services.community.CommunityService;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Xavier Campos
 */
public class TrObtainAllGroups {
    private SortedSet<Group> result;
    private CommunityService communityService;

    public TrObtainAllGroups(CommunityService communityService) {
        this.communityService = communityService;
        result = new TreeSet<>();
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
    public SortedSet<Group> getResult() {
        return result;
    }
}
