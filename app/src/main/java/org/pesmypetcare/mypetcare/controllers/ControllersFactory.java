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

    public static TrObtainAllPetImages createTrObtainAllPetImages() {
        return new TrObtainAllPetImages(new PetManagerAdapter());
    }

    public static TrUpdateUserImage createTrUpdateUserImage() {
        return new TrUpdateUserImage(new UserManagerAdapter());
    }

    public static TrDeletePersonalEvent createTrDeletePersonalEvent() {
        return null;
        //return new TrDeletePersonalEvent(new PetManagerAdapter());
    }

    public static TrNewPersonalEvent createTrNewPersonalEvent() {
        return null;
        //return new TrNewPersonalEvent(new PetManagerAdapter());
    }

    public static TrNewPeriodicNotification createTrNewPeriodicNotification() {
        return new TrNewPeriodicNotification(new PetManagerAdapter());
    }
}

