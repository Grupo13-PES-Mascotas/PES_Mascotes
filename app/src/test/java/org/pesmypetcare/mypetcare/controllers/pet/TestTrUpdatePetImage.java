package org.pesmypetcare.mypetcare.controllers.pet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.junit.Before;
import org.junit.Test;
import org.pesmypetcare.httptools.utilities.DateTime;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.users.NotPetOwnerException;
import org.pesmypetcare.mypetcare.features.users.User;
import org.pesmypetcare.mypetcare.services.StubPetManagerService;
import org.pesmypetcare.usermanager.datacontainers.pet.GenderType;

import static org.junit.Assert.assertEquals;

/**
 * @author Albert Pinto
 */
public class TestTrUpdatePetImage {
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private static final float RIGHT = 20.0f;
    private static final float BOTTOM = 20.0f;
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
        Bitmap bitmap = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(0.0f, 0.0f, RIGHT, BOTTOM, paint);
        return bitmap;
    }

    private Pet getDinkyPet() throws PetRepeatException {
        Pet pet = new Pet();
        pet.setName("Dinky");
        pet.setGender(GenderType.Female);
        pet.setBirthDate(DateTime.Builder.buildDateString("2020-03-02"));
        pet.setBreed("Husky");
        pet.setRecommendedDailyKiloCaloriesForCurrentDate(2);
        pet.setWashFrequencyForCurrentDate(2);
        pet.setWeightForCurrentDate(2);
        return pet;
    }
}
