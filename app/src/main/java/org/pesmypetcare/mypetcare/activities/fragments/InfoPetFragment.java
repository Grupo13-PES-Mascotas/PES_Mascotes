package org.pesmypetcare.mypetcare.activities.fragments;

import android.graphics.Bitmap;
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

import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.communication.InfoPetCommunication;
import org.pesmypetcare.mypetcare.activities.views.CircularImageView;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetBinding;

import java.util.Objects;


public class InfoPetFragment extends Fragment {
    private FragmentInfoPetBinding binding;
    private Button birthDate;
    private Boolean modified;
    private String newWeight;
    private String newName;
    private String newBreed;
    private String newGender;
    private CircularImageView petProfileImage;
    private InfoPetCommunication communication;
    public static Bitmap bitmap;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        modified = false;
        binding = FragmentInfoPetBinding.inflate(inflater, container, false);
        communication = (InfoPetCommunication) getActivity();

        updatePetListeners();
        setCalendarPicker();
        setGenderDropdownMenu();
        setPetProfileImage();

        return binding.getRoot();
    }

    private void setPetProfileImage() {
       petProfileImage = binding.imgPet;

       if (bitmap != null) {
           petProfileImage.setImage(bitmap);
       }

       petProfileImage.setOnClickListener(view -> {
           communication.makeZoomImage(petProfileImage.getBitmap());
       });
    }

    public static void setProfileImage(Bitmap image) {
        bitmap = image;
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
        binding.Gender.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            newGender = Objects.requireNonNull(binding.Gender.getEditText()).getText().toString();
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
        binding.PetName.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            newName = Objects.requireNonNull(binding.PetName.getEditText()).getText().toString();
        });
    }

    /**
     * Initializes the listeners for the new_weight variable.
     */
    private void modifiedWeight() {
        binding.Weight.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            newWeight = Objects.requireNonNull(binding.Weight.getEditText()).getText().toString();
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

    public void setPetImage(Bitmap bitmap) {
        petProfileImage.setImage(bitmap);
    }
}
