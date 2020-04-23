package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.services.MealManagerAdapter;
import org.pesmypetcare.mypetcare.services.PetManagerAdapter;
import org.pesmypetcare.mypetcare.services.StubCommunityService;
import org.pesmypetcare.mypetcare.services.StubMedicationService;
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
        return new TrDeleteUser(new UserManagerAdapter(), new PetManagerAdapter(), new MealManagerAdapter());
    }

    public static TrDeletePet createTrDeletePet() {
        return new TrDeletePet(new PetManagerAdapter(), new MealManagerAdapter());
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

    public static TrChangeUsername createTrChangeUsername() {
        return new TrChangeUsername(new UserManagerAdapter());
    }

    public static TrExistsUsername createTrExistsUsername() {
        return new TrExistsUsername(new UserManagerAdapter());
    }
  
    public static TrAddNewWeight createTrAddNewWeight() {
        return new TrAddNewWeight(new PetManagerAdapter());
    }

    public static TrDeleteWeight createTrDeleteWeight() {
        return new TrDeleteWeight(new PetManagerAdapter());
    }

    public static TrAddNewWashFrequency createTrAddNewWashFrequency() {
        return new TrAddNewWashFrequency(new PetManagerAdapter());
    }

    public static TrDeleteWashFrequency createTrDeleteWashFrequency() {
        return new TrDeleteWashFrequency(new PetManagerAdapter());
    }
  
    public static TrNewPetMeal createTrNewPetMeal() {
        return new TrNewPetMeal(new MealManagerAdapter());
    }

    public static TrObtainAllPetMeals createTrObtainAllPetMeals() {
        return new TrObtainAllPetMeals(new MealManagerAdapter());
    }

    public static TrDeleteMeal createTrDeleteMeal() {
        return new TrDeleteMeal(new MealManagerAdapter());
    }

    public static TrUpdateMeal createTrUpdateMeal() {
        return new TrUpdateMeal(new MealManagerAdapter());
    }

    public static TrObtainAllGroups createTrObtainAllGroups() {
        return new TrObtainAllGroups(new StubCommunityService());
    }

    public static TrCreateNewGroup createTrObtainNewGroup() {
        return new TrCreateNewGroup(new StubCommunityService());
    }

    public static TrDeleteGroup createTrDeleteGroup() {
        return new TrDeleteGroup(new StubCommunityService());
    }

    public static TrAddSubscription createTrAddSubscription() {
        return new TrAddSubscription(new StubCommunityService());
    }

    public static TrDeleteSubscription createTrDeleteSubscription() {
        return new TrDeleteSubscription(new StubCommunityService());
    }

    public static TrNewPetMedication createTrNewPetMedication() {
        return new TrNewPetMedication(new StubMedicationService());
    }

    public static TrObtainAllPetMedications createTrObtainAllPetMedications() {
        return new TrObtainAllPetMedications(new StubMedicationService());
    }

    public static TrDeleteMedication createTrDeleteMedication() {
        return new TrDeleteMedication(new StubMedicationService());
    }

    public static TrUpdateMedication createTrUpdateMedication() {
        return new TrUpdateMedication(new StubMedicationService());
    }

    public static TrNewPeriodicNotification createTrNewPeriodicNotification() {
        return new TrNewPeriodicNotification(new PetManagerAdapter());
    }

    public static TrDeletePeriodicNotification createTrDeletePeriodicNotification() {
        return new TrDeletePeriodicNotification(new PetManagerAdapter());
    }
}

