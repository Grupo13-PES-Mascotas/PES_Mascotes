package org.pesmypetcare.mypetcare.controllers;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.PetManagerService;
import org.pesmypetcare.mypetcare.services.UserManagerService;

public class TrNewPersonalEvent {
    private PetManagerService petManagerService;
    private UserManagerService userManagerService;
    private User user;
    private Pet pet;
    private Event event;
    private boolean result;


    public TrNewPersonalEvent(PetManagerService petManagerService, UserManagerService userManagerService) {
        this.petManagerService = petManagerService;
        this.userManagerService = userManagerService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
