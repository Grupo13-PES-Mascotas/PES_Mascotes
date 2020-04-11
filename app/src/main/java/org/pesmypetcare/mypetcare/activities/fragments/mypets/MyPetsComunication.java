package org.pesmypetcare.mypetcare.activities.fragments.mypets;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

public interface MyPetsComunication {

    /**
     * Method responsible of communicating the user to the fragments.
     * @return The current user
     */
    User getUser();

    void changePetProfileImage(Pet actualPet);
}
