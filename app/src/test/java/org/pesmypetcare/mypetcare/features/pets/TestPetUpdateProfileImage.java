package org.pesmypetcare.mypetcare.features.pets;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.mypetcare.controllers.TrUpdatePetImage;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;

import static org.junit.Assert.assertEquals;

public class TestPetUpdateProfileImage {
    private User user;
    private Pet pet;
    private TrUpdatePetImage trUpdatePetImage;

    @Before
    public void setUp() throws PetRepeatException {
        user = new User("johnDoe", "johndoe@gmail.com", "1234");
        pet = getDinkyPet();

        pet.setProfileImage(getBitmap(Color.BLUE));
        user.addPet(pet);

        trUpdatePetImage = new TrUpdatePetImage(new StubPetManagerService());
    }

    @Test(expected = NotPetOwnerException.class)
    public void shouldNotUpdateImageOfNonOwnerPet() throws NotPetOwnerException {
        trUpdatePetImage.setUser(new User("arthurGalvin", "johngalvin@gmail.com", "5678"));
        trUpdatePetImage.setPet(pet);
        trUpdatePetImage.setNewPetImage(getBitmap(Color.GREEN));
        trUpdatePetImage.execute();
    }

    @Test
    public void shouldAddPetProfileImage() throws NotPetOwnerException {
        Bitmap newPetImage = getBitmap(Color.GREEN);
        trUpdatePetImage.setUser(user);
        trUpdatePetImage.setPet(pet);
        trUpdatePetImage.setNewPetImage(newPetImage);
        trUpdatePetImage.execute();

        assertEquals("Should add pet profileImage", newPetImage, pet.getProfileImage());
    }

    private Bitmap getBitmap(int color) {
        Bitmap bitmap = Bitmap.createBitmap(20, 20, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(0F, 0F, 20.0f, 20.0f, paint);
        return bitmap;
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(Gender.MALE);
        pet.setBirthDate("2 MAR 2020");
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCalories(2);
        pet.setWashFrequency(2);
        pet.setWeight(2);
        return pet;
    }
}
