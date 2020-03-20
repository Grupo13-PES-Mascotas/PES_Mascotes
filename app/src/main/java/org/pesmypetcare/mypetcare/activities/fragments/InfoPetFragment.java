package org.pesmypetcare.mypetcare.activities.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentInfoPetBinding;

import java.util.Objects;


public class InfoPetFragment extends Fragment {
    private FragmentInfoPetBinding binding;
    private Button birthDate;
    private Boolean modified;
    private String new_weight, new_name, new_breed, new_gender;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        modified = false;
        binding = FragmentInfoPetBinding.inflate(inflater, container, false);
        updatePetListeners();
        setCalendarPicker();
        setGenderDropdownMenu();
        return binding.getRoot();
    }

    /**
     * Initializes the listeners for update Pet.
     */
    private void updatePetListeners() {
        modifiedPet();
        binding.updatePet.setOnClickListener(v -> {
            if (modified) {
                Toast.makeText(getActivity(), new_name, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), new_breed, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), new_gender, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), new_weight, Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Not implemented yet", Toast.LENGTH_LONG).show();
                modified = false;
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
            new_gender = Objects.requireNonNull(binding.Gender.getEditText()).getText().toString();
        });
    }

    /**
     * Initializes the listeners for the new_breed variable.
     */
    private void modifiedBreed() {
        binding.breed.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            new_breed = Objects.requireNonNull(binding.breed.getEditText()).getText().toString();
        });
    }

    /**
     * Initializes the listeners for the new_name variable.
     */
    private void modifiedName() {
        binding.PetName.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            new_name = Objects.requireNonNull(binding.PetName.getEditText()).getText().toString();
        });
    }

    /**
     * Initializes the listeners for the new_weight variable.
     */
    private void modifiedWeight() {
        binding.Weight.addOnEditTextAttachedListener(textInputLayout -> {
            modified = true;
            new_weight = Objects.requireNonNull(binding.Weight.getEditText()).getText().toString();
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
}
