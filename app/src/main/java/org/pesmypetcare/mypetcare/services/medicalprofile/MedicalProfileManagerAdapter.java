package org.pesmypetcare.mypetcare.services.medicalprofile;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.illness.Illness;
import org.pesmypetcare.mypetcare.features.pets.events.medicalprofile.vaccination.Vaccination;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.pet.IllnessData;
import org.pesmypetcare.usermanager.datacontainers.pet.IllnessType;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.pesmypetcare.usermanager.datacontainers.pet.SeverityType;
import org.pesmypetcare.usermanager.datacontainers.pet.VaccinationData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos & Enric Hernando
 */
public class MedicalProfileManagerAdapter implements MedicalProfileManagerService {
    @Override
    public void createVaccination(User user, Pet pet, Vaccination vaccination) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = new VaccinationData(vaccination.getDescription());
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccination.getVaccinationDate().toString(),
                vaccinationData);

        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.VACCINATIONS, libraryVaccination.getKey(), libraryVaccination.getBodyAsMap());
    }

    @Override
    public List<Vaccination> findVaccinationsByPet(User user, Pet pet) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ArrayList<Vaccination> appVaccinations = new ArrayList<>();
        List<org.pesmypetcare.usermanager.datacontainers.pet.Vaccination> libraryVaccinations =
            ServiceLocator.getInstance().getPetCollectionsManagerClient().getAllVaccinations(accessToken, owner,
                petName);
        for (org.pesmypetcare.usermanager.datacontainers.pet.Vaccination vaccination: libraryVaccinations) {
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

        VaccinationData vaccinationData = new VaccinationData(vaccination.getDescription());
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccination.getVaccinationDate().toString(),
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

        VaccinationData vaccinationData = ServiceLocator.getInstance().getPetCollectionsManagerClient()
            .getVaccination(accessToken, owner, petName, vaccinationDate.toString());
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination oldVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccinationDate.toString(),
                vaccinationData);
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination newVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(newDate, vaccinationData);
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

        VaccinationData vaccinationData = new VaccinationData(vaccination.getDescription());
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccination.getVaccinationDate().toString(),
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
        org.pesmypetcare.usermanager.datacontainers.pet.Illness libraryIllness =
            new org.pesmypetcare.usermanager.datacontainers.pet.Illness(illness.getDateTime().toString(), illnessData);

        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.ILLNESSES, libraryIllness.getKey(), libraryIllness.getBodyAsMap());
    }

    @Override
    public List<Illness> findIllnessesByPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        List<org.pesmypetcare.usermanager.datacontainers.pet.Illness> illnesses = ServiceLocator.getInstance()
                .getPetCollectionsManagerClient().getAllIllnesses(user.getToken(), user.getUsername(), pet.getName());

        ArrayList<Illness> result = new ArrayList<>();
        for (org.pesmypetcare.usermanager.datacontainers.pet.Illness i : illnesses) {
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
        org.pesmypetcare.usermanager.datacontainers.pet.Illness libraryIllness =
                new org.pesmypetcare.usermanager.datacontainers.pet.Illness(illness.getDateTime().toString(),
                        new IllnessData(illness.getEndTime().toString(), illness.getDescription(),
                                IllnessType.valueOf(illness.getType()), SeverityType.valueOf(illness.getSeverity())));
        try {
            ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
                    PetData.ILLNESSES, libraryIllness.getKey());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateIllnessBody(User user, Pet pet, Illness illness) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        IllnessData illnessData = new IllnessData(illness.getEndTime().toString(), illness.getDescription(),
            IllnessType.valueOf(illness.getType()), SeverityType.valueOf(illness.getSeverity()));
        org.pesmypetcare.usermanager.datacontainers.pet.Illness libraryIllness =
            new org.pesmypetcare.usermanager.datacontainers.pet.Illness(illness.getDateTime().toString(),
                illnessData);

        ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner, petName,
            PetData.ILLNESSES, libraryIllness.getKey(), libraryIllness.getBodyAsMap());
    }

    @Override
    public void updateIllnessKey(User user, Pet pet, String newDate, DateTime dateTime) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        IllnessData illnessData = ServiceLocator.getInstance().getPetCollectionsManagerClient()
            .getIllness(accessToken, owner, petName, dateTime.toString());
        org.pesmypetcare.usermanager.datacontainers.pet.Illness oldIllness =
            new org.pesmypetcare.usermanager.datacontainers.pet.Illness(dateTime.toString(),
                illnessData);
        org.pesmypetcare.usermanager.datacontainers.pet.Illness newIllness =
            new org.pesmypetcare.usermanager.datacontainers.pet.Illness(newDate, illnessData);
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
            PetData.ILLNESSES, oldIllness.getKey());
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.ILLNESSES, newIllness.getKey(), newIllness.getBodyAsMap());
    }
}
