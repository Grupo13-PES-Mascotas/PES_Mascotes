package org.pesmypetcare.mypetcare.services.medication;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.medication.Medication;
import org.pesmypetcare.mypetcare.features.pets.events.medication.MedicationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.pet.MedicationData;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class MedicationManagerAdapter implements MedicationManagerService {
    @Override
    public void createMedication(User user, Pet pet, Medication medication) throws MedicationAlreadyExistingException,
        ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MedicationData libraryMedicationData = new MedicationData(medication.getMedicationQuantity(),
            medication.getMedicationDuration(), medication.getMedicationFrequency());
        org.pesmypetcare.usermanager.datacontainers.pet.Medication libraryMedication =
            new org.pesmypetcare.usermanager.datacontainers.pet.Medication(medication.getMedicationDate().toString(),
                medication.getMedicationName(), libraryMedicationData);
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.MEDICATIONS, libraryMedication.getKey(), libraryMedication.getBodyAsMap());
    }

    @Override
    public void updateMedicationBody(User user, Pet pet, Medication medication) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        MedicationData libraryMedicationData = new MedicationData(medication.getMedicationQuantity(),
                medication.getMedicationDuration(), medication.getMedicationFrequency());
        org.pesmypetcare.usermanager.datacontainers.pet.Medication libraryMedication =
                new org.pesmypetcare.usermanager.datacontainers.pet.Medication(medication.getMedicationDate()
                    .toString(), medication.getMedicationName(), libraryMedicationData);
        ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner, petName,
                PetData.MEDICATIONS, libraryMedication.getKey(), libraryMedication.getBodyAsMap());
    }

    @Override
    public void updateMedicationKey(User user, Pet pet, String newDate, String oldDate, String newName,
                                    String oldName) throws ExecutionException, InterruptedException,
        MedicationAlreadyExistingException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        DateTime oldDateTime = DateTime.Builder.buildFullString(oldDate);

        MedicationData medicationData = ServiceLocator.getInstance().getPetCollectionsManagerClient().getMedication(user.getToken(), user.getUsername(), pet.getName(), oldDate);
        Medication currentMedication = new Medication(oldName, medicationData.getQuantity(),
            medicationData.getPeriodicity(), medicationData.getDuration(), oldDateTime);
        deleteMedication(user, pet, currentMedication);
        currentMedication.setMedicationName(newName);
        currentMedication.setMedicationDate(DateTime.Builder.buildFullString(newDate));
        createMedication(user, pet, currentMedication);
    }

    @Override
    public void deleteMedication(User user, Pet pet, Medication medication) throws ExecutionException,
        InterruptedException {
        MedicationData libraryMedicationData = new MedicationData(medication.getMedicationQuantity(),
                medication.getMedicationDuration(), medication.getMedicationFrequency());
        org.pesmypetcare.usermanager.datacontainers.pet.Medication libraryMedication =
                new org.pesmypetcare.usermanager.datacontainers.pet.Medication(medication.getMedicationDate()
                    .toString(), medication.getMedicationName(), libraryMedicationData);
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
                PetData.MEDICATIONS, libraryMedication.getKey());
    }

    @Override
    public void deleteMedicationsFromPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollection(accessToken, owner, petName,
                PetData.MEDICATIONS);
    }

    @Override
    public List<Medication> findMedicationsByPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        List<Medication> result = new ArrayList<>();
        List<org.pesmypetcare.usermanager.datacontainers.pet.Medication> serverMedications = new ArrayList<>();
        serverMedications = ServiceLocator.getInstance().getPetCollectionsManagerClient().getAllMedications(accessToken,
            owner, petName);
        for (org.pesmypetcare.usermanager.datacontainers.pet.Medication serverMedication : serverMedications) {
            Medication med = new Medication(serverMedication);
            result.add(med);
        }
        return result;
    }
}
