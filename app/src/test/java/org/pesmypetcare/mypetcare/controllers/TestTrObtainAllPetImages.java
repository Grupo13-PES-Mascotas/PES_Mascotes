package org.pesmypetcare.mypetcare.controllers;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;

import java.util.Map;

import static org.junit.Assert.assertArrayEquals;

public class TestTrObtainAllPetImages {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final float RIGHT = 20.0f;
    private static final float BOTTOM = 20.0f;

    private User user;
    private TrObtainAllPetImages trObtainAllPetImages;

    @Before
    public void setUp() throws PetRepeatException {
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
            (byte) 0x0000FF, (byte) 0x0000FF}, color));
    }
}
