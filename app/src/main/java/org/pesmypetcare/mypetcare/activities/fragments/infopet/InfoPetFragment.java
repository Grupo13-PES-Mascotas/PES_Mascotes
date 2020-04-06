package org.pesmypetcare.mypetcare.activities.fragments.infopet;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.PetRepeatException;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;
import org.pesmypetcare.mypetcare.utilities.ImageManager;
import org.pesmypetcare.usermanagerlib.datacontainers.GenderType;

import java.util.Objects;


public class InfoPetFragment extends Fragment {
    private static Drawable petProfileDrawable;
    private static boolean isImageModified;
    private static Pet pet = new Pet("Linux");
    private static Resources resources;
    private static boolean isDefaultPetImage;
    private static final String PET_PROFILE_IMAGE_DESCRIPTION = "pet profile image";

    private FragmentInfoPetBinding binding;
    private Button birthDate;
    private boolean modified;
    private String newWeight;
    private String newName;
    private String newBreed;
    private String newGender;
    private boolean isPetDeleted;
    private CircularImageView petProfileImage;
    private InfoPetCommunication communication;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetBinding.inflate(inflater, container, false);
        communication = (InfoPetCommunication) getActivity();
        resources = Objects.requireNonNull(getActivity()).getResources();
        isPetDeleted = false;

