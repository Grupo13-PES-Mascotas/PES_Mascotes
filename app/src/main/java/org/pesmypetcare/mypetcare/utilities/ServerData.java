package org.pesmypetcare.mypetcare.utilities;

import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.mypetcare.features.community.groups.Group;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.List;

/**
 * @author Albert Pinto
 */
public class ServerData {
    private static ServerData instance;
    private FirebaseAuth mAuth;
    private User user;
    private List<Group> groups;

    private ServerData() {
        // Private constructor
    }

    public static ServerData getInstance() {
        if (instance == null) {
            instance = new ServerData();
        }

        return instance;
    }

    public FirebaseAuth getMAuth() {
        return mAuth;
    }

    public void setMAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
