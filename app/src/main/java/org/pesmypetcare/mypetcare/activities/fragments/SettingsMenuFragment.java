package org.pesmypetcare.mypetcare.activities.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.NewPasswordInterface;
import org.pesmypetcare.mypetcare.databinding.FragmentSettingsMenuBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsMenuFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentSettingsMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSettingsMenuBinding.inflate(getLayoutInflater());
        settingsOptionsListeners();
        return binding.getRoot();
    }

    /**
     * Initializes the listeners of the fragment.
     */
    private void settingsOptionsListeners() {
        ArrayAdapter<CharSequence> languages;
        languages = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.array.Languages, android.R.layout.simple_spinner_item);
        languages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.languageSelector.setAdapter(languages);
        binding.languageSelector.setOnItemSelectedListener(this);

        binding.logoutButton.setOnClickListener(v -> Toast.makeText(getActivity(),
                "Logout button clicked", Toast.LENGTH_LONG).show());
        binding.changePasswordButton.setOnClickListener(v -> {
            Activity thisActivity = getActivity();
            assert thisActivity != null;
            ((NewPasswordInterface) thisActivity).changeFragmentPass(new NewPassword());
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        if (parent.getSelectedItemPosition() != 0) {
            Toast.makeText(parent.getContext(), text + " is not implemented yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Unused method for our current tab
    }
}
