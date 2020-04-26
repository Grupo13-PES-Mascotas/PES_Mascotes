package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.features.community.groups.GroupNotFoundException;
import org.pesmypetcare.mypetcare.services.CommunityService;

public class TrDeleteGroup {
    private CommunityService communityService;
    private String groupName;
    private boolean result;

    public TrDeleteGroup(CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the name of the group that has to be deleted.
     * @param groupName The name of the group that has to be deleted
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the group was deleted successfully or false otherwise
     */
    public boolean isResult() {
        return result;
    }

    /**
     * Executes the transaction.
     * @throws GroupNotFoundException Exception thrown if the group to be deleted is not found
     */
    public void execute() throws GroupNotFoundException {
        result = false;
        communityService.deleteGroup(groupName);
        result = true;
    }
}
