package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PetManagerAdapter implements PetManagerService {
    @Override
    public void updatePet(Pet pet) {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();

        ServiceLocator.getInstance().getPetManagerClient().updateGender(ownerUsername, name,
            pet.getGender().toString());
        //ServiceLocator.getInstance().getPetManagerClient().updateBirthday(ownerUsername, name, pet.getBirthDate());
        ServiceLocator.getInstance().getPetManagerClient().updateWeight(ownerUsername, name, pet.getWeight());
        ServiceLocator.getInstance().getPetManagerClient().updateBreed(ownerUsername, name, pet.getBreed());
        /*ServiceLocator.getInstance().getPetManagerClient().updatePathologies(ownerUsername, name,
            pet.getPathologies());
        ServiceLocator.getInstance().getPetManagerClient().updateRecKcal(ownerUsername, name,
            pet.getRecommendedDailyKiloCalories());
        ServiceLocator.getInstance().getPetManagerClient().updateWashFreq(ownerUsername, name,
            pet.getWashFrequency());*/
    }

    @Override
    public boolean registerNewPet(String username, Pet pet) {
        ServiceLocator.getInstance().getPetManagerClient()
            .signUpPet(username, pet.getName(), pet.getGender().toString(), pet.getBreed(), pet.getBirthDate(),
                pet.getWeight(), pet.getPathologies(), pet.getRecommendedDailyKiloCalories(), pet.getWashFrequency());
        return true;
    }

    @Override
    public void updatePetImage(String username, String petName, Bitmap newPetImage) {
        // Not implemented yet
    }

    @Override
    public void deletePet(Pet pet, String username) {
        ServiceLocator.getInstance().getPetManagerClient().deletePet(username, pet.getName());
    }

    @Override
    public void deletePetsFromUser(User user) {
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            ServiceLocator.getInstance().getPetManagerClient().deletePet(user.getUsername(), pet.getName());
        }
    }

    @Override
    public List<Pet> findPetsByOwner(String username) throws PetRepeatException {
        List<org.pesmypetcare.usermanagerlib.datacontainers.Pet> userPets = null;

        try {
            userPets = ServiceLocator.getInstance().getPetManagerClient().getAllPets(username);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        List<Pet> pets = new ArrayList<>();

        for (org.pesmypetcare.usermanagerlib.datacontainers.Pet userPet : userPets) {
            if (userPet != null) {
                pets.add(decodePet(userPet));
            }
        }

        return pets;
    }

    /**
     * Decodes the pet information from the server.
     * @param userPet The information from the server
     * @return The pet associated with that information
     * @throws PetRepeatException The pet is repeated.
     */
    private Pet decodePet(org.pesmypetcare.usermanagerlib.datacontainers.Pet userPet) throws PetRepeatException {
        PetData petData = userPet.getBody();
        Pet pet = new Pet();

        pet.setName(userPet.getName());
        pet.setGender(petData.getGender());
        pet.setBirthDate(DateConversion.convertToApp(petData.getBirth().toString()));
        pet.setWeight(petData.getWeight());
        pet.setWashFrequency(petData.getWashFreq());
        pet.setRecommendedDailyKiloCalories(petData.getRecommendedKcal());
        pet.setBreed(petData.getBreed());
        pet.setPathologies(petData.getPathologies());
        return pet;
    }
}