        updatePetListeners();
        //setCalendarPicker();
        setGenderDropdownMenu();
        setPetProfileImage();
        initializePetInfo();
        setDeletePet();
        modified = false;
        return binding.getRoot();
    }

    /**
     * Initialize the pet info.
     */
    private void initializePetInfo() {
        if (pet != null) {
            Objects.requireNonNull(binding.petName.getEditText()).setText(pet.getName());
            Objects.requireNonNull(binding.breed.getEditText()).setText(pet.getBreed());
            Objects.requireNonNull(binding.weight.getEditText()).setText(String.valueOf(pet.getWeight()));
            Objects.requireNonNull(binding.recommendedKcal.getEditText())
                .setText(String.valueOf(pet.getRecommendedDailyKiloCalories()));
            Objects.requireNonNull(binding.washFrequency.getEditText())
                .setText(String.valueOf(pet.getWashFrequency()));
            binding.inputBirthMonth.setText(pet.getBirthDate());

            setGender();
            setPathologies();
        }
    }

    /**
     * Sets the pet pathologies if exists.
     */
    private void setPathologies() {
        if (pet.getPathologies() != null) {
            Objects.requireNonNull(binding.pathologies.getEditText()).setText(pet.getPathologies());
        }
    }

    /**
     * Get the gender of the pet.
     * @return The gender of the pet
     */
    private GenderType getGender() {
        if (newGender.equals(getString(R.string.male))) {
            return GenderType.Male;
        }

        if (newGender.equals(getString(R.string.female))) {
            return GenderType.Female;
        }

        return GenderType.Other;
    }

    /**
     * Sets the gender of the pet.
     */
    private void setGender() {
        if (pet.getGender() == GenderType.Male) {
            binding.inputGender.setText(R.string.male);
        } else if (pet.getGender() == GenderType.Female) {
            binding.inputGender.setText(R.string.female);
        } else {
            binding.inputGender.setText(R.string.other);
        }
    }

    /**
     * Sets the pet profile image.
     */
    private void setPetProfileImage() {
        petProfileImage = binding.imgPet;
        petProfileImage.setContentDescription(PET_PROFILE_IMAGE_DESCRIPTION);

        if (petProfileDrawable == null) {
            assignPetImageToDisplay();
            isImageModified = false;
        }

        petProfileImage.setDrawable(petProfileDrawable);

        petProfileImage.setOnClickListener(view -> communication.makeZoomImage(petProfileImage.getDrawable()));
    }

    /**
     * Assigns the pet image to display.
     */
    private void assignPetImageToDisplay() {
        Bitmap petImage = pet.getProfileImage();

        if (petImage == null) {
            petProfileDrawable = getResources().getDrawable(R.drawable.single_paw, null);
        } else {
            petProfileDrawable = new BitmapDrawable(getResources(), petImage);
        }
    }

    /**
     * Sets the pet profile image drawable.
     * @param drawable The pet profile drawable to set
     */
    public static void setPetProfileDrawable(Drawable drawable) {
        isImageModified = isImageModified || !drawable.equals(petProfileDrawable);
        petProfileDrawable = drawable;
    }

    public static void setIsDefaultPetImage(boolean isDefaultPetImage) {
        InfoPetFragment.isDefaultPetImage = isDefaultPetImage;
    }

    /**
     * Sets the pet from which we want to display its information.
     * @param pet The pet from which we want to display its information
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

    public static void setDefaultPetImage() {
        pet.setProfileImage(null);
    }

    /**
     * Initializes the listeners for update Pet.
     */
    private void updatePetListeners() {
        modifiedPet();
        binding.updatePet.setOnClickListener(v -> {
            if (modified) {
                modified = false;
                try {
                    updatePet();
                    //communication.changeToMainView();
                } catch (PetRepeatException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getActivity(), "No changes", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Update the Pet.
     */
    private void updatePet() throws PetRepeatException {
        pet.setName(newName);
        pet.setGender(getGender());
        pet.setBreed(newBreed);
        pet.setWeight(Double.parseDouble(newWeight));

        try {
            communication.updatePet(pet);
        } catch (UserIsNotOwnerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initializes the listeners for the variable modified.
     */
    private void modifiedPet() {
        modifiedWeight();
        modifiedName();
        modifiedBreed();
        modifiedGender();
    }

    /**
     * Initializes the listeners for the new_gender variable.
     */
    private void modifiedGender() {
        Objects.requireNonNull(binding.gender.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                newGender = Objects.requireNonNull(binding.gender.getEditText()).getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Unused
            }
        });
    }

    /**
     * Initializes the listeners for the new_breed variable.
     */
    private void modifiedBreed() {
        Objects.requireNonNull(binding.breed.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                newBreed = Objects.requireNonNull(binding.breed.getEditText()).getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Unused
            }
        });
    }

    /**
     * Initializes the listeners for the new_name variable.
     */
    private void modifiedName() {
        Objects.requireNonNull(binding.petName.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                newName = Objects.requireNonNull(binding.petName.getEditText()).getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Unused
            }
        });
    }

    /**
     * Initializes the listeners for the new_weight variable.
     */
    private void modifiedWeight() {
        Objects.requireNonNull(binding.weight.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Unused
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modified = true;
                newWeight = Objects.requireNonNull(binding.weight.getEditText()).getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Unused
            }
        });
    }

    /**
     * Configure the Dropdown menu of gender.
     */
    private void setGenderDropdownMenu() {
        AutoCompleteTextView gender = binding.inputGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.male), getString(R.string.female),
            getString(R.string.other)});
        gender.setAdapter(adapter);
    }

    /**
     * Configure the Calendar picker of birthday.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.birth_random));
        MaterialDatePicker materialDatePicker = builder.build();
        birthDate = binding.inputBirthMonth;
        birthDate.setOnClickListener(v ->
                materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(selection ->
                birthDate.setText(materialDatePicker.getHeaderText()));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!isPetDeleted && isImageModified) {
            Bitmap bitmap = null;

            if (pet.getProfileImage() == null) {
                ImageManager.deleteImage(ImageManager.PET_PROFILE_IMAGES_PATH, pet.getOwner().getUsername() + '_'
                    + pet.getName());
            }

            if (!isDefaultPetImage) {
                bitmap = ((BitmapDrawable) petProfileImage.getDrawable()).getBitmap();
            }

            communication.updatePetImage(pet, bitmap);
        }
    }

    /**
     * Check whether the pet has a new image defined.
     * @return True if the pet has a new image defined
     */
    private boolean hasNewImageDefined() {
        return !petProfileImage.getDrawable().equals(getResources().getDrawable(R.drawable.single_paw, null));
    }

    /**
     * Configure the botton deleteButton to delete the pet.
     */
    private void setDeletePet() {
        MaterialButton delete = binding.deleteButton;
        delete.setOnClickListener(v -> {
            MaterialAlertDialogBuilder dialogAlert = new MaterialAlertDialogBuilder(Objects
                    .requireNonNull(getActivity()));
            configDialog(dialogAlert);
            configureNegativeButton(dialogAlert);
            dialogAlert.show();
        });
    }

    /**
     * Configure negative button.
     */
    private void configureNegativeButton(MaterialAlertDialogBuilder dialogAlert) {
        dialogAlert.setNegativeButton(getResources().getString(R.string.cancel),
            (dialog, which) -> dialog.cancel());
    }

    /**
     * Configure some attributes of confirmation dialog to delete a pet and positive button.
     */
    private void configDialog(MaterialAlertDialogBuilder dialogAlert) {
        dialogAlert.setTitle(getResources().getString(R.string.delete_pet)).setMessage(getResources()
            .getString(R.string.user_confirm)).setPositiveButton(getResources()
            .getString(R.string.affirmative_response), (dialog, which) -> {
                    try {
                        communication.deletePet(pet);
                        isPetDeleted = true;
                    } catch (UserIsNotOwnerException e) {
                        e.printStackTrace();
                    }
            });
    }
}
