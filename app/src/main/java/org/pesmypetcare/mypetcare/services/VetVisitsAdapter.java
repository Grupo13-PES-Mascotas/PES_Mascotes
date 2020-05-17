package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.pet.PetData;
import org.pesmypetcare.usermanager.datacontainers.pet.VetVisitData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author Xavier Campos
 */
public class VetVisitsAdapter implements VetVisitsManagerService {
    @Override
    public List<VetVisit> findVetVisitsByPet(User user, Pet pet) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        ArrayList<VetVisit> appVisits = new ArrayList<>();
        List<org.pesmypetcare.usermanager.datacontainers.pet.VetVisit> libraryVisit =
            ServiceLocator.getInstance().getPetCollectionsManagerClient().getAllVetVisits(accessToken, owner, petName);
        for (org.pesmypetcare.usermanager.datacontainers.pet.VetVisit visit : libraryVisit) {
            appVisits.add(new VetVisit(DateTime.Builder.buildFullString(visit.getKey()), visit.getBody().getAddress(),
                visit.getBody().getReason()));
        }
        return appVisits;
    }

    @Override
    public void createVetVisit(User user, Pet pet, VetVisit vetVisit) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VetVisitData visitData = new VetVisitData(vetVisit.getReason(), vetVisit.getAddress());
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit libraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(vetVisit.getVisitDate().toString(), visitData);

        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.VET_VISITS, libraryVisit.getKey(), libraryVisit.getBodyAsMap());
    }

    @Override
    public void deleteVetVisit(User user, Pet pet, VetVisit vetVisit) throws ExecutionException, InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        VetVisitData visitData = new VetVisitData(vetVisit.getReason(), vetVisit.getAddress());
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit libraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(vetVisit.getVisitDate().toString(), visitData);
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
            PetData.VET_VISITS, libraryVisit.getKey());
    }

    @Override
    public void updateVetVisitKey(User user, Pet pet, String newDate, DateTime visitDate) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();
        VetVisitData vetVisitData = ServiceLocator.getInstance().getPetCollectionsManagerClient().getVetVisit(
            accessToken, owner, petName, visitDate.toString());
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit oldLibraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(visitDate.toString(), vetVisitData);
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit newLibraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(newDate, vetVisitData);
        ServiceLocator.getInstance().getPetManagerClient().deleteFieldCollectionElement(accessToken, owner, petName,
            PetData.VET_VISITS, oldLibraryVisit.getKey());
        ServiceLocator.getInstance().getPetManagerClient().addFieldCollectionElement(accessToken, owner, petName,
            PetData.VET_VISITS, newLibraryVisit.getKey(), newLibraryVisit.getBodyAsMap());
    }

    @Override
    public void updateVetVisitBody(User user, Pet pet, VetVisit vetVisit) throws ExecutionException,
        InterruptedException {
        String accessToken = user.getToken();
        String owner = user.getUsername();
        String petName = pet.getName();

        VetVisitData visitData = new VetVisitData(vetVisit.getReason(), vetVisit.getAddress());
        org.pesmypetcare.usermanager.datacontainers.pet.VetVisit libraryVisit =
            new org.pesmypetcare.usermanager.datacontainers.pet.VetVisit(vetVisit.getVisitDate().toString(), visitData);
        ServiceLocator.getInstance().getPetManagerClient().updateFieldCollectionElement(accessToken, owner, petName,
            PetData.VET_VISITS, libraryVisit.getKey(), libraryVisit.getBodyAsMap());
    }
}
