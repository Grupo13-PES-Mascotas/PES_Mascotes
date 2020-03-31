package org.pesmypetcare.mypetcare.features.pets;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestPet {
    private User user;
    private Pet pet;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getLinuxPet();
        pet.addEvent(new Event("Take to vet", "2020-04-02T10:30:00"));
        pet.addEvent(new Event("Take to my mother's house", "2020-04-02T11:00:00"));
        pet.addEvent(new Event("Take to hairdresser", "2020-04-03T10:30:00"));

        user.addPet(pet);
    }

    @Test
    public void shouldDisplayEventDate() {
        List<Event> events = pet.getEvents("2020-04-03");
        assertEquals("Should display event date", "2020-04-03",
            DateConversion.getDate(events.get(0).getDateTime()));
    }

    private Pet getLinuxPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Linux");
        pet.setGender(GenderType.Male);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}
