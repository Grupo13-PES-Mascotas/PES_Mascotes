package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.services.PetManagerAdapter;

/**
 * @author Albert Pinto
 */
public class ExerciseControllersFactory {
    private ExerciseControllersFactory() {
        // Private constructor
    }

    /**
     * Create the transaction for adding a new exercise.
     * @return The transaction for adding a new exercise
     */
    public static TrAddExercise createTrAddExercise() {
        return new TrAddExercise(new PetManagerAdapter());
    }
}
