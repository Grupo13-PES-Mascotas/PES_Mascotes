package org.pesmypetcare.mypetcare.activities.fragments;

import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;

import org.pesmypetcare.mypetcare.R;
import org.pesmypetcare.mypetcare.activities.MainActivity;
import org.pesmypetcare.mypetcare.databinding.FragmentSignUpBinding;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {
    private final int MIN_PASS_LENTGH = 6;
    private final int PASS_POSITION = 2;
    private FragmentSignUpBinding binding;
    private TextInputEditText[] editText;
    private TextInputLayout [] inputLayout;
    private FirebaseAuth mAuth;
    private String email;
    private String password;
    private String username;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        editText = new TextInputEditText[] {binding.signUpUsernameText,
            binding.signUpMailText, binding.signUpPasswordText, binding.signUpRepPasswordText};
        inputLayout = new TextInputLayout[] {binding.signUpUsernameLayout,
            binding.signUpMailLayout, binding.signUpPasswordLayout, binding.signUpRepPasswordLayout};
        binding.signupButton.setOnClickListener(v -> {
            if (validateSignUp()) {
                userCreationAndValidation();
            }
        });
        return view;
    }

    /**
     * This method is responsible for the creation and validation of the new user.
     */
    private void userCreationAndValidation() {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
            if (task.isSuccessful()) {
                sendEmailVerification();
            } else {
                Toast toast1 = Toast.makeText(getActivity(), "Failure at the user creation", Toast.LENGTH_LONG);
                toast1.setGravity(Gravity.CENTER, 0, 0);
                toast1.show();
            }
        });
    }

    /**
     * This method is responsible for the validation of the new user.
     */
    private void sendEmailVerification() {
        Objects.requireNonNull(mAuth.getCurrentUser()).sendEmailVerification();
        if (mAuth.getCurrentUser().isEmailVerified()) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        }
        Toast toast1 = Toast.makeText(getActivity(), "Email is not verified, check it", Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();
    }

    /**
     * Method responsible of checking if the sign up is correct.
     * @return True if the sign up was successful or false otherwise
     */
    private boolean validateSignUp() {
        resetFieldsStatus();
        email = Objects.requireNonNull(binding.signUpMailText.getText()).toString();
        password = Objects.requireNonNull(binding.signUpPasswordText.getText()).toString();
        username = Objects.requireNonNull(binding.signUpUsernameText.getText()).toString();
        boolean[] emptyFields = checkEmptyFields();
        if (emptyFields[PASS_POSITION]) {
            return false;
        }
        if (shortPass()) {
            return false;
        }
        if (weakPass()) {
            return false;
        }
        return !diffPass();
    }

    /**
     * Method responsible for checking which fields are empty.
     * @return Position i of the array is true if the field i is empty or false otherwise
     */
    private boolean[] checkEmptyFields() {
        boolean [] emptyFields = new boolean[editText.length];
        for (int i = 0; i < emptyFields.length; ++i) {
            if ("".equals(Objects.requireNonNull(editText[i].getText()).toString())) {
                emptyFields[i] = true;
                emptyFieldHandler(editText[i], inputLayout[i]);
            }
        }
        return emptyFields;
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
        if (password.length() < MIN_PASS_LENTGH) {
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
        boolean uppercase = containsUppercase(password);
        boolean lowercase = containsLowercase(password);
        boolean number = containsNumber(password);
        boolean specialChar = containsSpecialChar(password);
        if (uppercase && lowercase && number && specialChar) {
            return false;
        }
        weakPassHandler(binding.signUpPasswordText, binding.signUpPasswordLayout,
            getResources().getString(R.string.weakPassword));
        return true;
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

    /**
     * The method responsible for checking if the passwords are different.
     * @return True if the passwords do not match or false otherwise
     */
    private boolean diffPass() {
        if (!password.equals(Objects.requireNonNull(binding.signUpRepPasswordText.getText()).toString())) {
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
