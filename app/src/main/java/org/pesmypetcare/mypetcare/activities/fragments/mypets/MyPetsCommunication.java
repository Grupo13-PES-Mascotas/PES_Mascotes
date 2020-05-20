package org.pesmypetcare.mypetcare.activities.fragments.mypets;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;

/**
 * @author Xavier Campos
 */
public interface MyPetsCommunication {

    /**
     * Method responsible of communicating the user to the fragments.
     * @return The current user
     */
    User getUser();

    /**
     * Method responsible of communication the new pet profile image.
     * @param actualPet The actual pet
     */
    void changePetProfileImage(Pet actualPet);
}
