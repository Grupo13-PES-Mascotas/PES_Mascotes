package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrChangeMail {
    private UserManagerService userManagerService;
    private User user;
    private String mail;

    public TrChangeMail(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the user to change the mail.
     * @param user The user that wants to change his mail
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the mail that user want.
     * @param newMail The mail that user wants to set
     */
    public void setMail(String newMail) {
        this.mail = newMail;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        user.setEmail(mail);
        userManagerService.changeMail(mail, user);
    }
}
