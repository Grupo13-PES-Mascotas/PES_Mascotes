package org.pesmypetcare.mypetcare.activities.fragments.walks;

import org.pesmypetcare.mypetcare.features.pets.events.exercise.walk.WalkPets;

import java.util.List;

/**
 * @author Albert Pinto
 */
public interface WalkCommunication {

    /**
     * Get the walking routes.
     * @return The walking routes
     */
    List<WalkPets> getWalkingRoutes();

    /**
     * Changes to my pets fragment.
     */
    void changeToMyPets();

    /**
     * Asks for permissions.
     */
    void askForPermission(String... permissions);
}
