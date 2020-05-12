package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.VaccinationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xavier Campos
 */
public class StubMedicalProfileManagerService implements MedicalProfileManagerService {
    private static final String USERNAME = "Manolo Lama";
    private static final String PET1 = "Bichinho";
    private static final String PET2 = "Comandante";
    private static final String SPACE_KOLIN = " : ";
    private Map<String, ArrayList<Vaccination>> data;
    public static int nVaccinations = 3;

    public StubMedicalProfileManagerService() {
        addStubDefaultData();
    }

    public void addStubDefaultData() {
        this.data = new HashMap<>();
        this.data.put(USERNAME + SPACE_KOLIN + PET1, new ArrayList<>());
        this.data.put(USERNAME + SPACE_KOLIN + PET2, new ArrayList<>());
        Objects.requireNonNull(this.data.get(USERNAME + SPACE_KOLIN + PET1)).add(new Vaccination(
            "Vacuna ebola", DateTime.Builder.buildDateString("2020-04-15")));
        Objects.requireNonNull(this.data.get(USERNAME + SPACE_KOLIN + PET1)).add(new Vaccination(
            "Vacuna malaria", DateTime.Builder.buildDateString("2020-06-11")));
        Objects.requireNonNull(this.data.get(USERNAME + SPACE_KOLIN + PET2)).add(new Vaccination(
            "Vacuna sida",DateTime.Builder.buildDateString("2020-11-29")));
    }


    @Override
    public List<Vaccination> findVaccinationsByPet(User user, Pet pet) {
        if (data.containsKey(user.getUsername() + SPACE_KOLIN + pet.getName())) {
            return data.get(user.getUsername() + SPACE_KOLIN + pet.getName());
        }
        return null;
    }

    @Override
    public void createVaccination(User user, Pet pet, Vaccination vaccination)
        throws VaccinationAlreadyExistingException {
        if (Objects.requireNonNull(data.get(user.getUsername() + SPACE_KOLIN + pet.getName())).contains(vaccination)) {
            throw new VaccinationAlreadyExistingException();
        }

        data.putIfAbsent(user.getUsername() + SPACE_KOLIN + pet.getName(), new ArrayList<>());
        Objects.requireNonNull(data.get(user.getUsername() + SPACE_KOLIN + pet.getName())).add(vaccination);
        nVaccinations++;
    }

    @Override
    public void deleteVaccination(User user, Pet pet, Vaccination vaccination) {
        Objects.requireNonNull(data.get(user.getUsername() + SPACE_KOLIN + pet.getName())).remove(vaccination);
        nVaccinations--;
    }

    @Override
    public void updateVaccinationKey(User user, Pet pet, String newDate, DateTime vaccinationDate) {
        ArrayList<Vaccination> petVisits = Objects.requireNonNull(data.get(user.getUsername() + SPACE_KOLIN
            + pet.getName()));
        for (Vaccination vaccination:petVisits) {
            if (vaccination.getVaccinationDate().compareTo(vaccinationDate) == 0) {
                vaccination.setVaccinationDate(DateTime.Builder.buildFullString(newDate));
            }
        }
    }

    @Override
    public void updateVaccinationBody(User user, Pet pet, Vaccination vaccination) {
        ArrayList<Vaccination> petVaccinations = Objects.requireNonNull(data.get(user.getUsername() + SPACE_KOLIN
            + pet.getName()));
        for (Vaccination serverVacc:petVaccinations) {
            if (serverVacc.getVaccinationDate().compareTo(vaccination.getDateTime()) == 0) {
                serverVacc.setVaccinationDescription(vaccination.getVaccinationDescription());
            }
        }
    }
}

