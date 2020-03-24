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

    public static TrChangePassword createTrChangePassword() {
        return new TrChangePassword(null);
    }

    public static TrDeleteUser createTrDeleteUser() { return new TrDeleteUser(null,
            null); }

    public static TrDeletePet createTrDeletePet() { return new TrDeletePet(null); }

    public static TrRegisterNewUser createTrRegisterNewUser() { return new TrRegisterNewUser(null); }
}
