package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.CommunityService;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.List;

public class TrCreateNewGroup {
    private CommunityService communityService;
    private String groupName;
    private User user;
    private String description;
    private DateTime creationDate;
    private List<String> tags;
    private Group result;

    public TrCreateNewGroup(CommunityService communityService) {
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
     * Setter of the description of the group that has to be created.
     * @param description The description of the group that has to be created
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return The group that has been created
     */
    public Group getResult() {
        return result;
    }

    /**
     * Execute the transaction.
     */
    public void execute() throws GroupAlreadyExistingException {
        Group tmp = new Group(groupName, user.getUsername(), creationDate);
        tmp.setDescription(description);
        for (String tag : tags) {
            tmp.addTag(tag);
        }
        communityService.createGroup(user, tmp);
        user.addSubscribedGroup(tmp);
        result = tmp;
    }
}
