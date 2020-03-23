package org.pesmypetcare.mypetcare.controllers;

public class ControllersFactory {
    private ControllersFactory() {
        // Empty private constructor
    }

    public static TrRegisterNewPet createTrRegisterNewPet() {
        return new TrRegisterNewPet(null);
    }

    public static TrUpdatePetImage createTrUpdatePetImage() {
        return new TrUpdatePetImage(null);
    }
}
