package org.pesmypetcare.mypetcare.controllers.pet;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.pet.TrObtainAllPetImages;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;

import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author Albert Pinto
 */
public class TestTrObtainAllPetImages {
    private static final int BLUE_COLOR = 0x0000FF;
    private User user;
    private TrObtainAllPetImages trObtainAllPetImages;

    @Before
    public void setUp() {
        user = new User("johnDoe2", "johndoe@gmail.com", "1234");
        trObtainAllPetImages = new TrObtainAllPetImages(new StubPetManagerService());

        for (int index = 0; index < 2; ++index) {
            Pet pet = new Pet("pet" + index);
            user.addPet(pet);
        }
    }

    @Test
    public void shouldGetAllImages() {
        trObtainAllPetImages.setUser(user);
        trObtainAllPetImages.execute();
        Map<String, byte[]> images = trObtainAllPetImages.getResult();

        images.forEach((name, color) -> assertArrayEquals("Should color be blue", new byte[]{
            (byte) BLUE_COLOR, (byte) BLUE_COLOR}, color));
    }
}
