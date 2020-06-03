package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.utilities.ImageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

/**
 * @author Daniel Clemente & Enric Hernando
 */

public class InfoPetFragment extends Fragment {
    public static final int INFO_PET_ZOOM_IDENTIFIER = 1;
    private static Pet pet = new Pet("Linux");
    private static Drawable petProfileDrawable;
    private static boolean isImageModified;
    private static Resources resources;
    private static boolean isDefaultPetImage;
    private static InfoPetCommunication communication;
    private static boolean isPetDeleted;
    private static FloatingActionButton sharePetInfoButton;
    private static final String PET_PROFILE_IMAGE_DESCRIPTION = "pet profile image";


    private FragmentInfoPetBinding binding;
    private InfoPetFragmentAdapter infoPetFragmentAdapter;
    private ViewPager2 viewPager;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        binding = FragmentInfoPetBinding.inflate(inflater, container, false);
        communication = (InfoPetCommunication) getActivity();
        resources = Objects.requireNonNull(getActivity()).getResources();
        isPetDeleted = false;

        sharePetInfoButton = binding.flSharePetInfo;
        setUpSharePetInfoListener();

        setUpViewPager();
        setUpPet();

        return binding.getRoot();
    }

    /**
     * Set the share app button listener.
     */
    private void setUpSharePetInfoListener() {
        sharePetInfoButton.setOnClickListener(v -> {
            View rootView = Objects.requireNonNull(this.getActivity()).getWindow().getDecorView()
                    .findViewById(android.R.id.content);
            Bitmap bm = getScreenShot(rootView);
            saveImage(bm);

        });
    }

    /**
     * Get a screenshot of the pet info.
     * @param view The view
     * @return The bitmap of the screenshot
     */
    private static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    /**
     * Saves the image as PNG to the app's cache directory and share.
     * @param image Bitmap to share.
     */
    private void saveImage(Bitmap image) {
        File file = saveBitmap(image);
        Uri uri = Uri.fromFile(file);
        shareUri(uri);
    }

    /**
     * Creates a intent to share the uri.
     * @param uri The uri to share
     */
    private void shareUri(Uri uri) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/jpeg");

        intent.putExtra(Intent.EXTRA_SUBJECT, "My Pet Care");
        intent.putExtra(Intent.EXTRA_TEXT, "");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(intent);
    }

    /**
     * Save a bitmap.
     * @param image The bitmap of the image
     * @return The file created
     */
    @NonNull
    private File saveBitmap(Bitmap image) {
        File file = fileCreation();
        FileOutputStream fOut;
        try {
            fOut = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Creation of the file.
     * @return The file
     */
    @NonNull
    private File fileCreation() {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyPetCare/ScreenShot";
        File dir = new File(file_path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        return new File(dir, pet.getName()+"Info.jpg");
    }

    /**
     * Set up the pet.
     */
    private void setUpPet() {
        Objects.requireNonNull(binding.petName.getEditText()).setText(pet.getName());
        setPetProfileImage();
    }

    /**
     * Sets the pet profile image.
     */
    private void setPetProfileImage() {
        binding.imgPet.setContentDescription(PET_PROFILE_IMAGE_DESCRIPTION);

        if (petProfileDrawable == null) {
            assignPetImageToDisplay();
            isImageModified = false;
        }

        binding.imgPet.setDrawable(petProfileDrawable);
        binding.imgPet.setOnClickListener(view -> communication.makeZoomImage(binding.imgPet.getDrawable()));
    }

    /**
     * Assigns the pet image to display.
     */
    private void assignPetImageToDisplay() {
        Bitmap petImage = pet.getProfileImage();

        if (petImage == null) {
            petProfileDrawable = new BitmapDrawable(getResources(), ImageManager.getDefaultPetImage());
        } else {
            petProfileDrawable = new BitmapDrawable(getResources(), petImage);
        }
    }

    /**
     * Set up the view pager.
     */
    private void setUpViewPager() {
        infoPetFragmentAdapter = new InfoPetFragmentAdapter(this);
        viewPager = binding.pager;
        viewPager.setAdapter(infoPetFragmentAdapter);

        TabLayout tabLayout = binding.tabLayoutInfoPet;
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch(position) {
                case InfoPetFragmentAdapter.INFO_PET_BASIC:
                    tab.setText(R.string.pet_info_basic);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_HEALTH:
                    tab.setText(R.string.pet_info_health);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_MEALS:
                    tab.setText(R.string.pet_info_meal);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_EXERCISE:
                    tab.setText(R.string.pet_info_exercise);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_WASHING:
                    tab.setText(R.string.pet_info_washing);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_MEDICAL_PROFILE:
                    tab.setText(R.string.pet_info_medical_profile);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_MEDICATION:
                    tab.setText(R.string.pet_info_medication);
                    break;
                case InfoPetFragmentAdapter.INFO_PET_VET_VISITS:
                    tab.setText(R.string.pet_info_vet_visits);
                    break;
                default:
            }
        });

        tabLayoutMediator.attach();
    }

    /**
     * Get the pet.
     * @return The pet
     */
    public static Pet getPet() {
        return pet;
    }

    /**
     * Set the pet.
     * @param pet The pet to set
     */
    public static void setPet(Pet pet) {
        InfoPetFragment.pet = pet;
        Drawable drawable = new BitmapDrawable(resources, ImageManager.getDefaultPetImage());
        isDefaultPetImage = true;

        if (pet.getProfileImage() != null) {
            drawable = new BitmapDrawable(resources, pet.getProfileImage());
            isDefaultPetImage = false;
        }

        isImageModified = isImageModified || !drawable.equals(petProfileDrawable);
        petProfileDrawable = drawable;
    }

    /**
     * Get the communication.
     * @return The communication
     */
    public static InfoPetCommunication getCommunication() {
        return communication;
    }

    /**
     * Set if the pet is deleted.
     * @param isPetDeleted The pet is deleted state
     */
    public static void setIsPetDeleted(boolean isPetDeleted) {
        InfoPetFragment.isPetDeleted = isPetDeleted;
    }

    /**
     * Set the default pet image.
     */
    public static void setDefaultPetImage() {
        pet.setProfileImage(null);
    }

    /**
     * Set the default pet image.
     * @param isDefaultPetImage The default pet image to set
     */
    public static void setIsDefaultPetImage(boolean isDefaultPetImage) {
        InfoPetFragment.isDefaultPetImage = isDefaultPetImage;
    }

    /**
     * Sets the pet profile image drawable.
     * @param drawable The pet profile drawable to set
     */
    public static void setPetProfileDrawable(Drawable drawable) {
        isImageModified = isImageModified || !drawable.equals(petProfileDrawable);
        petProfileDrawable = drawable;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!isPetDeleted && isImageModified) {
            Bitmap bitmap = null;

            if (pet.getProfileImage() == null) {
                Thread deleteImageThread = createDeleteImageThread();
                deleteImageThread.start();
            }

            if (!isDefaultPetImage) {
                bitmap = ((BitmapDrawable) binding.imgPet.getDrawable()).getBitmap();
            }

            communication.updatePetImage(pet, bitmap);
        }
    }

    /**
     * Create delete image thread.
     * @return The thread for deleting the image
     */
    private Thread createDeleteImageThread() {
        return new Thread(() -> ImageManager.deleteImage(ImageManager.PET_PROFILE_IMAGES_PATH,
                pet.getOwner().getUsername() + '_' + pet.getName()));
    }
}
