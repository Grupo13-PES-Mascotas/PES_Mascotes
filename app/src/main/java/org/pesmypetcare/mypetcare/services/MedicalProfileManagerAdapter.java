package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Illness;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.IllnessData;
import org.pesmypetcare.usermanagerlib.datacontainers.IllnessType;
import org.pesmypetcare.usermanagerlib.datacontainers.PetData;
import org.pesmypetcare.usermanagerlib.datacontainers.SeverityType;
import org.pesmypetcare.usermanagerlib.datacontainers.VaccinationData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos & Enric Hernando
 */
public class MedicalProfileManagerAdapter implements MedicalProfileManagerService {
    @Override
    public void createVaccination(User user, Pet pet, Vaccination vaccination) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = new VaccinationData(vaccination.getVaccinationDescription());
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(vaccination.getVaccinationDate().toString(),
                vaccinationData);

        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.VACCINATIONS, libraryVaccination.getKey(), libraryVaccination.getBodyAsMap());
    }

    @Override
    public List<Vaccination> findVaccinationsByPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ArrayList<Vaccination> appVaccinations = new ArrayList<>();
        List<org.pesmypetcare.usermanagerlib.datacontainers.Vaccination> libraryVaccinations =
            ServiceLocator.getInstance().getPetCollectionsManagerClient().getAllVaccinations(accessToken, owner,
                petName);
        for (org.pesmypetcare.usermanagerlib.datacontainers.Vaccination vaccination: libraryVaccinations) {
            appVaccinations.add(new Vaccination(vaccination.getBody().getDescription(),
                DateTime.Builder.buildFullString(vaccination.getKey())));
        }
        return appVaccinations;
    }

    @Override
    public void deleteVaccination(User user, Pet pet, Vaccination vaccination) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = new VaccinationData(vaccination.getVaccinationDescription());
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(vaccination.getVaccinationDate().toString(),
                vaccinationData);

        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
            PetData.VACCINATIONS, libraryVaccination.getKey());
    }

    @Override
    public void updateVaccinationKey(User user, Pet pet, String newDate, DateTime vaccinationDate) throws
        ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = ServiceLocator.getInstance().getPetCollectionsManagerClient().
            getVaccination(accessToken, owner, petName, vaccinationDate.toString());
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination oldVaccination =
            new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(vaccinationDate.toString(),
                vaccinationData);
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination newVaccination =
            new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(newDate, vaccinationData);
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
            PetData.VACCINATIONS, oldVaccination.getKey());
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.VACCINATIONS, newVaccination.getKey(), newVaccination.getBodyAsMap());
    }

    @Override
    public void updateVaccinationBody(User user, Pet pet, Vaccination vaccination) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = new VaccinationData(vaccination.getVaccinationDescription());
        org.pesmypetcare.usermanagerlib.datacontainers.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanagerlib.datacontainers.Vaccination(vaccination.getVaccinationDate().toString(),
                vaccinationData);

        ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner, petName,
            PetData.VACCINATIONS, libraryVaccination.getKey(), libraryVaccination.getBodyAsMap());
    }

    @Override
    public void createIllness(User user, Pet pet, Illness illness) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        IllnessData illnessData = new IllnessData(illness.getEndTime().toString(), illness.getDescription(),
            IllnessType.valueOf(illness.getType()), SeverityType.valueOf(illness.getSeverity()));
        org.pesmypetcare.usermanagerlib.datacontainers.Illness libraryIllness =
            new org.pesmypetcare.usermanagerlib.datacontainers.Illness(illness.getDateTime().toString(), illnessData);

        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.ILLNESSES, libraryIllness.getKey(), libraryIllness.getBodyAsMap());
    }

    @Override
    public List<Illness> findIllnessesByPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        List<org.pesmypetcare.usermanagerlib.datacontainers.Illness> illnesses = ServiceLocator.getInstance()
                .getPetCollectionsManagerClient().getAllIllnesses(user.getToken(), user.getUsername(), pet.getName());

        ArrayList<Illness> result = new ArrayList<>();
        for (org.pesmypetcare.usermanagerlib.datacontainers.Illness i : illnesses) {
            result.add(new Illness(i.getBody().getDescription(), DateTime.Builder.buildFullString(i.getKey()),
                    DateTime.Builder.buildFullString(i.getBody().getEndDateTime()), i.getBody().getType().toString(),
                    i.getBody().getSeverity().toString()));
        }
        return result;
    }

    @Override
    public void deleteIllness(User user, Pet pet, Illness illness) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        org.pesmypetcare.usermanagerlib.datacontainers.Illness libraryIllness =
                new org.pesmypetcare.usermanagerlib.datacontainers.Illness(illness.getDateTime().toString(),
                        new IllnessData(illness.getEndTime().toString(), illness.getDescription(),
                                IllnessType.valueOf(illness.getType()), SeverityType.valueOf(illness.getSeverity())));
        try {
            ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
                    PetData.ILLNESSES, libraryIllness.getKey());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
