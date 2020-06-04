package org.pesmypetcare.mypetcare.services.vetvisits;

import androidx.annotation.NonNull;

import org.pesmypetcare.httptools.exceptions.MyPetCareException;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.events.vetvisit.VetVisit;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.ServiceLocator;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.pesmypetcare.usermanager.datacontainers.pet.VetVisitData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Xavier Campos
 */
public class VetVisitsAdapter implements VetVisitsManagerService {


    private static final long TIME = 5;

    @Override
    public List<VetVisit> findVetVisitsByPet(User user, Pet pet) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        return getVetVisitsLibraryCall(accessToken, owner, petName);
    }

    /**
     * Method responsible for calling the library for getting all the vet visits from a pet.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @return A list containing all the vet visits of the pet
     */
    @NonNull
    private List<VetVisit> getVetVisitsLibraryCall(String accessToken, String owner, String petName) {
        ArrayList<VetVisit> appVisits = new ArrayList<>();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<org.pesmypetcare.usermanager.datacontainers.pet.VetVisit> libraryVisit =
                    null;
            try {
                libraryVisit = ServiceLocator.getInstance().getPetCollectionsManagerClient()
                        .getAllVetVisits(accessToken, petName);
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            for (org.pesmypetcare.usermanager.datacontainers.pet.VetVisit visit : libraryVisit) {
                appVisits.add(new VetVisit(DateTime.Builder.buildFullString(visit.getKey()),
                        visit.getBody().getAddress(), visit.getBody().getReason()));
            }
        });
        executorService.shutdown();
        try {
            executorService.awaitTermination(TIME, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return appVisits;
    }

    @Override
    public void createVetVisit(User user, Pet pet, VetVisit vetVisit) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        VetVisitData visitData = new VetVisitData(vetVisit.getReason(), vetVisit.getAddress());
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit libraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(vetVisit.getVisitDate().toString(),
                    visitData);

        createVisitLibCall(accessToken, owner, petName, libraryVisit);
    }

    /**
     * Method responsible for calling the library for creating a vet visit.
     * @param accessToken The access token of the user
     * @param owner The owner of the pet
     * @param petName The name of the pet
     * @param libraryVisit The visit that has to be created
     */
    private void createVisitLibCall(String accessToken, String owner, String petName,
                                    org.pesmypetcare.usermanager.datacontainers.pet.VetVisit libraryVisit) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken,
                        petName, PetData.VET_VISITS, libraryVisit.getKey(), libraryVisit.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void deleteVetVisit(User user, Pet pet, VetVisit vetVisit) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        VetVisitData visitData = new VetVisitData(vetVisit.getReason(), vetVisit.getAddress());
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit libraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(vetVisit.getVisitDate().toString(),
                    visitData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken,
                        petName, PetData.VET_VISITS, libraryVisit.getKey());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void updateVetVisitKey(User user, Pet pet, String newDate, DateTime visitDate) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            VetVisitData vetVisitData = null;
            try {
                vetVisitData = ServiceLocator.getInstance().getPetCollectionsManagerClient().getVetVisit(
                        accessToken, petName, visitDate.toString());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            org.pesmypetcare.usermanager.datacontainers.pet.VetVisit oldLibraryVisit =
                    new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(visitDate.toString(), vetVisitData);
            org.pesmypetcare.usermanager.datacontainers.pet.VetVisit newLibraryVisit =
                    new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(newDate, vetVisitData);
            try {
                ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken,
                        petName, PetData.VET_VISITS, oldLibraryVisit.getKey());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
            try {
                ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken,
                        petName, PetData.VET_VISITS, newLibraryVisit.getKey(), newLibraryVisit.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }

    @Override
    public void updateVetVisitBody(User user, Pet pet, VetVisit vetVisit) {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VetVisitData visitData = new VetVisitData(vetVisit.getReason(), vetVisit.getAddress());
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit libraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(vetVisit.getVisitDate().toString(),
                    visitData);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken,
                        petName, PetData.VET_VISITS, libraryVisit.getKey(), libraryVisit.getBodyAsMap());
            } catch (MyPetCareException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
