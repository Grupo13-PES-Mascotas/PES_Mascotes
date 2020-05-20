package org.pesmypetcare.mypetcare.activities.fragments.settings;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.pesmypetcare.mypetcare.databinding.FragmentNewPasswordBinding;

/**
 * @author Enric Hernando
 */
public class NewPasswordFragment extends Fragment {
    private final int MIN_PASS_LENTGH = 6;
    private String passwd;
    private FragmentNewPasswordBinding binding;
    private SettingsCommunication communication;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNewPasswordBinding.inflate(getLayoutInflater());
        communication = (SettingsCommunication) getActivity();
        settingsOptionsListeners();
        return binding.getRoot();
    }

    /**
     * Initializes the listeners of the fragment.
     */
    private void settingsOptionsListeners() {
        binding.confirmButton.setOnClickListener(v -> {
            if (validatePassword()) {
                changePassword();
                Activity thisActivity = getActivity();
                assert thisActivity != null;
                ((NewPasswordInterface) thisActivity).changeFragmentPass(new SettingsMenuFragment());
            }
        });
    }

    /**
     * Method responsible of made the password change.
     */
    private void changePassword() {
        communication.changePassword(passwd);
    }

    /**
     * Method responsible of checking if the password change is correct.
     * @return True if the sign up was successful or false otherwise
     */
    private boolean validatePassword() {
        passwd = binding.newPasswordText.getText().toString();
        return !(!passwd.equals(binding.confirmNewPasswordText.getText().toString())
                || passwd.length() < MIN_PASS_LENTGH || weakPass(passwd));
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
