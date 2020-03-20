package org.pesmypetcare.mypetcare.controllers;

public class ControllersFactory {
    private ControllersFactory() {
        // Empty private constructor
    }

    public static TrRegisterNewPet createStubRegisterNewPet() {
        return new TrRegisterNewPet(null);
    }
}
