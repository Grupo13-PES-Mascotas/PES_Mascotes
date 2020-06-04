package org.pesmypetcare.mypetcare.controllers.pet;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Albert Pinto
 */
public class TestTrGetPetImage {
    private static final int BLUE_COLOR = 0x0000FF;
    private User user;
    private Pet pet;
    private TrGetPetImage trGetPetImage;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();
        user.addPet(pet);
        trGetPetImage = new TrGetPetImage(new StubPetManagerService());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotDeletePetIfNotOwner() throws NotPetOwnerException {
        trGetPetImage.setUser(new User("johnSmith", "johnSmith@gmail.com", "5678"));
        trGetPetImage.setPet(pet);
        trGetPetImage.execute();
    }

    @Test
    public void shouldGetPetImage() throws NotPetOwnerException {
        trGetPetImage.setUser(user);
        trGetPetImage.setPet(pet);
        trGetPetImage.execute();

        assertArrayEquals("Should get the pet image", new byte[] {(byte) 0x0000FF}, trGetPetImage.getResult());
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Male);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        return pet;
    }
}
