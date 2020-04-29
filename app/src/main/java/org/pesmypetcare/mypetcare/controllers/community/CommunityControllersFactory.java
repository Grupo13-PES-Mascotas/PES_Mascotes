package org.pesmypetcare.mypetcare.controllers.community;

import org.pesmypetcare.mypetcare.controllers.TrUnlikePost;
import org.pesmypetcare.mypetcare.services.CommunityAdapter;
import org.pesmypetcare.mypetcare.services.StubCommunityService;

/**
 * @author Albert Pinto
 */
public class CommunityControllersFactory {
    private CommunityControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for obtaining all the groups.
     * @return The transaction for obtaining all the groups
     */
    public static TrObtainAllGroups createTrObtainAllGroups() {
        return new TrObtainAllGroups(new CommunityAdapter());
    }

    /**
     * Create the transaction for creating a new group.
     * @return The transaction for creating a new group
     */
    public static TrCreateNewGroup createTrCreateNewGroup() {
        return new TrCreateNewGroup(new CommunityAdapter());
    }

    /**
     * Create the transaction for deleting a group.
     * @return The transaction for deleting a group
     */
    public static TrDeleteGroup createTrDeleteGroup() {
        return new TrDeleteGroup(new CommunityAdapter());
    }

    /**
     * Create the transaction for adding a subscription.
     * @return The transaction for adding a subscription
     */
    public static TrAddSubscription createTrAddSubscription() {
        return new TrAddSubscription(new CommunityAdapter());
    }

    /**
     * Create the transaction for deleting a subscription.
     * @return The transaction for deleting a subscription
     */
    public static TrDeleteSubscription createTrDeleteSubscription() {
        return new TrDeleteSubscription(new CommunityAdapter());
    }

    /**
     * Create the transaction for adding a new forum.
     * @return The transaction for adding a new forum
     */
    public static TrAddNewForum createTrAddNewForum() {
        return new TrAddNewForum(new CommunityAdapter());
    }

    /**
     * Create the transaction for deleting a forum.
     * @return The transaction for deleting a forum
     */
    public static TrDeleteForum createTrDeleteForum() {
        return new TrDeleteForum(new CommunityAdapter());
    }

    /**
     * Create the transaction for adding a new post.
     * @return The transaction for adding a new post
     */
    public static TrAddNewPost createTrAddNewPost() {
        return new TrAddNewPost(new CommunityAdapter());
    }

    /**
     * Create the transaction for deleting a new post.
     * @return The transaction for deleting a new post
     */
    public static TrDeletePost createTrDeletePost() {
        return new TrDeletePost(new CommunityAdapter());
    }

    /**
     * Create the transaction for updating a post.
     * @return The transaction for updating a post
     */
    public static TrUpdatePost createTrUpdatePost() {
        return new TrUpdatePost(new CommunityAdapter());
    }

    public static TrLikePost createTrLikePost() {
        return new TrLikePost(new StubCommunityService());
    }

    /**
     * Create the transaction to unlike a post.
     * @return The transaction to unlike a post
     */
    public static TrUnlikePost createTrUnlikePost() {
        return new TrUnlikePost(new StubCommunityService());
    }
}
