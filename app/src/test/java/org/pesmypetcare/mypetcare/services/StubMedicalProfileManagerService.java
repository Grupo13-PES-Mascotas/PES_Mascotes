package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Illness;
import org.pesmypetcare.mypetcare.features.pets.IllnessAlreadyExistingException;
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
    private Map<String, ArrayList<Vaccination>> vaccinationData;
    private Map<String, ArrayList<Illness>> illnessData;
    public static int nVaccinations = 3;
    public static int nIllnesses = 3;

    public StubMedicalProfileManagerService() {
        addStubDefaultData();
    }

    public void addStubDefaultData() {
        addVaccinationDefaultData();
        addIllnessDefaultData();
    }

    private void addIllnessDefaultData() {
        this.illnessData = new HashMap<>();
        this.illnessData.put(USERNAME + SPACE_KOLIN + PET1, new ArrayList<>());
        this.illnessData.put(USERNAME + SPACE_KOLIN + PET2, new ArrayList<>());
        Objects.requireNonNull(this.illnessData.get(USERNAME + SPACE_KOLIN + PET2)).add(new Illness("Infecció renal",
            DateTime.Builder.buildDateString("2022-03-14"), DateTime.Builder.buildDateString("2023-05-23"),
            "Infecció", "Lleu"));
        Objects.requireNonNull(this.illnessData.get(USERNAME + SPACE_KOLIN + PET2)).add(new Illness("Malatia xunga",
            DateTime.Builder.buildDateString("2020-12-21"), DateTime.Builder.buildDateString("2021-01-15"),
            "Terminal", "Greu"));
        Objects.requireNonNull(this.illnessData.get(USERNAME + SPACE_KOLIN + PET1)).add(new Illness(
            "Vacuna ebola", DateTime.Builder.buildDateString("2020-04-17"),
            DateTime.Builder.buildDateString("2020-04-18"), SeverityType.Medium.toString(),
            IllnessType.Allergy.toString()));
    }

    private void addVaccinationDefaultData() {
        this.vaccinationData = new HashMap<>();
        this.vaccinationData.put(USERNAME + SPACE_KOLIN + PET1, new ArrayList<>());
        this.vaccinationData.put(USERNAME + SPACE_KOLIN + PET2, new ArrayList<>());
        Objects.requireNonNull(this.vaccinationData.get(USERNAME + SPACE_KOLIN + PET1)).add(new Vaccination(
            "Vacuna ebola", DateTime.Builder.buildDateString("2020-04-15")));
        Objects.requireNonNull(this.vaccinationData.get(USERNAME + SPACE_KOLIN + PET1)).add(new Vaccination(
            "Vacuna malaria", DateTime.Builder.buildDateString("2020-06-11")));
        Objects.requireNonNull(this.vaccinationData.get(USERNAME + SPACE_KOLIN + PET2)).add(new Vaccination(
            "Vacuna sida",DateTime.Builder.buildDateString("2020-11-29")));
    }


    @Override
    public List<Vaccination> findVaccinationsByPet(User user, Pet pet) {
        if (vaccinationData.containsKey(user.getUsername() + SPACE_KOLIN + pet.getName())) {
            return vaccinationData.get(user.getUsername() + SPACE_KOLIN + pet.getName());
        }
        return null;
    }

    @Override
    public void createVaccination(User user, Pet pet, Vaccination vaccination)
        throws VaccinationAlreadyExistingException {
        if (Objects.requireNonNull(vaccinationData.get(user.getUsername() + SPACE_KOLIN + pet.getName()))
            .contains(vaccination)) {
            throw new VaccinationAlreadyExistingException();
        }

        vaccinationData.putIfAbsent(user.getUsername() + SPACE_KOLIN + pet.getName(), new ArrayList<>());
        Objects.requireNonNull(vaccinationData.get(user.getUsername() + SPACE_KOLIN + pet.getName())).add(vaccination);
        nVaccinations++;
    }

    @Override
    public void deleteVaccination(User user, Pet pet, Vaccination vaccination) {
        Objects.requireNonNull(vaccinationData.get(user.getUsername() + SPACE_KOLIN + pet.getName()))
            .remove(vaccination);
        nVaccinations--;
    }

    @Override
    public void updateVaccinationKey(User user, Pet pet, String newDate, DateTime vaccinationDate) {
        ArrayList<Vaccination> petVisits = Objects.requireNonNull(vaccinationData.get(user.getUsername() + SPACE_KOLIN
            + pet.getName()));
        for (Vaccination vaccination:petVisits) {
            if (vaccination.getVaccinationDate().compareTo(vaccinationDate) == 0) {
                vaccination.setVaccinationDate(DateTime.Builder.buildFullString(newDate));
            }
        }
    }

    @Override
    public void updateVaccinationBody(User user, Pet pet, Vaccination vaccination) {
        ArrayList<Vaccination> petVaccinations = Objects.requireNonNull(vaccinationData.get(user.getUsername()
            + SPACE_KOLIN
            + pet.getName()));
        for (Vaccination serverVacc:petVaccinations) {
            if (serverVacc.getVaccinationDate().compareTo(vaccination.getDateTime()) == 0) {
                serverVacc.setDescription(vaccination.getDescription());
            }
        }
    }

    @Override
    public void createIllness(User user, Pet pet, Illness illness) throws IllnessAlreadyExistingException {
        if (Objects.requireNonNull(illnessData.get(user.getUsername() + SPACE_KOLIN + pet.getName())).
            contains(illness)) {
            throw new IllnessAlreadyExistingException();
        }

        illnessData.putIfAbsent(user.getUsername() + SPACE_KOLIN + pet.getName(), new ArrayList<>());
        Objects.requireNonNull(illnessData.get(user.getUsername() + SPACE_KOLIN + pet.getName())).add(illness);
        nIllnesses++;
    }

    @Override
    public void updateIllnessBody(User user, Pet pet, Illness illness) {
        ArrayList<Illness> petIllnesses = Objects.requireNonNull(illnessData.get(user.getUsername() + SPACE_KOLIN
            + pet.getName()));
        for (Illness serverIllness:petIllnesses) {
            if (serverIllness.getDateTime().compareTo(illness.getDateTime()) == 0) {
                serverIllness.setDescription(illness.getDescription());
                serverIllness.setType(illness.getType());
                serverIllness.setEndTime(illness.getEndTime());
                serverIllness.setSeverity(illness.getSeverity());
            }
        }
    }

    @Override
    public void updateIllnessKey(User user, Pet pet, String newDate, DateTime dateTime) {
        ArrayList<Illness> petIllnesses = Objects.requireNonNull(illnessData.get(user.getUsername() + SPACE_KOLIN
            + pet.getName()));
        for (Illness serverIllness:petIllnesses) {
            if (serverIllness.getDateTime().compareTo(dateTime) == 0) {
                serverIllness.setDateTime(DateTime.Builder.buildFullString(newDate));
            }
        }
    }

    @Override
    public List<Illness> findIllnessesByPet(User user, Pet pet) {
        if (illnessData.containsKey(user.getUsername() + SPACE_KOLIN + pet.getName())) {
            return illnessData.get(user.getUsername() + SPACE_KOLIN + pet.getName());
        }
        return null;
    }

    @Override
    public void deleteIllness(User user, Pet pet, Illness illness) {
        Objects.requireNonNull(illnessData.get(user.getUsername() + SPACE_KOLIN + pet.getName())).remove(illness);
        nIllnesses--;
    }
}

