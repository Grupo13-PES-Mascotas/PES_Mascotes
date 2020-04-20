package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.community.Group;
import org.pesmypetcare.mypetcare.features.community.GroupNotExistingException;
import org.pesmypetcare.mypetcare.features.community.NotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.OwnerCannotDeleteSubscriptionException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Xavier Campos
 */
public class TestTrDeleteSubscription {
    private User user;
    private Group group;
    private TrDeleteSubscription trDeleteSubscription;

    @Before
    public void setUp() {
        user = new User("John Smith", "johnSmith@gmail.com", "1234");
        group = new Group("Elephants", "Enric", DateTime.Builder.buildDateString("2020-04-15"));
        trDeleteSubscription = new TrDeleteSubscription(new StubCommunityService());
    }

    @Test(expected = GroupNotExistingException.class)
    public void shouldNotDeleteSubscriptionFromNonExistingGroup() throws GroupNotExistingException, NotSubscribedException,
        OwnerCannotDeleteSubscriptionException {
        trDeleteSubscription.setUser(user);
        trDeleteSubscription.setGroup(new Group("Penguins", "Oriol Simo",
            DateTime.Builder.buildDateString("2020-04-15")));
        trDeleteSubscription.execute();
    }

    @Test(expected = NotSubscribedException.class)
    public void shouldNotDeleteSubscriptionFromNotSubscribedGroup() throws GroupNotExistingException, NotSubscribedException,
        OwnerCannotDeleteSubscriptionException {
        trDeleteSubscription.setUser(user);
        trDeleteSubscription.setGroup(group);
        trDeleteSubscription.execute();
    }

    @Test(expected = OwnerCannotDeleteSubscriptionException.class)
    public void shouldNotDeleteSubscriptionFromOwnedGroup() throws GroupNotExistingException, NotSubscribedException,
        OwnerCannotDeleteSubscriptionException {
        trDeleteSubscription.setUser(new User("Enric", "enric@gmail.com", "1234"));
        trDeleteSubscription.setGroup(group);
        trDeleteSubscription.execute();
    }

    @Test
    public void shouldDeleteSubscriberFromGroup() throws NotSubscribedException, OwnerCannotDeleteSubscriptionException,
        GroupNotExistingException {
        user.addSubscribedGroup(group);
        trDeleteSubscription.setUser(user);
        trDeleteSubscription.setGroup(group);
        trDeleteSubscription.execute();
        assertEquals("Should only be owner", "[Enric]", group.getSubscribers().keySet().toString());
    }

}
