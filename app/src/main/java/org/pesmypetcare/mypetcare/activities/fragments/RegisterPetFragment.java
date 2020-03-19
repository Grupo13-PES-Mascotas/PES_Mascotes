package org.pesmypetcare.mypetcare.activities.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.databinding.FragmentRegisterPetBinding;

import java.util.Objects;

@SuppressWarnings("unchecked")
public class RegisterPetFragment extends Fragment {
    private FragmentRegisterPetBinding binding;
    private MaterialDatePicker materialDatePicker;
    private Button birthDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterPetBinding.inflate(inflater, container, false);

        setCalendarPicker();
        setGenderDropdownMenu();
        setUpAddNewPetButton();

        return binding.getRoot();
    }

    private void setUpAddNewPetButton() {
        /*MaterialButton btnNewPet = binding.btnAddPet;
        btnNewPet.addOnCheckedChangeListener((button, isChecked) -> {
            if (isChecked) {
                System.out.println("Button pressed");
            }
            else {
                System.out.println("Button no pressed");
            }
        });*/
    }

    private void setGenderDropdownMenu() {
        AutoCompleteTextView gender = binding.inputGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.male), getString(R.string.female)});
        gender.setAdapter(adapter);
    }

    private void setCalendarPicker() {
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.select_birth_date));
        materialDatePicker = builder.build();

        birthDate = binding.inputBirthMonth;
        birthDate.setOnClickListener(v ->
            materialDatePicker.show(Objects.requireNonNull(getFragmentManager()), "DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {
            birthDate.setText(materialDatePicker.getHeaderText());
        });
    }
}
