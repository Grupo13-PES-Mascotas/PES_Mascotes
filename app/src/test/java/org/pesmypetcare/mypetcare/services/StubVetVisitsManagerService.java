package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.VetVisit;
import org.pesmypetcare.mypetcare.features.pets.VetVisitAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanager.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xavier Campos
 */
public class StubVetVisitsManagerService implements VetVisitsManagerService {
    private static final String USERNAME = "Manolo Lama";
    private static final String PET1 = "Bichinho";
    private static final String PET2 = "Comandante";
    public static VetVisit currentVisit;
    public static int nVetVisit = 3;
    private Map<String, ArrayList<VetVisit>> data;

    public StubVetVisitsManagerService() {
        addStubDefaultData();
    }

    public void addStubDefaultData() {
        this.data = new HashMap<>();
        this.data.put(USERNAME + " : " + PET1, new ArrayList<>());
        this.data.put(USERNAME + " : " + PET2, new ArrayList<>());
        Objects.requireNonNull(this.data.get(USERNAME + " : " + PET1)).add(new VetVisit(
            DateTime.Builder.buildDateString("2020-04-15"), "Carrer Major 15", "Ebola"));
        Objects.requireNonNull(this.data.get(USERNAME + " : " + PET1)).add(new VetVisit(
            DateTime.Builder.buildDateString("2020-06-11"), "Avinguda Ramon Berenguer 31", "VIH"));
        Objects.requireNonNull(this.data.get(USERNAME + " : " + PET2)).add(new VetVisit(
            DateTime.Builder.buildDateString("2020-11-29"), "Carrer Major 15", "Combustió Espontànea"));
    }


    @Override
    public List<VetVisit> findVetVisitsByPet(User user, Pet pet) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            return data.get(user.getUsername() + " : " + pet.getName());
        }
        return null;
    }

    @Override
    public void createVetVisit(User user, Pet pet, VetVisit vetVisit) throws VetVisitAlreadyExistingException {
        if (Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).contains(vetVisit)) {
            throw new VetVisitAlreadyExistingException();
        }

        data.putIfAbsent(user.getUsername() + " : " + pet.getName(), new ArrayList<>());
        Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).add(vetVisit);
        nVetVisit++;
    }

    @Override
    public void deleteVetVisit(User user, Pet pet, VetVisit vetVisit) {
        Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).remove(vetVisit);
        nVetVisit--;
    }

    @Override
    public void updateVetVisitKey(User user, Pet pet, String newDate, DateTime visitDate) {
        ArrayList<VetVisit> petVisits = Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName()));
        for (VetVisit visit:petVisits) {
            if (visit.getVisitDate().compareTo(visitDate) == 0) {
                visit.setVisitDate(DateTime.Builder.buildFullString(newDate));
            }
        }
    }

    @Override
    public void updateVetVisitBody(User user, Pet pet, VetVisit vetVisit) {
        ArrayList<VetVisit> petVisits = Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName()));
        for (VetVisit visit:petVisits) {
            if (visit.getVisitDate().compareTo(vetVisit.getDateTime()) == 0) {
                Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).remove(visit);
                Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).add(vetVisit);
            }
        }
    }
}

