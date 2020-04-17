package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.services.CommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

public class TrCreateNewGroup {
    private CommunityService communityService;
    private String groupName;
    private String ownerUsername;
    private DateTime creationDate;
    private Boolean result;

    public TrCreateNewGroup (CommunityService communityService) {
        this.communityService = communityService;
    }

    /**
     * Setter of the name of the group that has to be created.
     * @param groupName The name of the group that has to be created
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * Setter of the username of the owner of the group that has to be created.
     * @param ownerUsername The username of the owner of the group that has to be created
     */
    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    /**
     * Setter of the creation date of the group that has to be created.
     * @param creationDate The creation date of the group that has to be created
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Getter of the result of the transaction.
     * @return True if the group was created successfully or false otherwise
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = false;
        try {
            communityService.createGroup(groupName, ownerUsername, creationDate);
        } catch (GroupAlreadyExistingException e) {
            e.printStackTrace();
        }
        result = true;
    }
}
