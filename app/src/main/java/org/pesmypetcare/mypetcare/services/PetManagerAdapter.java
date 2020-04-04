package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Event;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PetManagerAdapter implements PetManagerService {
    @Override
    public void updatePet(Pet pet) {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();

        ServiceLocator.getInstance().getPetManagerClient().updateGender(pet.getOwner().getToken(), ownerUsername, name,
            pet.getGender().toString());
        //ServiceLocator.getInstance().getPetManagerClient().updateBirthday(pet.getOwner().getToken(), ownerUsername,
        //  name, pet.getBirthDate());
        ServiceLocator.getInstance().getPetManagerClient().updateWeight(pet.getOwner().getToken(), ownerUsername,
            name, pet.getWeight());
        ServiceLocator.getInstance().getPetManagerClient().updateBreed(pet.getOwner().getToken(), ownerUsername,
            name, pet.getBreed());
        /*ServiceLocator.getInstance().getPetManagerClient().updatePathologies(pet.getOwner().getToken(),
            ownerUsername, name, pet.getPathologies());
        ServiceLocator.getInstance().getPetManagerClient().updateRecKcal(pet.getOwner().getToken(), ownerUsername,
            name, pet.getRecommendedDailyKiloCalories());
        ServiceLocator.getInstance().getPetManagerClient().updateWashFreq(pet.getOwner().getToken(), ownerUsername,
            name, pet.getWashFrequency());*/
    }

    @Override
    public boolean registerNewPet(User user, Pet pet) {
        ServiceLocator.getInstance().getPetManagerClient().createPet(user.getToken(), user.getUsername(), pet.getName(),
            pet.getGender().toString(), pet.getBreed(), pet.getBirthDate(), pet.getWeight(), pet.getPathologies(),
            pet.getRecommendedDailyKiloCalories(), pet.getWashFrequency());
        return true;
    }

    @Override
    public void updatePetImage(User user, Pet pet, Bitmap newPetImage) {
        byte[] bytesImage = ImageManager.getImageBytes(newPetImage);
        if (pet.getProfileImage() != null) {
            ImageManager.writeImage(ImageManager.PET_PROFILE_IMAGES_PATH, user.getUsername() + '_' + pet.getName(),
                bytesImage);
        }

        ServiceLocator.getInstance().getPetManagerClient().saveProfileImage(user.getToken(), user.getUsername(),
            pet.getName(), bytesImage);
    }

    @Override
    public void deletePet(Pet pet, User user) {
        ServiceLocator.getInstance().getPetManagerClient().deletePet(user.getToken(), user.getUsername(),
            pet.getName());

        ImageManager.deleteImage(ImageManager.PET_PROFILE_IMAGES_PATH, user.getUsername() + '_' + pet.getName());
    }

    @Override
    public void deletePetsFromUser(User user) {
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            ServiceLocator.getInstance().getPetManagerClient().deletePet(user.getToken(), user.getUsername(),
                pet.getName());
        }
    }

    @Override
    public List<Pet> findPetsByOwner(User user) throws PetRepeatException {
        List<org.pesmypetcare.usermanagerlib.datacontainers.Pet> userPets = null;

        try {
            userPets = ServiceLocator.getInstance().getPetManagerClient().getAllPets(user.getToken(),
                user.getUsername());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return getPets(userPets);
    }

    /**
     * Get the transformation of the pets from the server to pet instances.
     * @param userPets The pets from the server
     * @return The instances of the pets of our application
     * @throws PetRepeatException The pet is repeated
     */
    private List<Pet> getPets(List<org.pesmypetcare.usermanagerlib.datacontainers.Pet> userPets)
        throws PetRepeatException {
        List<Pet> pets = new ArrayList<>();

        for (org.pesmypetcare.usermanagerlib.datacontainers.Pet userPet : Objects.requireNonNull(userPets)) {
            if (userPet != null) {
                pets.add(decodePet(userPet));
            }
        }

        return pets;
    }

    @Override
    public Map<String, byte[]> getAllPetsImages(User user) {
        Map<String, byte[]> pets = null;

        try {
            pets = ServiceLocator.getInstance().getPetManagerClient().downloadAllProfileImages(user.getToken(),
                user.getUsername());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return pets;
    }
      
    public void registerNewEvent(Pet pet, Event event) {
        // Not implemented yet
    }

    @Override
    public void deleteEvent(Pet pet, Event event) {
        // Not implemented yet
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
