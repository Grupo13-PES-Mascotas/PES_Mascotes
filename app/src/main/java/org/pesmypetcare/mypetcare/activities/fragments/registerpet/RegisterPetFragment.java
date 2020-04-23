package org.pesmypetcare.mypetcare.activities.fragments.registerpet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentRegisterPetBinding;
import org.pesmypetcare.mypetcare.features.pets.Pet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class RegisterPetFragment extends Fragment {
    private FragmentRegisterPetBinding binding;
    private MaterialDatePicker materialDatePicker;
    private RegisterPetCommunication communication;
    private Button birthDate;
    private boolean isBirthDateSelected;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterPetBinding.inflate(inflater, container, false);
        communication = (RegisterPetCommunication) getActivity();
        isBirthDateSelected = false;

        setCalendarPicker();
        setGenderDropdownMenu();
        setUpAddNewPetButton();

        return binding.getRoot();
    }

    /**
     * Sets up the new pet button.
     */
    private void setUpAddNewPetButton() {
        MaterialButton btnNewPet = binding.btnAddPet;
        btnNewPet.setOnClickListener(v -> {
            if (isAnyFieldBlank()) {
                Toast errorMsg = Toast.makeText(getActivity(), R.string.error_empty_field, Toast.LENGTH_LONG);
                errorMsg.show();
            } else {
                Bundle petInfo = getPetInfoBundle();
                communication.addNewPet(petInfo);
            }
        });
    }

    /**
     * Creates a bundle to passing the pets information into the activity class.
     * @return A bundle that contains the information of the pet
     */
    private Bundle getPetInfoBundle() {
        Bundle petInfo = new Bundle();
        petInfo.putString(Pet.BUNDLE_NAME, Objects.requireNonNull(binding.inputPetName.getText()).toString());
        petInfo.putString(Pet.BUNDLE_GENDER, Objects.requireNonNull(binding.inputGender.getText()).toString());
        petInfo.putString(Pet.BUNDLE_BREED, Objects.requireNonNull(binding.inputBreed.getText()).toString());
        petInfo.putString(Pet.BUNDLE_BIRTH_DATE, binding.inputBirthMonth.getText().toString());
        petInfo.putFloat(Pet.BUNDLE_WEIGHT, Float.parseFloat(Objects.requireNonNull(binding.inputWeight.getText())
            .toString()));
        petInfo.putString(Pet.BUNDLE_PATHOLOGIES, Objects.requireNonNull(binding.inputPathologies.getText())
            .toString());
        petInfo.putInt(Pet.BUNDLE_WASH, Integer.parseInt(Objects.requireNonNull(binding.inputWashFrequency.getText())
            .toString()));

        return petInfo;
    }

    /**
     * Check whether there is any blank field except for pathologies.
     * @return True if there is any blank field except for pathologies
     */
    private boolean isAnyFieldBlank() {
        boolean isEmptyNameGenderBreed = isEmpty(binding.inputPetName) || isEmpty(binding.inputGender)
            || isEmpty(binding.inputBreed);
        boolean isEmptyWeightCaloriesWash = isEmpty(binding.inputWeight) || isEmpty(binding.inputWashFrequency);

        return isEmptyNameGenderBreed || isEmptyWeightCaloriesWash || !isBirthDateSelected;
    }

    /**
     * Checks whether an EditText is empty or not.
     * @param input EditText to check
     * @return True if input is empty
     */
    private boolean isEmpty(EditText input) {
        return "".equals(input.getText().toString());
    }

    /**
     * Sets the gender drop down menu.
     */
    private void setGenderDropdownMenu() {
        AutoCompleteTextView gender = binding.inputGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.male), getString(R.string.female),
            getString(R.string.other)});
        gender.setAdapter(adapter);
    }

    /**
     * Sets the calendar picker.
     */
    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_birth_date));
        materialDatePicker = builder.build();

        birthDate = binding.inputBirthMonth;
        birthDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(Long.parseLong(selection.toString()));
            String formattedDate = simpleDateFormat.format(calendar.getTime());
            birthDate.setText(formattedDate);
            isBirthDateSelected = true;
        });
    }
}
