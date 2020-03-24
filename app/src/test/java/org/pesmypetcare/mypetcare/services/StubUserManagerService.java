package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.users.User;

import java.util.HashSet;
import java.util.Set;

public class StubUserManagerService implements UserManagerService {
    private Set<User> data;

    public StubUserManagerService() {
        this.data = new HashSet<>();
    }
  
    @Override
    public boolean registerNewUser(String username, String email, String password) {
        data.add(new User(username, email, password));
        return true;
    }

    @Override
    public boolean userExists(String username) {
        return data.contains(new User(username, "", ""));
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        data.remove(user);
        data.add(new User(user.getUsername(), user.getMail(), newPassword));
        return true;
    }
}
