package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.users.User;

import java.util.HashSet;
import java.util.Set;

public class StubUserManagerService implements UserManagerService {
    private Set<User> data;

    public StubUserManagerService() {
        this.data = new HashSet<>();
        User user = new User("johnDoe", "johndoe@gmail.com","123456");
        data.add(user);
    }
  
    @Override
    public User findUserByUsername(String username) {
        for (User user : data) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public boolean userExists(User user) {
        return data.contains(new User(user.getUsername(), "", ""));
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        data.remove(user);
        data.add(new User(user.getUsername(), user.getEmail(), newPassword));
        return true;
    }

    @Override
    public void deleteUser(User user) {
        data.remove(user);
    }

    @Override
    public void changeMail(String mail, User user) {
        for (User nextUser : data) {
            if (nextUser.getUsername().equals(user.getUsername())) {
                nextUser.setEmail(mail);
                break;
            }
        }
    }

    @Override
    public void createUser(String uid, String email, String password) {
        data.add(new User(uid, email, password));
    }
}
