package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.services.PetManagerAdapter;
import org.pesmypetcare.mypetcare.services.UserManagerAdapter;

public class ControllersFactory {
    private ControllersFactory() {
        // Empty private constructor
    }

    public static TrRegisterNewPet createTrRegisterNewPet() {
        return new TrRegisterNewPet(new PetManagerAdapter());
    }

    public static TrUpdatePetImage createTrUpdatePetImage() {
        return new TrUpdatePetImage(new PetManagerAdapter());
    }

    public static TrChangePassword createTrChangePassword() {
        return new TrChangePassword(new UserManagerAdapter());
    }

    public static TrDeleteUser createTrDeleteUser() {
        return new TrDeleteUser(new UserManagerAdapter(), new PetManagerAdapter());
    }

    public static TrDeletePet createTrDeletePet() {
        return new TrDeletePet(new PetManagerAdapter());
    }

    public static TrObtainUser createTrObtainUser() {
        return new TrObtainUser(new UserManagerAdapter(), new PetManagerAdapter());
    }

    public static TrUpdatePet createTrUpdatePet() {
        return new TrUpdatePet(new PetManagerAdapter());
    }

    public static TrChangeMail createTrChangeMail() {
        return new TrChangeMail(new UserManagerAdapter());
    }
}
