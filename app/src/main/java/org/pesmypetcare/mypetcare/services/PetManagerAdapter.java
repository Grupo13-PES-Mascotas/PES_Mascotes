package org.pesmypetcare.mypetcare.services;

import android.graphics.Bitmap;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.usermanagerlib.clients.PetManagerClient;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PetManagerAdapter implements PetManagerService {
    @Override
    public void updatePet(Pet pet) {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();

        try {
            ServiceLocator.getInstance().getPetManagerClient().updateField("token", ownerUsername, name,
                PetManagerClient.GENDER, pet.getGender().toString());
            ServiceLocator.getInstance().getPetManagerClient().updateField("token", ownerUsername, name,
                PetManagerClient.BREED, pet.getBreed());
            updateHealth(pet);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        /*ServiceLocator.getInstance().getPetManagerClient().updateGender(userToken, ownerUsername, name,
            pet.getGender().toString());
        ServiceLocator.getInstance().getPetManagerClient().updateBirthday(pet.getOwner().getToken(), ownerUsername,
          name, pet.getBirthDate());
        ServiceLocator.getInstance().getPetManagerClient().updateBreed(userToken, ownerUsername,
            name, pet.getBreed());
        ServiceLocator.getInstance().getPetManagerClient().updatePathologies(pet.getOwner().getToken(),
            ownerUsername, name, pet.getPathologies());
        updateHealth(pet);*/
    }

    /**
     * Update the health data of the pet.
     * @param pet The pet to which its health data has to be updated
     */
    private void updateHealth(Pet pet) throws ExecutionException, InterruptedException {
        String name = pet.getName();
        String ownerUsername = pet.getOwner().getUsername();

        ServiceLocator.getInstance().getPetManagerClient().updateField("token", ownerUsername, name,
            PetManagerClient.WEIGHT, pet.getWeight());

        /*ServiceLocator.getInstance().getPetManagerClient().updateWeight(pet.getOwner().getToken(), ownerUsername,
            name, pet.getWeight());
        ServiceLocator.getInstance().getPetManagerClient().updateRecKcal(pet.getOwner().getToken(), ownerUsername,
            name, pet.getRecommendedDailyKiloCalories());
        ServiceLocator.getInstance().getPetManagerClient().updateWashFreq(pet.getOwner().getToken(), ownerUsername,
            name, pet.getWashFrequency());*/
    }

    @Override
    public boolean registerNewPet(String username, Pet pet) {
        /*ServiceLocator.getInstance().getPetManagerClient().createPet(user.getToken(), user.getUsername(),
            pet.getName(), pet.getGender().toString(), pet.getBreed(), pet.getBirthDate(), pet.getWeight(),
            pet.getPathologies(), pet.getRecommendedDailyKiloCalories(), pet.getWashFrequency());*/

        org.pesmypetcare.usermanagerlib.datacontainers.Pet registerPet = getRegisterPet(pet);

        try {
            ServiceLocator.getInstance().getPetManagerClient()
                .createPet("token", username, registerPet);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Get the register pet.
     * @param pet The pet from he application
     * @return The pet to be registered in the system
     */
    private org.pesmypetcare.usermanagerlib.datacontainers.Pet getRegisterPet(Pet pet) {
        org.pesmypetcare.usermanagerlib.datacontainers.Pet registerPet;
        registerPet = new org.pesmypetcare.usermanagerlib.datacontainers.Pet();
        PetData petData = new PetData();

        registerPet.setName(pet.getName());
        petData.setBirth(pet.getBirthDate());
        petData.setBreed(pet.getBreed());
        petData.setGender(pet.getGender());
        petData.setPathologies(pet.getPathologies());
        petData.setRecommendedKcal(pet.getRecommendedDailyKiloCalories());
        petData.setWashFreq(pet.getWashFrequency());
        petData.setWeight(pet.getWeight());
        registerPet.setBody(petData);
        return registerPet;
    }

    @Override
    public void updatePetImage(String username, String petName, Bitmap newPetImage) {
        // To be implemented
    }

    @Override
    public void deletePet(Pet pet, String username) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().deletePet("token", username,
                pet.getName());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePetsFromUser(User user) {
        ArrayList<Pet> pets = user.getPets();

        for (Pet pet : pets) {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deletePet("token", user.getUsername(),
                    pet.getName());
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Pet> findPetsByOwner(String username) throws PetRepeatException {
        List<org.pesmypetcare.usermanagerlib.datacontainers.Pet> userPets = null;

        try {
            userPets = ServiceLocator.getInstance().getPetManagerClient().getAllPets("token", username);
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
