package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.users.SamePasswordException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.features.users.notValidPasswordException;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrChangePassword {
    private static final int MIN_PASS_LENTGH = 6;
    private UserManagerService userManagerService;
    private User user;
    private String newPassword;
    private boolean result;

    public TrChangePassword(UserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    /**
     * Set the user that will change the password.
     * @param user The user of the change
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Set the new password.
     * @param newPassword The new password
     * @exception SamePasswordException The user has already the same password
     * @exception notValidPasswordException The new password isn't valid
     */
    public void setNewPassword(String newPassword) throws SamePasswordException, notValidPasswordException {
        if (newPassword.equals(this.user.getPasswd())) {
            throw new SamePasswordException();
        } else if (!validatePassword(newPassword)) {
            throw new notValidPasswordException();
        }
        this.newPassword = newPassword;
    }

    /**
     * Method responsible of checking if the password change is correct.
     * @return True if the sign up was successful or false otherwise
     */
    private boolean validatePassword(String password) {
        return !(password.length() < MIN_PASS_LENTGH || weakPass(password));
    }

    /**
     * Method responsible for checking whether a password is weak or not.
     * @return True if the password is weak or false otherwise
     */
    private boolean weakPass(String pass) {
        boolean uppercase = containsUppercase(pass);
        boolean lowercase = containsLowercase(pass);
        boolean number = containsNumber(pass);
        boolean specialChar = containsSpecialChar(pass);
        return !(uppercase && lowercase && number && specialChar);
    }

    /**
     * Method responsible for checking if the password contains an uppercase character.
     * @param pass The password
     * @return True if the password contains an uppercase letter or false otherwise
     */
    private boolean containsUppercase(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isUpperCase(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for checking if the password contains an lowercase character.
     * @param pass The password
     * @return True if the password contains an lowercase letter or false otherwise
     */
    private boolean containsLowercase(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isLowerCase(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for checking if the password contains a number.
     * @param pass The password
     * @return True if the password contains a number or false otherwise
     */
    private boolean containsNumber(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isDigit(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for checking if the password contains an special character.
     * @param pass The password
     * @return True if the password contains an special character or false otherwise
     */
    private boolean containsSpecialChar(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (String.valueOf(pass.charAt(i)).matches("[^a-zA-Z0-9]")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Execute the transaction.
     */
    public void execute() {
        result = false;
        userManagerService.changePassword(this.user, this.newPassword);
        result = true;
    }

    /**
     * Get the result of the transaction.
     * @return The result of the transaction
     */
    public boolean isResult() {
        return this.result;
    }
}
