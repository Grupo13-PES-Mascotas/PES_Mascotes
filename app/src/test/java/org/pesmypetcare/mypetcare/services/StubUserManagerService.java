package org.pesmypetcare.mypetcare.services;

import android.icu.text.Edits;
import org.pesmypetcare.mypetcare.features.users.User;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class StubUserManagerService implements UserManagerService {
    private Set<User> data;

    public StubUserManagerService() {
        this.data = new HashSet<>();
        User user = new User("johnDoe", "johndoe@gmail.com","123456");
        data.add(user);
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

    @Override
    public void deleteUser(User user) {
        data.remove(user);
    }

    @Override
    public void changeMail(String mail, String username) {
        for (User user : data) {
            if (user.getUsername().equals(username)) {
                user.setMail(mail);
                break;
            }
        }
    }
}
