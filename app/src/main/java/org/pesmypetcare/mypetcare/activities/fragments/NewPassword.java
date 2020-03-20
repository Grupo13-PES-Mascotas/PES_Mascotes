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
    private final int MIN_PASS_LENTGH = 6;
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
        return !(!pass.equals(binding.confirmNewPasswordText.getText().toString())
                || pass.length() < MIN_PASS_LENTGH || weakPass(pass));
    }

    /**
     * Method responsible for checking whether a password is weak or not.
     * @return True if the password is weak or false otherwise
     */
    private boolean weakPass(String pass) {
        boolean uppercase = containsUppercase(pass);
        boolean lowercase = containsLowercase(pass);
        boolean number = containsNumber(pass);
        boolean specialChar = containsSpecialChar(pass);
        return !(uppercase && lowercase && number && specialChar);
    }

    /**
     * Method responsible for checking if the password contains an uppercase character.
     * @param pass The password
     * @return True if the password contains an uppercase letter or false otherwise
     */
    private boolean containsUppercase(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isUpperCase(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for checking if the password contains an lowercase character.
     * @param pass The password
     * @return True if the password contains an lowercase letter or false otherwise
     */
    private boolean containsLowercase(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isLowerCase(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for checking if the password contains a number.
     * @param pass The password
     * @return True if the password contains a number or false otherwise
     */
    private boolean containsNumber(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (Character.isDigit(pass.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method responsible for checking if the password contains an special character.
     * @param pass The password
     * @return True if the password contains an special character or false otherwise
     */
    private boolean containsSpecialChar(String pass) {
        for (int i = 0; i < pass.length(); ++i) {
            if (String.valueOf(pass.charAt(i)).matches("[^a-zA-Z0-9]")) {
                return true;
            }
        }
        return false;
    }
}