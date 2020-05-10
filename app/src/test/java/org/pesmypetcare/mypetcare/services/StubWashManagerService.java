package org.pesmypetcare.mypetcare.services;

import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.Wash;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Enric Hernando
 */
public class StubWashManagerService implements WashManagerService {

    private static final String JOHN_DOE = "johnDoe";
    private static final String LINUX = "Linux";
    private static final String MEAL_NAME = "Meal";
    private static final int YEAR = 2020;
    private static final int MONTH = 2;
    private static final int DAY = 26;
    private static final int HOUR = 15;
    private static final int MINUTES = 23;
    private static final int SECONDS = 56;
    public static int nWash;
    public static Wash currentWash;
    private static DateTime washDate;
    private static int washDuration = 75;
    private Map<String, ArrayList<Wash>> data;

    public StubWashManagerService() {
        this.data = new HashMap<>();
        try {
            washDate = DateTime.Builder.build(YEAR, MONTH, DAY, HOUR, MINUTES, SECONDS);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }

        this.data.put(JOHN_DOE + " : " + LINUX, new ArrayList<>());
        for (int i = 0; i < 2; ++i) {
            Objects.requireNonNull(this.data.get(JOHN_DOE + " : " + LINUX))
                    .add(new Wash(washDate, washDuration, MEAL_NAME + i));
            washDate.increaseDay();
        }
    }

    private Wash getTestWash() {
        DateTime date = null;
        try {
            date = DateTime.Builder.build(2020, 2, 26, 15, 23, 56);
        } catch (org.pesmypetcare.usermanagerlib.exceptions.InvalidFormatException e) {
            e.printStackTrace();
        }
        assert date != null;
        return new Wash(date, 53, "Linux meal");
    }

    @Override
    public void createWash(User user, Pet pet, Wash wash) {
        data.putIfAbsent(user.getUsername() + " : " + pet.getName(), new ArrayList<>());
        Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).add(wash);
        ++nWash;
    }

    @Override
    public void deleteWash(User user, Pet pet, Wash wash) {
        ArrayList<Wash> petWash = data.get(user.getUsername() + " : " + pet.getName());
        assert petWash != null;
        petWash.remove(wash);
        --nWash;
    }

    @Override
    public List<Wash> findWashesByPet(User user, Pet pet) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            return data.get(user.getUsername() + " : " + pet.getName());
        }
        return null;
    }

    @Override
    public void updateWashBody(User user, Pet pet, Wash wash) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            ArrayList<Wash> petWash = data.get(user.getUsername() + " : " + pet.getName());
            assert petWash != null;
            petWash.remove(getTestWash());
        }
        this.createWash(user, pet, wash);
        StubWashManagerService.currentWash = wash;
    }

    @Override
    public void updateWashDate(User user, Pet pet, String newDate, String oldDate) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            ArrayList<Wash> petWash = data.get(user.getUsername() + " : " + pet.getName());
            assert petWash != null;
            for (Wash m:petWash) {
                if (m.getDateTime().equals(oldDate)) {
                    petWash.remove(m);
                }
            }
        }

        this.createWash(user, pet, new Wash(DateTime.Builder.buildFullString(newDate), washDuration,
                MEAL_NAME + MINUTES));

    }

    @Override
    public void deleteWashesFromPet(User user, Pet pet) {
        if (data.containsKey(user.getUsername() + " : " + pet.getName())) {
            Objects.requireNonNull(data.get(user.getUsername() + " : " + pet.getName())).clear();
        }
        nWash = 0;
    }
}
