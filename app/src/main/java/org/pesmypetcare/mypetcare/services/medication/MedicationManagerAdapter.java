package org.pesmypetcare.mypetcare.services.medication;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.pet.MedicationData;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Xavier Campos
 */
public class MedicationManagerAdapter implements MedicationManagerService {

    private static final long TIME = 2;

    @Override
    public void createMedication(User user, Pet pet, Medication medication) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MedicationData libraryMedicationData = new MedicationData(medication.getMedicationQuantity(),
            medication.getMedicationDuration(), medication.getMedicationFrequency());
        org.pesmypetcare.usermanager.datacontainers.pet.Medication libraryMedication =
            new org.pesmypetcare.usermanager.datacontainers.pet.Medication(medication.getMedicationDate().toString(),
                medication.getMedicationName(), libraryMedicationData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner,
                        petName, PetData.MEDICATIONS, libraryMedication.getKey(), libraryMedication.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateMedicationBody(User user, Pet pet, Medication medication) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MedicationData libraryMedicationData = new MedicationData(medication.getMedicationQuantity(),
                medication.getMedicationDuration(), medication.getMedicationFrequency());
        org.pesmypetcare.usermanager.datacontainers.pet.Medication libraryMedication =
                new org.pesmypetcare.usermanager.datacontainers.pet.Medication(medication.getMedicationDate()
                    .toString(), medication.getMedicationName(), libraryMedicationData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner,
                        petName, PetData.MEDICATIONS, libraryMedication.getKey(), libraryMedication.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateMedicationKey(User user, Pet pet, String newDate, String oldDate, String newName,
                                    String oldName) {
        DateTime oldDateTime = DateTime.Builder.buildFullString(oldDate);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            MedicationData medicationData = null;
            try {
                medicationData = ServiceLocator.getInstance().getPetCollectionsManagerClient()
                        .getMedication(user.getToken(), user.getUsername(), pet.getName(), oldDate);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            Medication currentMedication = new Medication(oldName, medicationData.getQuantity(),
                    medicationData.getPeriodicity(), medicationData.getDuration(), oldDateTime);
            deleteMedication(user, pet, currentMedication);
            currentMedication.setMedicationName(newName);
            currentMedication.setMedicationDate(DateTime.Builder.buildFullString(newDate));
            createMedication(user, pet, currentMedication);
        });
        executorService.shutdown();
    }

    @Override
    public void deleteMedication(User user, Pet pet, Medication medication) {
        MedicationData libraryMedicationData = new MedicationData(medication.getMedicationQuantity(),
                medication.getMedicationDuration(), medication.getMedicationFrequency());
        org.pesmypetcare.usermanager.datacontainers.pet.Medication libraryMedication =
                new org.pesmypetcare.usermanager.datacontainers.pet.Medication(medication.getMedicationDate()
                    .toString(), medication.getMedicationName(), libraryMedicationData);
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner,
                        petName, PetData.MEDICATIONS, libraryMedication.getKey());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deleteMedicationsFromPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollection(accessToken, owner, petName,
                        PetData.MEDICATIONS);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public List<Medication> findMedicationsByPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        List<Medication> result = new ArrayList<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<org.pesmypetcare.usermanager.datacontainers.pet.Medication> serverMedications = new ArrayList<>();
            try {
                serverMedications = ServiceLocator.getInstance().getPetCollectionsManagerClient()
                        .getAllMedications(accessToken, owner, petName);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            for (org.pesmypetcare.usermanager.datacontainers.pet.Medication serverMedication : serverMedications) {
                Medication med = new Medication(serverMedication);
                result.add(med);
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
}
