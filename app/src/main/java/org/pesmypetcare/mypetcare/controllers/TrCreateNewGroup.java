package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.List;

public class TrCreateNewGroup {
    private CommunityService communityService;
    private String groupName;
    private User user;
    private DateTime creationDate;
    private List<String> tags;
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
     * Setter of the owner of the group that has to be created.
     * @param user The owner of the group that has to be created
     */
    public void setOwner(User user) {
        this.user = user;
    }

    /**
     * Setter of the creation date of the group that has to be created.
     * @param creationDate The creation date of the group that has to be created
     */
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Setter of the tags of the group that has to be created.
     * @param tags The tags of the group that has to be created
     */
    public void setTags(List<String> tags) {
        this.tags = tags;
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
    public void execute() throws GroupAlreadyExistingException {
        result = false;
        Group tmp = new Group(groupName, user.getUsername(), creationDate);
        for (String tag : tags) {
            tmp.addTag(tag);
        }
        communityService.createGroup(user, tmp);
        result = true;
    }
}
