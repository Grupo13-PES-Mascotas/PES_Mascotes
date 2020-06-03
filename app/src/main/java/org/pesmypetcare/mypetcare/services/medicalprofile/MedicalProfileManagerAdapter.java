package org.pesmypetcare.mypetcare.services.medicalprofile;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Xavier Campos & Enric Hernando
 */
public class MedicalProfileManagerAdapter implements MedicalProfileManagerService {
    private static final long TIME = 2;

    @Override
    public void createVaccination(User user, Pet pet, Vaccination vaccination) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = new VaccinationData(vaccination.getDescription());
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccination.getVaccinationDate()
                    .toString(), vaccinationData);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner,
                        petName, PetData.VACCINATIONS, libraryVaccination.getKey(), libraryVaccination.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public List<Vaccination> findVaccinationsByPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ArrayList<Vaccination> appVaccinations = new ArrayList<>();
        findVaccinationsByPetLibraryCall(accessToken, owner, petName, appVaccinations);
        return appVaccinations;
    }

    /**
     * Method responsible for calling the library to obtain all the vaccinations of a given pet.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param appVaccinations The list with all the vaccinations of the pet
     */
    private void findVaccinationsByPetLibraryCall(String accessToken, String owner, String petName,
                                                  List<Vaccination> appVaccinations) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<org.pesmypetcare.usermanager.datacontainers.pet.Vaccination> libraryVaccinations = null;
            try {
                libraryVaccinations = ServiceLocator.getInstance().getPetCollectionsManagerClient()
                        .getAllVaccinations(accessToken, owner, petName);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            for (org.pesmypetcare.usermanager.datacontainers.pet.Vaccination vaccination: libraryVaccinations) {
                appVaccinations.add(new Vaccination(vaccination.getBody().getDescription(),
                        DateTime.Builder.buildFullString(vaccination.getKey())));
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVaccination(User user, Pet pet, Vaccination vaccination) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = new VaccinationData(vaccination.getDescription());
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccination.getVaccinationDate()
                    .toString(), vaccinationData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner,
                        petName, PetData.VACCINATIONS, libraryVaccination.getKey());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateVaccinationKey(User user, Pet pet, String newDate, DateTime vaccinationDate) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            VaccinationData vaccinationData = null;
            vaccinationData = getVaccinationData(vaccinationDate, accessToken, owner, petName);
            org.pesmypetcare.usermanager.datacontainers.pet.Vaccination oldVaccination =
                    new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccinationDate.toString(),
                            vaccinationData);
            org.pesmypetcare.usermanager.datacontainers.pet.Vaccination newVaccination =
                    new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(newDate, vaccinationData);
            deleteOldVaccination(accessToken, owner, petName, oldVaccination);
            createNewVaccination(accessToken, owner, petName, newVaccination);
        });

    }

    /**
     * Creates the new vaccination with the updated date.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param newVaccination The updated vaccination that has to be created
     */
    private void createNewVaccination(String accessToken, String owner, String petName,
                                      org.pesmypetcare.usermanager.datacontainers.pet.Vaccination newVaccination) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
                    PetData.VACCINATIONS, newVaccination.getKey(), newVaccination.getBodyAsMap());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the old vaccination with the old date.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param oldVaccination The old vaccination that has to be deleted
     */
    private void deleteOldVaccination(String accessToken, String owner, String petName,
                                      org.pesmypetcare.usermanager.datacontainers.pet.Vaccination oldVaccination) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner,
                    petName, PetData.VACCINATIONS, oldVaccination.getKey());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtains the data of the vaccination which date has to be updated.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     */
    private VaccinationData getVaccinationData(DateTime vaccinationDate, String accessToken, String owner,
                                               String petName) {
        VaccinationData vaccinationData = new VaccinationData();
        try {
            vaccinationData = ServiceLocator.getInstance().getPetCollectionsManagerClient()
                    .getVaccination(accessToken, owner, petName, vaccinationDate.toString());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
        return vaccinationData;
    }

    @Override
    public void updateVaccinationBody(User user, Pet pet, Vaccination vaccination) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VaccinationData vaccinationData = new VaccinationData(vaccination.getDescription());
        org.pesmypetcare.usermanager.datacontainers.pet.Vaccination libraryVaccination =
            new org.pesmypetcare.usermanager.datacontainers.pet.Vaccination(vaccination.getVaccinationDate()
                    .toString(), vaccinationData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner,
                        petName, PetData.VACCINATIONS, libraryVaccination.getKey(), libraryVaccination.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void createIllness(User user, Pet pet, Illness illness) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        IllnessData illnessData = new IllnessData(illness.getEndTime().toString(), illness.getDescription(),
            IllnessType.valueOf(illness.getType()), SeverityType.valueOf(illness.getSeverity()));
        org.pesmypetcare.usermanager.datacontainers.pet.Illness libraryIllness =
            new org.pesmypetcare.usermanager.datacontainers.pet.Illness(illness.getDateTime().toString(), illnessData);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner,
                        petName, PetData.ILLNESSES, libraryIllness.getKey(), libraryIllness.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public List<Illness> findIllnessesByPet(User user, Pet pet) {
        ArrayList<Illness> result = new ArrayList<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<org.pesmypetcare.usermanager.datacontainers.pet.Illness> illnesses = null;
            try {
                illnesses = ServiceLocator.getInstance()
                        .getPetCollectionsManagerClient().getAllIllnesses(user.getToken(), user.getUsername(),
                                pet.getName());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            for (org.pesmypetcare.usermanager.datacontainers.pet.Illness i : illnesses) {
                result.add(new Illness(i.getBody().getDescription(), DateTime.Builder.buildFullString(i.getKey()),
                        DateTime.Builder.buildFullString(i.getBody().getEndDateTime()),
                        i.getBody().getType().toString(), i.getBody().getSeverity().toString()));
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner,
                        petName, PetData.ILLNESSES, libraryIllness.getKey());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateIllnessBody(User user, Pet pet, Illness illness) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        IllnessData illnessData = new IllnessData(illness.getEndTime().toString(), illness.getDescription(),
            IllnessType.valueOf(illness.getType()), SeverityType.valueOf(illness.getSeverity()));
        org.pesmypetcare.usermanager.datacontainers.pet.Illness libraryIllness =
            new org.pesmypetcare.usermanager.datacontainers.pet.Illness(illness.getDateTime().toString(),
                illnessData);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner,
                        petName, PetData.ILLNESSES, libraryIllness.getKey(), libraryIllness.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateIllnessKey(User user, Pet pet, String newDate, DateTime dateTime) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            IllnessData illnessData = null;
            illnessData = getIllnessData(dateTime, accessToken, owner, petName);
            org.pesmypetcare.usermanager.datacontainers.pet.Illness oldIllness =
                    new org.pesmypetcare.usermanager.datacontainers.pet.Illness(dateTime.toString(),
                            illnessData);
            org.pesmypetcare.usermanager.datacontainers.pet.Illness newIllness =
                    new org.pesmypetcare.usermanager.datacontainers.pet.Illness(newDate, illnessData);
            deleteOldIllness(accessToken, owner, petName, oldIllness);
            createNewIllness(accessToken, owner, petName, newIllness);
        });

    }

    /**
     * Creates the new illness with the updated date.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param newIllness The updated illness that has to be created
     */
    private void createNewIllness(String accessToken, String owner, String petName,
                                  org.pesmypetcare.usermanager.datacontainers.pet.Illness newIllness) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
                    PetData.ILLNESSES, newIllness.getKey(), newIllness.getBodyAsMap());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes the old illness with the old date.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param oldIllness The old illness that has to be deleted
     */
    private void deleteOldIllness(String accessToken, String owner, String petName,
                                  org.pesmypetcare.usermanager.datacontainers.pet.Illness oldIllness) {
        try {
            ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner,
                    petName, PetData.ILLNESSES, oldIllness.getKey());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtains the data of the illness which date has to be updated.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     */
    private IllnessData getIllnessData(DateTime dateTime, String accessToken, String owner, String petName) {
        IllnessData illnessData = new IllnessData();
        try {
            illnessData = ServiceLocator.getInstance().getPetCollectionsManagerClient()
                    .getIllness(accessToken, owner, petName, dateTime.toString());
        } catch (MyPetCareException e) {
            e.printStackTrace();
        }
        return illnessData;
    }
}
