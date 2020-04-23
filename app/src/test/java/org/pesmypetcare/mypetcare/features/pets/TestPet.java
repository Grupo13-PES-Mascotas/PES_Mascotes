package org.pesmypetcare.mypetcare.features.pets;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.utilities.DateConversion;
import org.pesmypetcare.usermanagerlib.datacontainers.DateTime;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestPet {
    private static final String DATE = "2020-04-03";
    private User user;
    private Pet pet;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getLinuxPet();
        DateTime date = DateTime.Builder.buildFullString("2020-04-02T10:30:00");
        DateTime date2 = DateTime.Builder.buildFullString("2020-04-02T11:00:00");
        DateTime date3 = DateTime.Builder.buildFullString("2020-04-03T10:30:00");
        pet.addEvent(new Event("Take to vet", date));
        pet.addEvent(new Event("Take to my mother's house", date2));
        pet.addEvent(new Event("Take to hairdresser", date3));

        user.addPet(pet);
    }

    @Test
    public void shouldDisplayEventDate() {
        List<Event> events = pet.getEvents(DATE);
        assertEquals("Should display event date", DATE,
            DateConversion.getDate(events.get(0).getDateTime().toString()));
    }

    private Pet getLinuxPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Linux");
        pet.setGender(GenderType.Male);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}
