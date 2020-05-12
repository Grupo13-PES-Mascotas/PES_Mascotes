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

    /**
     * Create the transaction for deleting an exercise.
     * @return The transaction for deleting an exercise
     */
    public static TrDeleteExercise createTrDeleteExercise() {
        return new TrDeleteExercise(new PetManagerAdapter());
    }

    /**
     * Create the transaction for updating an exercise.
     * @return The transaction for updating an exercise
     */
    public static TrUpdateExercise createTrUpdateExercise() {
        return new TrUpdateExercise(new PetManagerAdapter());
    }

    /**
     * Create the transaction for adding a walking.
     * @return The transaction for adding a walking
     */
    public static TrAddWalk createTrAddWalk() {
        return new TrAddWalk(new PetManagerAdapter());
    }

    /**
     * Create the transaction for getting all the walks.
     * @return The transaction for getting all the walks
     */
    public static TrGetAllWalks createTrGetAllWalks() {
        return new TrGetAllWalks();
    }
}
