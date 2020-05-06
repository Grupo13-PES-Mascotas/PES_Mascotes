package org.pesmypetcare.mypetcare.controllers.exercise;

import org.pesmypetcare.mypetcare.controllers.infopet.TrDeleteExercise;
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

    /**
     * Create the transaction for deleting an exercise.
     * @return The transaction for deleting an exercise
     */
    public static TrDeleteExercise createTrDeleteExercise() {
        return new TrDeleteExercise(new PetManagerAdapter());
    }
}
