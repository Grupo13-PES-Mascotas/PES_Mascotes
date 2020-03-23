package org.pesmypetcare.mypetcare.activities.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.LoginActivity;
import org.pesmypetcare.mypetcare.activities.NewPasswordInterface;
import org.pesmypetcare.mypetcare.databinding.FragmentSettingsMenuBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsMenuFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private FragmentSettingsMenuBinding binding;
    private FirebaseAuth mAuth;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
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

        logOutListener();

        binding.changePasswordButton.setOnClickListener(v -> {
            Activity thisActivity = getActivity();
            assert thisActivity != null;
            ((NewPasswordInterface) thisActivity).changeFragmentPass(new NewPassword());
        });
    }

    /**
     * Initializes the listeners of the logOut button.
     */
    private void logOutListener() {
        binding.logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
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
