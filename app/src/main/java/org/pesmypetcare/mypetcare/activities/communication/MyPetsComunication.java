package org.pesmypetcare.mypetcare.activities.communication;

import org.pesmypetcare.mypetcare.features.users.User;

public interface MyPetsComunication {

    /**
     * Method responsible of communicating the user to the fragments.
     * @return The current user
     */
    User getUser();
}
