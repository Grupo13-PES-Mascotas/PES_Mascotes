package org.pesmypetcare.mypetcare.activities.fragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.User;
import org.pesmypetcare.mypetcare.databinding.FragmentSignUpBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private final int PASS_POSITION = 2;
    private FragmentSignUpBinding binding;
    private TextInputEditText[] editText;
    private TextInputLayout [] inputLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        editText = new TextInputEditText[] {binding.signUpUsernameText,
            binding.signUpMailText, binding.signUpPasswordText, binding.signUpRepPasswordText};
        inputLayout = new TextInputLayout[] {binding.signUpUsernameLayout,
            binding.signUpMailLayout, binding.signUpPasswordLayout, binding.signUpRepPasswordLayout};
        binding.signupButton.setOnClickListener(v -> {
            if (validateSignUp()) {
                testToast();
            }
        });
        return view;
    }

    /**
     * Method used to test to functionality of the components of the interface.
     */
    private void testToast() {
        String username = binding.signUpUsernameText.getText().toString();
        String mail = binding.signUpMailText.getText().toString();
        String passwd = binding.signUpPasswordText.getText().toString();
        User test = new User(username, mail, passwd);
        Toast toast1 = Toast.makeText(getActivity(), "Username " + test.getUsername(), Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
        toast1 = Toast.makeText(getActivity(), "Mail " + test.getMail(), Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
        toast1 = Toast.makeText(getActivity(), "Password " + test.getPasswd(), Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    /**
     * Method responsible of checking if the sign up is correct.
     * @return True if the sign up was successful or false otherwise
     */
    private boolean validateSignUp() {
        resetFieldsStatus();
        boolean [] emptyFields = new boolean[editText.length];
        boolean shortPass;
        boolean weakPass;
        boolean diffPass;
        for (int i = 0; i < emptyFields.length; ++i) {
            if ("".equals(editText[i].getText().toString())) {
                emptyFields[i] = true;
                emptyFieldHandler(editText[i], inputLayout[i]);
            }
        }
        if (!emptyFields[PASS_POSITION]) {
            shortPass = shortPass();
            if (!shortPass) {
                weakPass = weakPass();
                if (!weakPass) {
                    diffPass = diffPass();
                    return !diffPass;
                }
            }
        }
        return false;
    }

    /**
     * Method responsible for resetting the status of the fields.
     */
    private void resetFieldsStatus() {
        for (int i = 0; i < editText.length; ++i) {
            inputLayout[i].setHelperText("");
        }
        editText[PASS_POSITION].setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * Method responsible for handling the empty fields.
     * @param eT Edit Text of the empty field
     * @param iL Input Layout of the empty field
     */
    private void emptyFieldHandler(TextInputEditText eT, TextInputLayout iL) {
        iL.setHelperText(getResources().getString(R.string.emptyField));
        iL.setHelperTextColor(ColorStateList.valueOf(Color.RED));
        eT.setHintTextColor(Color.RED);
    }

    /**
     * Method responsible for checking if a password is too short.
     * @return True if the password is too short or false otherwise
     */
    private boolean shortPass() {
        int MIN_PASS_LENTGH = 6;
        if (binding.signUpPasswordText.getText().toString().length() < MIN_PASS_LENTGH) {
            weakPassHandler(binding.signUpPasswordText, binding.signUpPasswordLayout,
                getResources().getString(R.string.shortPassword));
            return true;
        }
        return false;
    }

    /**
     * Method responsible for handling weak passwords.
     * @param eT Edit Text of the password
     * @param iL Input Layout of the password
     * @param s String to set in the helper
     */
    private void weakPassHandler(TextInputEditText eT, TextInputLayout iL, String s) {
        eT.setTextColor(Color.RED);
        iL.setHelperText(s);
        iL.setHelperTextColor(ColorStateList.valueOf(Color.RED));
    }

    /**
     * Method responsible for checking whether a password is weak or not.
     * @return True if the password is weak or false otherwise
     */
    private boolean weakPass() {
        String pass = binding.signUpPasswordText.getText().toString();
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
        weakPassHandler(binding.signUpPasswordText, binding.signUpPasswordLayout,
            getResources().getString(R.string.weakPassword));
        return true;
    }

    /**
     * The method responsible for checking if the passwords are different.
     * @return True if the passwords do not match or false otherwise
     */
    private boolean diffPass() {
        String text = binding.signUpPasswordText.getText().toString();
        if (!text.equals(binding.signUpRepPasswordText.getText().toString())) {
            diffPassHandler(binding.signUpPasswordText, binding.signUpPasswordLayout,
                getResources().getString(R.string.differentPasswords));
            diffPassHandler(binding.signUpRepPasswordText, binding.signUpRepPasswordLayout,
                getResources().getString(R.string.differentPasswords));
            return true;
        }
        return false;
    }

    /**
     * Method responsible for handling the different passwords.
     * @param eT Edit Text of the password
     * @param iL Input Layout of the password
     * @param s String to set in the helper
     */
    private void diffPassHandler(TextInputEditText eT, TextInputLayout iL, String s) {
        eT.setText("");
        iL.setHelperText(s);
        iL.setHelperTextColor(ColorStateList.valueOf(Color.RED));
    }
}
