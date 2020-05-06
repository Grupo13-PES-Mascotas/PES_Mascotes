package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.community.TrDeleteSubscription;
import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.community.groups.GroupNotExistingException;
import org.pesmypetcare.mypetcare.features.community.groups.NotSubscribedException;
import org.pesmypetcare.mypetcare.features.community.groups.OwnerCannotDeleteSubscriptionException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.usermanagerliblib.datacontainers.DateTime;

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
        group = new Group("Husky", "John Doe", DateTime.Builder.buildDateString("2020-04-15"));
        trDeleteSubscription = new TrDeleteSubscription(new StubCommunityService());
    }

    @Test(expected = GroupNotExistingException.class)
    public void shouldNotDeleteSubscriptionFromNonExistingGroup() throws GroupNotExistingException,
        NotSubscribedException, OwnerCannotDeleteSubscriptionException {
        trDeleteSubscription.setUser(user);
        trDeleteSubscription.setGroup(new Group("Penguins", "Oriol Simo",
            DateTime.Builder.buildDateString("2020-04-15")));
        trDeleteSubscription.execute();
    }

    @Test(expected = NotSubscribedException.class)
    public void shouldNotDeleteSubscriptionFromNotSubscribedGroup() throws GroupNotExistingException,
        NotSubscribedException, OwnerCannotDeleteSubscriptionException {
        trDeleteSubscription.setUser(user);
        trDeleteSubscription.setGroup(group);
        trDeleteSubscription.execute();
    }

    @Test(expected = OwnerCannotDeleteSubscriptionException.class)
    public void shouldNotDeleteSubscriptionFromOwnedGroup() throws GroupNotExistingException, NotSubscribedException,
        OwnerCannotDeleteSubscriptionException {
        trDeleteSubscription.setUser(new User("John Doe", "johnDoe@gmail.com", "1234"));
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
        assertEquals("Should only be owner", "[John Doe]", group.getSubscribers().keySet().toString());
    }

}
