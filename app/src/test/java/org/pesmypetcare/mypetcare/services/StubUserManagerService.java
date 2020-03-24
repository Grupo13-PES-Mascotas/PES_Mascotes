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
    public void changeMail(String mail, String username) {
        Iterator it = data.iterator();
        User user = null;
        boolean finded = false;
        while(!finded && it.hasNext()) {
            if (((User) it.next()).getUsername().equals(username)) {
                finded = true;
                user = (User) it.next();
            }
        }
        user.setMail(mail);
        data.add(user);
    }
}
