package org.pesmypetcare.mypetcare.activities.fragments;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import org.pesmypetcare.mypetcare.activities.communication.InfoPetCommunication;
import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetBinding;
import org.pesmypetcare.mypetcare.features.pets.Gender;
import org.pesmypetcare.mypetcare.features.pets.Pet;
import org.pesmypetcare.mypetcare.features.pets.UserIsNotOwnerException;

import java.util.Objects;


public class InfoPetFragment extends Fragment {
    private static Drawable petProfileDrawable;
    private static boolean isImageModified;
    private static Pet pet = new Pet("Linux");

    private FragmentInfoPetBinding binding;
    private Button birthDate;
    private boolean modified;
    private String newWeight;
    private String newName;
    private String newBreed;
    private String newGender;
    private CircularImageView petProfileImage;
    private InfoPetCommunication communication;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInfoPetBinding.inflate(inflater, container, false);
        communication = (InfoPetCommunication) getActivity();

        updatePetListeners();
        setCalendarPicker();
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
     * Sets the gender of the pet.
     */
    private void setGender() {
        if (pet.getGender() == Gender.MALE) {
            binding.inputGender.setText(R.string.male);
        } else {
            binding.inputGender.setText(R.string.female);
        }
    }

    /**
     * Sets the pet profile image.
     */
    private void setPetProfileImage() {
        petProfileImage = binding.imgPet;

        if (petProfileDrawable == null) {
            assignPetImageToDisplay();
            isImageModified = false;
        }

        petProfileImage.setDrawable(petProfileDrawable);

        petProfileImage.setOnClickListener(view -> {
            communication.makeZoomImage(petProfileImage.getDrawable());
        });
    }

    /**
     * Assigns the pet image to display.
     */
    private void assignPetImageToDisplay() {
        Bitmap petImage = pet.getProfileImage();

        if (petImage == null) {
            petProfileDrawable = getResources().getDrawable(R.drawable.single_paw);
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

    /**
     * Sets the pet from which we want to display its information.
     * @param pet The pet from which we want to display its information
     */
    public static void setPet(Pet pet) {
        InfoPetFragment.pet = pet;
    }

    /**
     * Initializes the listeners for update Pet.
     */
    private void updatePetListeners() {
        modifiedPet();
        binding.updatePet.setOnClickListener(v -> {
            if (modified) {
                Toast.makeText(getActivity(), newName, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), newBreed, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), newGender, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), newWeight, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Not implemented yet", Toast.LENGTH_LONG).show();
                modified = false;
            } else {
                Toast.makeText(getActivity(), "No changes", Toast.LENGTH_LONG).show();
            }
        });
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
        binding.gender.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            newGender = Objects.requireNonNull(binding.gender.getEditText()).getText().toString();
        });
    }

    /**
     * Initializes the listeners for the new_breed variable.
     */
    private void modifiedBreed() {
        binding.breed.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            newBreed = Objects.requireNonNull(binding.breed.getEditText()).getText().toString();
        });
    }

    /**
     * Initializes the listeners for the new_name variable.
     */
    private void modifiedName() {
        binding.petName.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            newName = Objects.requireNonNull(binding.petName.getEditText()).getText().toString();
        });
    }

    /**
     * Initializes the listeners for the new_weight variable.
     */
    private void modifiedWeight() {
        binding.weight.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            newWeight = Objects.requireNonNull(binding.weight.getEditText()).getText().toString();
        });
    }

    /**
     * Configure the Dropdown menu of gender.
     */
    private void setGenderDropdownMenu() {
        AutoCompleteTextView gender = binding.inputGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
                R.layout.drop_down_menu_item, new String[] {getString(R.string.male), getString(R.string.female)});
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
        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            birthDate.setText(materialDatePicker.getHeaderText());
        });
    }

    @Override
    public void onStop() {
        super.onStop();

        if (isImageModified) {
            Bitmap bitmap = null;

            if (hasNewImageDefined()) {
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
        return !petProfileImage.getDrawable().equals(getResources().getDrawable(R.drawable.single_paw));
    }

    /**
     * Configure the botton deleteButton to delete the pet.
     */
    private void setDeletePet() {
        MaterialButton delete = binding.deleteButton;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder dialogAlert = new MaterialAlertDialogBuilder(getActivity());
                configDialog(dialogAlert);
                configureNegativeButton(dialogAlert);
                dialogAlert.show();
            }
        });
    }

    /**
     * Configure negative button.
     */
    private void configureNegativeButton(MaterialAlertDialogBuilder dialogAlert) {
        dialogAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel(); }
        });
    }

    /**
     * Configure some atributtes of confirmation dialog to delete a pet and positive button.
     */
    void configDialog(MaterialAlertDialogBuilder dialogAlert) {
        dialogAlert.setTitle("Delete Pet")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            communication.deletePet(pet);
                        } catch (UserIsNotOwnerException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
