package org.pesmypetcare.mypetcare.activities.fragments.walks;

import org.pesmypetcare.mypetcare.features.pets.Walk;

import java.util.List;

/**
 * @author Albert Pinto
 */
public interface WalkCommunication {

    /**
     * Get the walking routes.
     * @return The walking routes
     */
    List<Walk> getWalkingRoutes();
}
