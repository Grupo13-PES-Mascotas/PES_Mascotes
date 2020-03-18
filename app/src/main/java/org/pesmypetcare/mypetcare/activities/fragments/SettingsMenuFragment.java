package org.pesmypetcare.mypetcare.activities.fragments;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.databinding.FragmentSettingsMenuBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsMenuFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentSettingsMenuBinding binding = FragmentSettingsMenuBinding.inflate(getLayoutInflater());
        settingsOptionsListeners(binding);
        return binding.getRoot();
    }

    /**
     * Initializes the listeners of the fragment.
     * @param binding The binding of the fragment
     */
    private void settingsOptionsListeners(FragmentSettingsMenuBinding binding) {
        ArrayAdapter<CharSequence> languages;
        languages = ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()).getApplicationContext(),
                R.array.Languages, android.R.layout.simple_spinner_item);
        languages.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.idiomSelector.setAdapter(languages);
        binding.idiomSelector.setOnItemSelectedListener(this);

        binding.logoutButton.setOnClickListener(v -> Toast.makeText(getActivity(),
                "Logout button clicked", Toast.LENGTH_LONG).show());
        binding.changePasswordButton.setOnClickListener(v -> Toast.makeText(getActivity(),
                "Change Password button clicked", Toast.LENGTH_LONG).show());
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
