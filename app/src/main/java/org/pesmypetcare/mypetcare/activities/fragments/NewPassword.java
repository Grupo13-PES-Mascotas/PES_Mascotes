package org.pesmypetcare.mypetcare.activities.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.pesmypetcare.mypetcare.activities.NewPasswordInterface;
import org.pesmypetcare.mypetcare.databinding.FragmentNewPasswordBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPassword extends Fragment {
    private FragmentNewPasswordBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewPasswordBinding.inflate(getLayoutInflater());
        settingsOptionsListeners();
        return binding.getRoot();
    }

    /**
     * Initializes the listeners of the fragment.
     */
    private void settingsOptionsListeners() {
        binding.confirmButton.setOnClickListener(v -> {
            if (validatePassword()) {
                Toast.makeText(getActivity(), "Not implemented yet", Toast.LENGTH_LONG).show();
                Activity thisActivity = getActivity();
                assert thisActivity != null;
                ((NewPasswordInterface) thisActivity).changeFragmentPass(new SettingsMenuFragment());
            }
        });
    }

    /**
     * Method responsible of checking if the password change up is correct.
     * @return True if the sign up was successful or false otherwise
     */
    private boolean validatePassword() {
        String pass = binding.newPasswordText.getText().toString();
        if ( !pass.equals(binding.confirmNewPasswordText.getText().toString())
                || pass.length() < 6 || weakPass(pass)) {
            return false;
        }
        return true;
    }

    /**
     * Method responsible for checking whether a password is weak or not.
     * @return True if the password is weak or false otherwise
     */
    private boolean weakPass(String pass) {
        boolean uppercase = false;
        boolean lowercase = false;
        boolean number = false;
        boolean specialChar = false;
        for (int i = 0; i < pass.length(); ++i) {
            char aux = pass.charAt(i);
            if (Character.isLowerCase(aux)) {
                lowercase = true;
            } else if (Character.isUpperCase(aux)) {
                uppercase = true;
            } else if (Character.isDigit(aux)) {
                number = true;
            } else if (String.valueOf(aux).matches("[^a-zA-Z0-9]")) {
                specialChar = true;
            }
        }
        if (uppercase && lowercase && number && specialChar) {
            return false;
        }
        return true;
    }
}
