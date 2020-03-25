package org.pesmypetcare.mypetcare.activities.communication;

import android.os.Bundle;

public interface RegisterPetCommunication {
    /**
     * Passes the information to add a pet to the main activity.
     * @param petInfo Information about a pet to pass
     */
    void addNewPet(Bundle petInfo);
}
