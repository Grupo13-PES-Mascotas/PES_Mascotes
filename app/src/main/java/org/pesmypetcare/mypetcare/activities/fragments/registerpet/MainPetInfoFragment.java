package org.pesmypetcare.mypetcare.activities.fragments.registerpet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentMainPetInfoBinding;

import java.lang.reflect.Array;
import java.util.Objects;

@SuppressWarnings("unchecked")
public class MainPetInfoFragment extends Fragment {
    private FragmentMainPetInfoBinding binding;
    private MaterialDatePicker materialDatePicker;
    private Button birthDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMainPetInfoBinding.inflate(inflater, container, false);

        setCalendarPicker();

        AutoCompleteTextView gender = binding.inputGender;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Objects.requireNonNull(getContext()),
            R.layout.drop_down_menu_item, new String[] {getString(R.string.male), getString(R.string.female)});
        gender.setAdapter(adapter);

        System.out.println("Height: " + binding.inputPetName.getHeight());

        return binding.getRoot();
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
