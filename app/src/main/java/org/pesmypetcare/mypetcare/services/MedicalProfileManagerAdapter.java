package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;
import org.pesmypetcare.usermanagerlib.datacontainers.VaccinationData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos & Enric Hernando
 */
public class MedicalProfileManagerAdapter implements MedicalProfileManagerService {
    @Override
    public void createVaccination(User user, Pet pet, Vaccination vaccination) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination libraryVaccination =
                new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(vaccination.getDateTime().toString(),
                        vaccination.getVaccinationDescription());
        try {
            ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName, PetData.VACCINATIONS,
                    libraryVaccination.getKey(), libraryVaccination.getBodyAsMap());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vaccination> findVaccinationsByPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        List<org.pesmypetcare.usermanagerlib.datacontainers.Vaccination> vaccinations = ServiceLocator.getInstance()
                    .getPetCollectionsManagerClient().getAllVaccinations(user.getToken(), user.getUsername(), pet.getName());

        ArrayList<Vaccination> result = new ArrayList<>();
        for (org.pesmypetcare.usermanagerlib.datacontainers.Vaccination v : vaccinations) {
            result.add(new Vaccination(v.getBody().getDescription(), DateTime.Builder.buildFullString(v.getKey())));
        }
        return result;
    }

    @Override
    public void deleteVaccination(User user, Pet pet, Vaccination vaccination) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination libraryVaccination =
                new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(vaccination.getDateTime().toString(),
                        vaccination.getVaccinationDescription());
        try {
            ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
                    PetData.VACCINATIONS, libraryVaccination.getKey());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateVaccinationKey(User user, Pet pet, String newDate, DateTime vaccinationDate) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        VaccinationData libraryVaccinationData = ServiceLocator.getInstance().getPetCollectionsManagerClient()
                .getVaccination(user.getToken(), user.getUsername(), pet.getName(), vaccinationDate.toString());
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination libraryUpdatedVaccination =
                new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(newDate,
                        libraryVaccinationData.getDescription());
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
                PetData.VACCINATIONS, vaccinationDate.toString());
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
                PetData.VACCINATIONS, libraryUpdatedVaccination.getKey(), libraryUpdatedVaccination.getBodyAsMap());
    }

    @Override
    public void updateVaccinationBody(User user, Pet pet, Vaccination vaccination) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination libraryVaccination =
                new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(vaccination.getDateTime().toString(),
                        vaccination.getDescription());
        try {
            ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner, petName,
                    PetData.VACCINATIONS, libraryVaccination.getKey(), libraryVaccination.getBodyAsMap());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
