package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Illness;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Vaccination;
import org.pesmypetcare.mypetcare.features.pets.VaccinationAlreadyExistingException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.IllnessType;
import org.pesmypetcare.usermanagerlib.datacontainers.SeverityType;

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
    private Map<String, ArrayList<Illness>> data2;
    public static int nVaccinations = 3;
    public static int nIllnesses = 3;

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

        this.data2 = new HashMap<>();
        this.data2.put(USERNAME + SPACE_KOLIN + PET1, new ArrayList<>());
        this.data2.put(USERNAME + SPACE_KOLIN + PET2, new ArrayList<>());
        Objects.requireNonNull(this.data2.get(USERNAME + SPACE_KOLIN + PET1)).add(new Illness(
                "Vacuna ebola", DateTime.Builder.buildDateString("2020-04-17"),
                DateTime.Builder.buildDateString("2020-04-18"), SeverityType.Medium.toString(),
                IllnessType.Allergy.toString()));
        Objects.requireNonNull(this.data2.get(USERNAME + SPACE_KOLIN + PET1)).add(new Illness(
                "Vac23", DateTime.Builder.buildDateString("2020-04-19"),
                DateTime.Builder.buildDateString("2020-04-20"), SeverityType.Medium.toString(),
                IllnessType.Allergy.toString()));
        Objects.requireNonNull(this.data2.get(USERNAME + SPACE_KOLIN + PET2)).add(new Illness(
                "Vac24", DateTime.Builder.buildDateString("2020-04-21"),
                DateTime.Builder.buildDateString("2020-04-22"), SeverityType.Medium.toString(),
                IllnessType.Allergy.toString()));
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

    @Override
    public List<Illness> findIllnessesByPet(User user, Pet pet) {
        if (data2.containsKey(user.getUsername() + SPACE_KOLIN + pet.getName())) {
            return data2.get(user.getUsername() + SPACE_KOLIN + pet.getName());
        }
        return null;
    }

    @Override
    public void deleteIllness(User user, Pet pet, Illness illness) {
        Objects.requireNonNull(data2.get(user.getUsername() + SPACE_KOLIN + pet.getName())).remove(illness);
        nIllnesses--;
    }
}

